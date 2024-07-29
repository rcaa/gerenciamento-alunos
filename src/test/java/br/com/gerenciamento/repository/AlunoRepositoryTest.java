package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setNome("Aluno1");
        alunoRepository.save(aluno);
    }

    @Test
    void testFindById() {
        Optional<Aluno> found = alunoRepository.findById(aluno.getId());
        assertTrue(found.isPresent());
        assertEquals(aluno.getId(), found.get().getId());
    }

    @Test
    void testSave() {
        Aluno novoAluno = new Aluno();
        novoAluno.setNome("Aluno2");
        Aluno savedAluno = alunoRepository.save(novoAluno);
        assertNotNull(savedAluno);
        assertEquals("Aluno2", savedAluno.getNome());
    }

    @Test
    void testDelete() {
        alunoRepository.delete(aluno);
        Optional<Aluno> deletedAluno = alunoRepository.findById(aluno.getId());
        assertFalse(deletedAluno.isPresent());
    }

    @Test
    void testFindAll() {
        Iterable<Aluno> alunos = alunoRepository.findAll();
        assertNotNull(alunos);
        assertTrue(alunos.iterator().hasNext());
    }
}
