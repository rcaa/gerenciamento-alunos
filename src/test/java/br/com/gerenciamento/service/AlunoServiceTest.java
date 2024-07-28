package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
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
    public void findAll() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Aluno 1");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Aluno 2");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("789101");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertEquals(2, alunos.size());
    }

    @Test
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(1L);
        Assert.assertThrows(RuntimeException.class, () -> {
            this.serviceAluno.getById(1L);
        });
    }

    @Test
    public void findByStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Aluno 1");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Aluno 2");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("789101");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();
        Assert.assertEquals(1, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.get(0).getStatus() == Status.ATIVO);
    }

    @Test
    public void findByStatusInativo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Aluno 1");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Aluno 2");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("789101");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();
        Assert.assertEquals(1, alunosInativos.size());
        Assert.assertTrue(alunosInativos.get(0).getStatus() == Status.INATIVO);
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Aluno 1");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("aluno 2");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("789101");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunos = this.serviceAluno.findByNomeContainingIgnoreCase("aluno");
        Assert.assertEquals(2, alunos.size());
    }
}
