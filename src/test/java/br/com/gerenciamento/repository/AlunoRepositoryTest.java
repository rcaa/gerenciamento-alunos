package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Turno;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno aluno1;
    private Aluno aluno2;
    private Aluno aluno3;

    @Before
    public void setUp() {
        aluno1 = new Aluno();
        aluno1.setNome("João Silva");
        aluno1.setMatricula("123");
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.MATUTINO);

        aluno2 = new Aluno();
        aluno2.setNome("Maria Souza");
        aluno2.setMatricula("456");
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setTurno(Turno.NOTURNO);

        aluno3 = new Aluno();
        aluno3.setNome("Ana Clara");
        aluno3.setMatricula("789");
        aluno3.setCurso(Curso.DIREITO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setTurno(Turno.MATUTINO);

        alunoRepository.saveAll(Arrays.asList(aluno1, aluno2, aluno3));
    }

    @Test
    @Transactional
    public void testFindByStatusAtivo() {
        List<Aluno> result = alunoRepository.findByStatusAtivo();
        assertEquals(2, result.size());
        assertTrue(result.contains(aluno1));
        assertTrue(result.contains(aluno3));
    }

    @Test
    @Transactional
    public void testFindByStatusInativo() {
        List<Aluno> result = alunoRepository.findByStatusInativo();
        assertEquals(1, result.size());
        assertTrue(result.contains(aluno2));
    }

}
