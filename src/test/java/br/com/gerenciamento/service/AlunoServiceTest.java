package br.com.gerenciamento.service;

import java.util.List;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import java.util.NoSuchElementException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void atualizarAlunoExistente() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("789012");

        this.serviceAluno.save(aluno);

        aluno.setNome("Carlos Updated");
        aluno.setStatus(Status.ATIVO);
        this.serviceAluno.save(aluno);

        Aluno alunoAtualizado = this.serviceAluno.getById(3L);
        Assert.assertNotNull(alunoAtualizado);
        Assert.assertEquals("Carlos Updated", alunoAtualizado.getNome());
        Assert.assertEquals(Status.ATIVO, alunoAtualizado.getStatus());
    }

    @Test
    public void deletarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Marcos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("789012");

        this.serviceAluno.save(aluno);
        this.serviceAluno.deleteById(3L);

        Assert.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(3L);
        });
    }

    @Test
    public void buscarAlunosPorStatusAtivo() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setId(4L);
        alunoAtivo.setNome("Joana");
        alunoAtivo.setTurno(Turno.NOTURNO);
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setMatricula("321654");

        Aluno alunoInativo = new Aluno();
        alunoInativo.setId(5L);
        alunoInativo.setNome("Pedro");
        alunoInativo.setTurno(Turno.MATUTINO);
        alunoInativo.setCurso(Curso.BIOMEDICINA);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("987654");

        this.serviceAluno.save(alunoAtivo);
        this.serviceAluno.save(alunoInativo);

        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();
        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getId().equals(4L)));
        Assert.assertFalse(alunosAtivos.stream().anyMatch(aluno -> aluno.getId().equals(5L)));
    }

    @Test
    public void buscarTodosOsAlunos() {
        // Adiciona dois alunos para garantir que a lista não esteja vazia
        Aluno aluno1 = new Aluno();
        aluno1.setId(8L);
        aluno1.setNome("Fernanda");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("555666");

        Aluno aluno2 = new Aluno();
        aluno2.setId(9L);
        aluno2.setNome("Lucas");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("777888");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        // Verifica se a lista de alunos não está vazia
        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertFalse(alunos.isEmpty());
    }
}