package br.com.gerenciamento.service;

import java.util.NoSuchElementException;

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
        ConstraintViolationException exception = Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }
    
    @Test
    public void getByIdNotFound(){
        Assert.assertThrows(NoSuchElementException.class,() -> {
            this.serviceAluno.getById(11L);});
        }

    @Test
    public void salvarSemMatricula(){
        Aluno aluno = new Aluno();
        aluno.setId(5L);
        aluno.setNome("Pedro");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});
    }

    @Test
    public void salvarAlunoSemID(){
        Aluno aluno = new Aluno();
        aluno.setNome("Pedro");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        if(aluno.getId() == null){
            Assert.fail();
        }
    }

    @Test
    public void salvarAlunoSemStatus(){
        Aluno aluno = new Aluno();
        aluno.setId(5L);
        aluno.setNome("Pedro");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});
    }
}