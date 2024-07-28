package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @Transactional
    public void findByStatusAtivo(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Carlos");
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setTurno(Turno.MATUTINO);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Maria");
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setTurno(Turno.MATUTINO);

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        List<Aluno> ativos = alunoRepository.findByStatusAtivo();
        assertEquals(1, ativos.size(), "Deve retornar 1 aluno ativo");
        assertEquals("Carlos", ativos.get(0).getNome(), "O aluno ativo deve ser Carlos");
    }

    @Test
    @Transactional
    public void findByStatusAtivoSemStatus(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Carlos");
        aluno1.setMatricula("123456");
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setTurno(Turno.MATUTINO);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Maria");
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setTurno(Turno.MATUTINO);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            alunoRepository.save(aluno1);
            alunoRepository.save(aluno2);});
    }

    @Test
    @Transactional
    public void findByStatusInativo(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Carlos");
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setTurno(Turno.MATUTINO);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Maria");
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setTurno(Turno.MATUTINO);

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        List<Aluno> inativos = alunoRepository.findByStatusInativo();
        assertEquals(1, inativos.size(), "Deve retornar 1 aluno inativo");
        assertEquals("Maria", inativos.get(0).getNome(), "O aluno inativo deve ser Maria");
    }

    @Test
    @Transactional
    public void findByNome(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Carlos Santos");
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setTurno(Turno.MATUTINO);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Maria");
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setTurno(Turno.MATUTINO);

        Aluno aluno3 = new Aluno();
        aluno3.setId(3L);
        aluno3.setNome("Carlos Ferreira");
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("987654");
        aluno3.setCurso(Curso.ENFERMAGEM);
        aluno3.setTurno(Turno.MATUTINO);

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);
        alunoRepository.save(aluno3);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Carlos Ferreira");
        assertEquals(1, alunos.size(), "Deve retornar 1 aluno");
        assertEquals("Carlos Ferreira", alunos.get(0).getNome(), "O aluno deve ser Carlos");
    }
}
