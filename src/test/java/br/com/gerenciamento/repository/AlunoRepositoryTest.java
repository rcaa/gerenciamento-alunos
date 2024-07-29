package br.com.gerenciamento.repository;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
	
	@Autowired
    private AlunoRepository repositoryAluno;
	
	@Test
	public void findByStatusAtivo() {
		Aluno aluno = new Aluno();
		aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.repositoryAluno.save(aluno);
        
        Aluno alunoRetorno = this.repositoryAluno.findByStatusAtivo().get(0);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
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
        this.repositoryAluno.save(aluno);
        
        Aluno alunoRetorno = this.repositoryAluno.findByStatusInativo().get(0);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
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
        this.repositoryAluno.save(aluno);
        
        Aluno alunoRetorno = this.repositoryAluno.findByNomeContainingIgnoreCase("Vinicius").get(0);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
	}
	
	@Test
	public void findByNomeContainingIgnoreCaseCaseSensitivity() {
		Aluno aluno = new Aluno();
		aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.repositoryAluno.save(aluno);
        
        Aluno alunoRetorno = this.repositoryAluno.findByNomeContainingIgnoreCase("vinicius").get(0);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
	}
}
