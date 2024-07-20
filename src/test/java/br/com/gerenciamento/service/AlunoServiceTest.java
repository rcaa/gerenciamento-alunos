package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void atualizarNome(){
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoSalvo = this.serviceAluno.getById(1L);
        alunoSalvo.setNome("Vinicios com o");
        this.serviceAluno.save(alunoSalvo);

        Aluno alunoAtualizado = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoAtualizado.getNome().equals("Vinicios com o"));
    }

    @Test
    public void deleteById(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(1L);

        Assert.assertThrows(NoSuchElementException.class, () -> {
          this.serviceAluno.getById(1L);
        });
    }
    
    @Test
    public void findByStatusInativo() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(4L);
        aluno2.setNome("Matheus");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123457");
        this.serviceAluno.save(aluno2);
        
        Aluno aluno3 = new Aluno();
        aluno3.setId(5L);
        aluno3.setNome("Lucas");
        aluno3.setTurno(Turno.MATUTINO);
        aluno3.setCurso(Curso.BIOMEDICINA);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("123458");
        this.serviceAluno.save(aluno3);

        Aluno aluno4 = new Aluno();
        aluno4.setId(6L);
        aluno4.setNome("Carlos");
        aluno4.setTurno(Turno.MATUTINO);
        aluno4.setCurso(Curso.DIREITO);
        aluno4.setStatus(Status.INATIVO);
        aluno4.setMatricula("123459");
        this.serviceAluno.save(aluno4);

        Aluno aluno5 = new Aluno();
        aluno5.setId(7L);
        aluno5.setNome("Rafael");
        aluno5.setTurno(Turno.MATUTINO);
        aluno5.setCurso(Curso.DIREITO);
        aluno5.setStatus(Status.INATIVO);
        aluno5.setMatricula("123460");
        this.serviceAluno.save(aluno5);
        List<Long> idsInativos = new ArrayList<Long>(5);
        idsInativos.add(aluno3.getId());
        idsInativos.add(aluno4.getId());
        idsInativos.add(aluno5.getId());
        
        List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();
        
        Assert.assertEquals(3, alunosInativos.size());
        Assert.assertTrue(idsInativos.contains(alunosInativos.get(0).getId()));
        Assert.assertTrue(idsInativos.contains(alunosInativos.get(1).getId()));
        Assert.assertTrue(idsInativos.contains(alunosInativos.get(2).getId()));
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno = new Aluno();
        aluno.setId(8L);
        aluno.setNome("AdaLBerTo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> listaAlunos = serviceAluno.findByNomeContainingIgnoreCase("adalberto");
        Assert.assertEquals(1, listaAlunos.size());
        Assert.assertEquals("AdaLBerTo", listaAlunos.get(0).getNome());
        
    }

}