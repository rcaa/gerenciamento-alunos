package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

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
    public void findByStatusInativo() {
    	Aluno aluno = new Aluno();
    	aluno.setId(1L);
    	aluno.setNome("Vinicius");
    	aluno.setTurno(Turno.NOTURNO);
    	aluno.setCurso(Curso.ADMINISTRACAO);
    	aluno.setStatus(Status.INATIVO);
    	aluno.setMatricula("123456");
    	this.serviceAluno.save(aluno);
    	
    	Aluno alunoRetorno = this.serviceAluno.findByStatusInativo().get(0);
    	Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }
    
    @Test
    public void deleteById() {
    	Aluno alunoA = new Aluno();
    	alunoA.setId(1L);
    	alunoA.setNome("Vinicius");
    	alunoA.setTurno(Turno.NOTURNO);
    	alunoA.setCurso(Curso.ADMINISTRACAO);
    	alunoA.setStatus(Status.ATIVO);
    	alunoA.setMatricula("123456");
    	this.serviceAluno.save(alunoA);
    	
    	Aluno alunoB = new Aluno();
    	alunoB.setId(2L);
    	alunoB.setNome("Lelouch");
    	alunoB.setTurno(Turno.NOTURNO);
    	alunoB.setCurso(Curso.ADMINISTRACAO);
    	alunoB.setStatus(Status.ATIVO);
    	alunoB.setMatricula("654321");
    	this.serviceAluno.save(alunoB);
    	
    	this.serviceAluno.deleteById(1L);
    	
    	List<Aluno> alunos = this.serviceAluno.findAll();
    	int tamanhoLista = alunos.size();
    	
    	Assert.assertTrue(tamanhoLista == 1);
    }
    
    @Test
    public void salvarSemId() {
    	Aluno aluno = new Aluno();
    	aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }
    
    @Test
    public void findByNomeContainingIgnoreCase() {
    	Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> alunoRetorno = this.serviceAluno.findByNomeContainingIgnoreCase("vinicius");
        Assert.assertTrue(alunoRetorno.get(0).getNome().equals("Vinicius"));
    }
}
