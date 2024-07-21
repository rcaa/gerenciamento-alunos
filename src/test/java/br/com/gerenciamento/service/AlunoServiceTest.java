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
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void salvarNomeComPoucosCaracteres() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Alex");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
           this.serviceAluno.save(aluno); 
        });
    }

    @Test
    public void salvarMatriculaComPoucosCaracteres() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Alex");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
           this.serviceAluno.save(aluno); 
        });
    }

    @Test
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setId(4L);
        aluno.setNome("Marco");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.deleteById(aluno.getId());
        
        Assert.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(aluno.getId());
        });
    }

    @Test
    public void findByStatusAtivo() {
        Aluno aluno = new Aluno();
        aluno.setId(5L);
        aluno.setNome("João");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("112233");
        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();

        Assert.assertNotNull(alunosAtivos);
    }

}