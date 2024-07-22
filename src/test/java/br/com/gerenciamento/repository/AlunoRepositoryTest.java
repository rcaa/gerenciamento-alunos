package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Before
    public void setUp() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setMatricula("123456");
        aluno1.setNome("Carlos");
        aluno1.setStatus(Status.ATIVO);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setMatricula("1234567");
        aluno2.setNome("Maria");
        aluno2.setStatus(Status.INATIVO);
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setMatricula("12345678");
        aluno3.setNome("Jorge");
        aluno3.setStatus(Status.ATIVO);
        alunoRepository.save(aluno3);

        Aluno aluno4 = new Aluno();
        aluno4.setTurno(Turno.NOTURNO);
        aluno4.setCurso(Curso.ADMINISTRACAO);
        aluno4.setMatricula("123456789");
        aluno4.setNome("Clara");
        aluno4.setStatus(Status.INATIVO);
        alunoRepository.save(aluno4);

        Aluno aluno5 = new Aluno();
        aluno5.setTurno(Turno.NOTURNO);
        aluno5.setCurso(Curso.ADMINISTRACAO);
        aluno5.setMatricula("1234567890");
        aluno5.setNome("Carla");
        aluno5.setStatus(Status.ATIVO);
        alunoRepository.save(aluno5);
    }

    @Test
    public void testeFindByStatusAtivo() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertEquals(3, alunosAtivos.size());
        assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getNome().equals("Carlos")));
        assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getNome().equals("Jorge")));
        assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getNome().equals("Carla")));
    }

    @Test
    public void testeFindByStatusInativo() {
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertEquals(2, alunosInativos.size());
        assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Maria")));
        assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Clara")));
    }

    @Test
    public void testeFindByNomeContainingIgnoreCase() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("car");
        assertEquals(2, alunos.size());
        assertTrue(alunos.stream().anyMatch(aluno -> aluno.getNome().equals("Carlos")));
        assertTrue(alunos.stream().anyMatch(aluno -> aluno.getNome().equals("Carla")));
    }

    @Test
    public void testeSalvarAluno() {
        Aluno aluno = new Aluno();
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("987654321");
        aluno.setNome("Novo Aluno");
        aluno.setStatus(Status.ATIVO);
        alunoRepository.save(aluno);

        Aluno alunoSalvo = alunoRepository.findById(aluno.getId()).orElse(null);
        assertEquals("Novo Aluno", alunoSalvo.getNome());
        assertEquals(Status.ATIVO, alunoSalvo.getStatus());
    }
}
