package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;
    
    private Aluno aluno = new Aluno();
    private Aluno aluno2 = new Aluno();

    @Before
    public void setUp() {
        aluno.setId(1L);
        aluno.setNome("alunoo");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setMatricula("123456");

        aluno2.setId(2L);
        aluno2.setNome("inativo");
        aluno2.setStatus(Status.INATIVO);
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setMatricula("123456");

        this.alunoRepository.save(aluno);
        this.alunoRepository.save(aluno2);
    }

    @After
    public void reset() {
        this.alunoRepository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Aluno> alunosEncontrados = this.alunoRepository.findAll();
        Assert.assertEquals(2, alunosEncontrados.size());
        Assert.assertTrue(alunosEncontrados.stream().anyMatch(a -> a.getNome().equals(aluno.getNome())));
        Assert.assertTrue(alunosEncontrados.stream().anyMatch(a -> a.getNome().equals(aluno2.getNome())));
    }

    @Test
    public void findByStatusAtivo() {
        List<Aluno> alunosEncontrados = this.alunoRepository.findByStatusAtivo();
        Assert.assertEquals(1, alunosEncontrados.size());
        Assert.assertTrue(alunosEncontrados.stream().anyMatch(a -> a.getNome().equals(aluno.getNome())));
    }

    @Test
    public void findByStatusInativo() {
        List<Aluno> alunosEncontrados = this.alunoRepository.findByStatusInativo();
        Assert.assertEquals(1, alunosEncontrados.size());
        Assert.assertTrue(alunosEncontrados.stream().anyMatch(a -> a.getNome().equals(aluno2.getNome())));
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        List<Aluno> alunosEncontrados = this.alunoRepository.findByNomeContainingIgnoreCase("aLuNo");
        Assert.assertEquals(1, alunosEncontrados.size());
        Assert.assertTrue(alunosEncontrados.stream().anyMatch(a -> a.getNome().equals(aluno.getNome())));
    }
}
