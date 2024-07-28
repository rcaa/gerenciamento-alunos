package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

	@Autowired
	private AlunoRepository alunoRepository;

	private Aluno aluno1, aluno2;

	@Before
	public void setup() {
		aluno1 = new Aluno();
		aluno1.setNome("Pedro");
		aluno1.setTurno(Turno.NOTURNO);
		aluno1.setCurso(Curso.INFORMATICA);
		aluno1.setStatus(Status.ATIVO);
		aluno1.setMatricula("1111");

		aluno2 = new Aluno();
		aluno2.setNome("Maria");
		aluno2.setTurno(Turno.NOTURNO);
		aluno2.setCurso(Curso.INFORMATICA);
		aluno2.setStatus(Status.INATIVO);
		aluno2.setMatricula("2222");

		alunoRepository.save(aluno1);
		alunoRepository.save(aluno2);
	}

	@After
	public void teardown() {
		alunoRepository.deleteAll();
	}

	@Test
	public void findByStatusAtivo() {
		List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();

		Assert.assertEquals(1, alunosAtivos.size());
		Assert.assertEquals("Pedro", alunosAtivos.get(0).getNome());
	}

	@Test
	public void findByStatusInativo() {
		List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();

		Assert.assertEquals(1, alunosInativos.size());
		Assert.assertEquals("Maria", alunosInativos.get(0).getNome());
	}

	@Test
	public void findByNomeContainingIgnoreCase() {
		String nomeAluno = "pedro";

		List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase(nomeAluno);

		Assert.assertEquals(1, alunos.size());
		Assert.assertEquals("Pedro", alunos.get(0).getNome());
	}

	@Test
	public void findByIdNotFound() {
		Long id = 100L;

		Assert.assertEquals(Optional.empty(), alunoRepository.findById(id));
	}

}
