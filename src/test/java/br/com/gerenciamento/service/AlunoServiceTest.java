package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
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

    private Aluno aluno_1, aluno_2;

    // _________________________Refatoração_________________________________
    @Before
	public void AlunosParaTestes() {
		aluno_1 = new Aluno();
		aluno_1.setNome("Katarinny");
        aluno_1.setCurso(Curso.ENFERMAGEM);
        aluno_1.setStatus(Status.ATIVO);
		aluno_1.setTurno(Turno.MATUTINO);
		aluno_1.setMatricula("123");

		aluno_2 = new Aluno();
		aluno_2.setNome("Rafael");
        aluno_2.setCurso(Curso.INFORMATICA);
        aluno_2.setStatus(Status.INATIVO);
		aluno_2.setTurno(Turno.NOTURNO);
		aluno_2.setMatricula("321");
	}
    @Test
    @Transactional
	public void getById() {
		this.serviceAluno.save(aluno_1);
		Long alunoId = aluno_1.getId();
		Aluno alunoTemp = this.serviceAluno.getById(alunoId);
		Assert.assertTrue(alunoTemp.getNome().equals("Katarinny"));
	}
    @Test
    @Transactional
	public void salvarSemNome() {
		aluno_1.setNome(null);
		Assert.assertThrows(ConstraintViolationException.class, () -> {
			this.serviceAluno.save(aluno_1);
		});
	}

  // _________________________Novos testes___________________________________

    @Test
    @Transactional
	public void salvarSemMatricula() {
		aluno_1.setMatricula(null);
		Assert.assertThrows(ConstraintViolationException.class, () -> {
			this.serviceAluno.save(aluno_1);
		});
	}
    
    @Test
    @Transactional
    public void findByStatusInativo() {
        this.serviceAluno.save(aluno_1);
        this.serviceAluno.save(aluno_2);
        
        List<Long> idsInativos = new ArrayList<Long>(2);
        idsInativos.add(aluno_2.getId());
        idsInativos.add(aluno_1.getId());

        List<Aluno> Inativos = this.serviceAluno.findByStatusInativo();
        Assert.assertEquals(1, Inativos.size());
        Assert.assertTrue(idsInativos.contains(Inativos.get(0).getId()));

    }
    @Test
    @Transactional
    public void findByStatusAtivo() {
        this.serviceAluno.save(aluno_1);
        this.serviceAluno.save(aluno_2);
        
        List<Long> idsAtivos = new ArrayList<Long>(3);
        idsAtivos.add(aluno_2.getId());
        idsAtivos.add(aluno_1.getId());

        List<Aluno> Ativos = this.serviceAluno.findByStatusAtivo();
        Assert.assertEquals(1, Ativos.size());
        Assert.assertTrue(idsAtivos.contains(Ativos.get(0).getId()));

    }

    @Test
    @Transactional
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno = new Aluno();
        aluno.setId(4L);
        aluno.setNome("EdMaciO");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("246810");
        this.serviceAluno.save(aluno);

        List<Aluno> listaAlunos = serviceAluno.findByNomeContainingIgnoreCase("Edmacio");
        Assert.assertEquals(1, listaAlunos.size());
        Assert.assertEquals("EdMaciO", listaAlunos.get(0).getNome());

    }

}