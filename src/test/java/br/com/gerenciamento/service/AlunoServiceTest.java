package br.com.gerenciamento.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

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
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }
    
    @Test 
    public void atualizarDados(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno atualizado = this.serviceAluno.getById(1L);
        atualizado.setNome("Lucas");
        this.serviceAluno.save(atualizado);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Lucas"));
    }

    @Test 
    public void encontrarTodosAlunos(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        
        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Pedro");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("18429");
        this.serviceAluno.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setId(3L);
        aluno3.setNome("Luisa");
        aluno3.setTurno(Turno.MATUTINO);
        aluno3.setCurso(Curso.DIREITO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("18473");
        this.serviceAluno.save(aluno3);

        List<Aluno> alunos = serviceAluno.findAll();
        Assert.assertNotNull(alunos);
        Assert.assertEquals(3,alunos.size());
        Assert.assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("Vinicius")));
        Assert.assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("Pedro")));
        Assert.assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("Luisa")));
    }

    @Test
    public void deletarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
    
        this.serviceAluno.deleteById(1L);
        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertNull(alunoRetorno);
    }

    @Test 
    public void pesquisarAluno(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius Souza");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Vinicius Santos");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.CONTABILIDADE);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("48163");
        this.serviceAluno.save(aluno2);

        List<Aluno> busca = serviceAluno.findByNomeContainingIgnoreCase("Vinicius");
        Assert.assertNotNull(busca);
        Assert.assertEquals(2,busca.size());
    }
    

}
