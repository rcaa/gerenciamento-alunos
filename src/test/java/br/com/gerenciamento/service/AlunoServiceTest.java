package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest
{
    @Autowired
    private ServiceAluno serviceAluno;

    @Test
	@Transactional
    public void getById()
	{
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        List<Aluno> alunos = this.serviceAluno.findAll();
		Aluno alunoAchado = this.serviceAluno.getById(alunos.get(0).getId());
        Assert.assertTrue(alunoAchado.getNome().equals("Vinicius"));
    }

    @Test
	@Transactional
    public void salvarSemNome()
	{
        Aluno aluno = new Aluno();
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {this.serviceAluno.save(aluno);});
    }

	@Test
	@Transactional
    public void findAll()
    {
		Aluno aluno1 = new Aluno();
		Aluno aluno2 = new Aluno();
        aluno1.setNome("aaaaa");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("aaa");
        aluno2.setNome("bbbbb");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("bbb");
        this.serviceAluno.save(aluno1);
		this.serviceAluno.save(aluno2);
        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertEquals(2, alunos.size());
    }

    @Test
	@Transactional
    public void deleteById()
    {
        Aluno aluno = new Aluno();
        aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
        this.serviceAluno.save(aluno);
		List<Aluno> alunos = this.serviceAluno.findAll();
        this.serviceAluno.deleteById(alunos.get(alunos.size()-1).getId());
        Assert.assertThrows(java.util.NoSuchElementException.class, () -> {this.serviceAluno.getById(alunos.get(alunos.size()-1).getId());});
    }

    @Test
	@Transactional
    public void findByStatusAtivo()
    {
        Aluno aluno = new Aluno();
        aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
        this.serviceAluno.save(aluno);
        List<Aluno> alunos = this.serviceAluno.findByStatusAtivo();
        Assert.assertTrue(alunos.get(0).getNome().equals("aaaaa"));
    }

    @Test
	@Transactional
    public void findByNomeContainingIgnoreCase()
    {
        Aluno aluno = new Aluno();
        aluno.setNome("AaAaA");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
        this.serviceAluno.save(aluno);
        List<Aluno> alunos = this.serviceAluno.findByNomeContainingIgnoreCase("aAaAa");
        Assert.assertTrue(alunos.get(0).getNome().equals("AaAaA"));
	}
}