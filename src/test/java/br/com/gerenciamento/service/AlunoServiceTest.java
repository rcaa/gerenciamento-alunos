package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Autowired
    AlunoRepository alunoRepository;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Guilherme");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("176");

        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);

        Assert.assertEquals(aluno.getNome(), alunoRetorno.getNome());

    }

    @Test
    public void salvarSemNome() {

        alunoRepository.deleteAll();

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    @Test
    public void deleteById() {

        alunoRepository.deleteAll();

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Guilherme");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("176");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(1L);
        Assert.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(1L);
        });
    }

    @Test
    public void findActives() {

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
        aluno2.setNome("Guillaume");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("1763");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> ativos = this.serviceAluno.findByStatusAtivo();

        Assert.assertEquals(1, ativos.size());
        Assert.assertEquals("Guilherme", ativos.get(0).getNome());

    }

    @Test
    public void findInactives() {

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
        aluno2.setNome("Guillaume");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("1763");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> inativos = this.serviceAluno.findByStatusInativo();

        Assert.assertEquals(1, inativos.size());
        Assert.assertEquals("Guillaume", inativos.get(0).getNome());

    }

    @Test
    public void findAll() {

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
        aluno2.setNome("Guillaume");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("1763");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunos = this.serviceAluno.findAll();

        Assert.assertEquals(2, alunos.size());

    }
}