package br.com.gerenciamento.repository;

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
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest
{
	@Autowired
    private AlunoRepository alunoRepository;

	@Test
	@Transactional
    public void save()
	{
		Aluno aluno = new Aluno();
		aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
		this.alunoRepository.save(aluno);
		Aluno alunoEncontrado = this.alunoRepository.findById(aluno.getId()).orElse(null);
		Assert.assertTrue(alunoEncontrado.getNome().equals("aaaaa"));
	}

	@Test
	@Transactional
    public void saveInvalidName()
	{
		Aluno aluno = new Aluno();
		aluno.setNome("a");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
		Assert.assertThrows(ConstraintViolationException.class, () -> {this.alunoRepository.save(aluno);});
	}

	@Test
	@Transactional
    public void findByStatusInativo()
	{
		Aluno aluno = new Aluno();
		aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("aaa");
		this.alunoRepository.save(aluno);
		List<Aluno> alunos = this.alunoRepository.findByStatusInativo();
		Aluno alunoAchado = null;
		for (int index = 0; index < alunos.size(); index++)
		{
			if (alunos.get(index).getId() == aluno.getId())
			{
				alunoAchado = alunos.get(index);
			}
		}
		Assert.assertTrue(alunoAchado.getNome().equals("aaaaa"));
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
		this.alunoRepository.save(aluno);
		List<Aluno> alunos = this.alunoRepository.findByNomeContainingIgnoreCase("aAaAa");
		Aluno alunoAchado = null;
		for (int index = 0; index < alunos.size(); index++)
		{
			if (alunos.get(index).getId() == aluno.getId())
			{
				alunoAchado = alunos.get(index);
			}
		}
		Assert.assertTrue(alunoAchado.getNome().equals("AaAaA"));
	}
}