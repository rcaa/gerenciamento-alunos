package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.transaction.Transactional;
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
@Transactional
public class AlunoServiceTest {

	@Autowired
	private ServiceAluno serviceAluno;

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
		aluno2.setCurso(Curso.ADMINISTRACAO);
		aluno2.setStatus(Status.INATIVO);
		aluno2.setMatricula("2222");
	}

	@Test
	public void getById() {
		this.serviceAluno.save(aluno1);
		Long alunoID = aluno1.getId();

		Aluno alunoRetorno = this.serviceAluno.getById(alunoID);

		Assert.assertTrue(alunoRetorno.getNome().equals("Pedro"));
	}

	@Test
	public void salvarSemNome() {
		aluno1.setNome(null);

		Assert.assertThrows(ConstraintViolationException.class, () -> {
			this.serviceAluno.save(aluno1);
		});
	}

	@Test
	public void findAll() {
		this.serviceAluno.save(aluno1);
		this.serviceAluno.save(aluno2);

		List<Aluno> alunos = this.serviceAluno.findAll();

		Assert.assertEquals(2, alunos.size());
		Assert.assertEquals("Pedro", alunos.get(0).getNome());
		Assert.assertEquals("Maria", alunos.get(1).getNome());
	}

	@Test
	public void getByIdNotFound() {
		Long id = 100L;

		Assert.assertThrows(NoSuchElementException.class, () -> {
			this.serviceAluno.getById(id);
		});
	}

	@Test
	public void findByStatusAtivo() {
		this.serviceAluno.save(aluno1);
		this.serviceAluno.save(aluno2);

		List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();

		Assert.assertEquals(1, alunosAtivos.size());
		Assert.assertEquals(Status.ATIVO, alunosAtivos.get(0).getStatus());
	}

	@Test
	public void findByStatusInativo() {
		this.serviceAluno.save(aluno1);
		this.serviceAluno.save(aluno2);

		List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();

		Assert.assertEquals(1, alunosInativos.size());
		Assert.assertEquals(Status.INATIVO, alunosInativos.get(0).getStatus());
	}
}