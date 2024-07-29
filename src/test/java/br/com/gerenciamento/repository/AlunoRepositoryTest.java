package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Before
    public void setUp() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Carlos");
        aluno1.setMatricula("654321");
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria");
        aluno2.setMatricula("987654");
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setTurno(Turno.NOTURNO);
        alunoRepository.save(aluno2);
    }

    @Test
    public void testFindByStatusAtivo() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertEquals(1, alunosAtivos.size());
        assertTrue(alunosAtivos.stream().anyMatch(a -> "Carlos".equals(a.getNome())));
    }

    @Test
    public void testFindByStatusInativo() {
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertEquals(1, alunosInativos.size());
        assertTrue(alunosInativos.stream().anyMatch(a -> "Maria".equals(a.getNome())));
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("carlo");
        assertEquals(1, alunos.size());
        assertTrue(alunos.stream().anyMatch(a -> "Carlos".equals(a.getNome())));
    }

    @Test
    public void testFindByNomeContainingIgnoreCaseNotFound() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("João");
        assertEquals(0, alunos.size());
    }
}
