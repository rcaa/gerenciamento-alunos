package br.com.gerenciamento.repository;


import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
// import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
	private AlunoRepository alunoRepository;

    private Aluno aluno_1, aluno_2;
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
    public void findByStatusAtivo() {

        this.alunoRepository.save(aluno_1);
        this.alunoRepository.save(aluno_2);

        List<Long> idsAtivos = new ArrayList<Long>(3);
        idsAtivos.add(aluno_2.getId());
        idsAtivos.add(aluno_1.getId());

        List<Aluno> Ativos = this.alunoRepository.findByStatusAtivo();
        Assert.assertEquals(1, Ativos.size());
        Assert.assertEquals("Katarinny", Ativos.get(0).getNome());


    }

    @Test
    @Transactional
    public void findByStatusInativo() {
        this.alunoRepository.save(aluno_1);
        this.alunoRepository.save(aluno_2);
        
        List<Long> idsInativos = new ArrayList<Long>(2);
        idsInativos.add(aluno_2.getId());
        idsInativos.add(aluno_1.getId());

        List<Aluno> Inativos = this.alunoRepository.findByStatusInativo();
        Assert.assertEquals(1, Inativos.size());
        Assert.assertEquals("Rafael", Inativos.get(0).getNome());

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
        this.alunoRepository.save(aluno);

        List<Aluno> listaAlunos = alunoRepository.findByNomeContainingIgnoreCase("Edmacio");
        Assert.assertEquals(1, listaAlunos.size());
        Assert.assertEquals("EdMaciO", listaAlunos.get(0).getNome());

    }

    @Test
    @Transactional
    public void FindAll() {
        this.alunoRepository.save(aluno_1);
        this.alunoRepository.save(aluno_2);

        List<Aluno> TodosAlunos = this.alunoRepository.findAll();

        Assert.assertEquals(2, TodosAlunos.size());
        Assert.assertEquals("Katarinny", TodosAlunos.get(0).getNome());
        Assert.assertEquals("Rafael", TodosAlunos.get(1).getNome());
    }
}
