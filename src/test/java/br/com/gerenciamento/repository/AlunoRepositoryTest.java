package br.com.gerenciamento.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    AlunoRepository alunoRepository;

    @Test
    public void containsName() {

        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setId(1L);
        aluno1.setNome("Guilherme");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("176");

        aluno2.setId(2L);
        aluno2.setNome("Guy F.");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("1763");

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        List<Aluno> alunos = this.alunoRepository.findByNomeContainingIgnoreCase("Gui");

        assertEquals(1, alunos.size());

    }

    @Test
    public void containsNameNotFound() {

        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setId(1L);
        aluno1.setNome("Guilherme");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("176");

        aluno2.setId(2L);
        aluno2.setNome("Guy F.");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("1763");

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        List<Aluno> alunos = this.alunoRepository.findByNomeContainingIgnoreCase("Fawkes");

        assertEquals(0, alunos.size());

    }

    @Test
    public void findByStatusAtivo() {

        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setId(1L);
        aluno1.setNome("Guilherme");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("176");

        aluno2.setId(2L);
        aluno2.setNome("Guy F.");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("1763");

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        List<Aluno> ativos = alunoRepository.findByStatusAtivo();

        assertEquals(1, ativos.size());

    }

    @Test
    public void findByStatusInativo() {

        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setId(1L);
        aluno1.setNome("Guilherme");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("176");

        aluno2.setId(2L);
        aluno2.setNome("Guy F.");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("1763");

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        List<Aluno> inativos = alunoRepository.findByStatusInativo();

        assertEquals(2, inativos.size());

    }

}
