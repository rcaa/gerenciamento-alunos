package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

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
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(aluno.getId());
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
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
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoSalvo = this.serviceAluno.getById(aluno.getId());
        alunoSalvo.setNome("Vinicios com o");
        this.serviceAluno.save(alunoSalvo);

        Aluno alunoAtualizado = this.serviceAluno.getById(alunoSalvo.getId());
        Assert.assertTrue(alunoAtualizado.getNome().equals("Vinicios com o"));
    }

    @Test
    public void deleteById(){
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(aluno.getId());

        Assert.assertThrows(NoSuchElementException.class, () -> {
          this.serviceAluno.getById(aluno.getId());
        });
    }
    
    @Test
    public void findByStatusInativo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Vinicius");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Matheus");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123457");
        this.serviceAluno.save(aluno2);
        
        Aluno aluno3 = new Aluno();
        aluno3.setNome("Lucas");
        aluno3.setTurno(Turno.MATUTINO);
        aluno3.setCurso(Curso.BIOMEDICINA);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("123458");
        this.serviceAluno.save(aluno3);

        Aluno aluno4 = new Aluno();
        aluno4.setNome("Carlos");
        aluno4.setTurno(Turno.MATUTINO);
        aluno4.setCurso(Curso.DIREITO);
        aluno4.setStatus(Status.INATIVO);
        aluno4.setMatricula("123459");
        this.serviceAluno.save(aluno4);

        Aluno aluno5 = new Aluno();
        aluno5.setNome("Rafael");
        aluno5.setTurno(Turno.MATUTINO);
        aluno5.setCurso(Curso.DIREITO);
        aluno5.setStatus(Status.INATIVO);
        aluno5.setMatricula("123460");
        this.serviceAluno.save(aluno5);
        
        List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();
        
        Assert.assertEquals(3, alunosInativos.size());
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Lucas")));
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Carlos")));
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Rafael")));
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno = new Aluno();
        aluno.setNome("AdaLBerTo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> listaAlunos = serviceAluno.findByNomeContainingIgnoreCase("adalberto");
        Assert.assertEquals(1, listaAlunos.size());
        Assert.assertTrue(listaAlunos.stream().anyMatch(a -> a.getNome().equals("AdaLBerTo")));
        
    }

}