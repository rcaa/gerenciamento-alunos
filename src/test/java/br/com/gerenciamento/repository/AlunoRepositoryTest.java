package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testFindByStatusAtivo() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertNotNull(alunosAtivos);
        for (Aluno aluno : alunosAtivos) {
            assertEquals(Status.ATIVO, aluno.getStatus());
        }
    }

    @Test
    public void testFindByStatusInativo() {
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertNotNull(alunosInativos);
        for (Aluno aluno : alunosInativos) {
            assertEquals(Status.INATIVO, aluno.getStatus());
        }
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("silva");
        assertNotNull(alunos);
        for (Aluno aluno : alunos) {
            assertTrue(aluno.getNome().toLowerCase().contains("silva"));
        }
    }

    @Test
    public void testSaveAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setMatricula("12345678");
        aluno.setId(1L);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(Status.ATIVO);

        alunoRepository.save(aluno);
        assertNotNull(aluno.getId());

        Aluno alunoSalvo = alunoRepository.findById(aluno.getId()).orElse(null);
        assertNotNull(alunoSalvo);
        assertEquals("João Silva", alunoSalvo.getNome());
        assertEquals(Status.ATIVO, alunoSalvo.getStatus());
    }

}
