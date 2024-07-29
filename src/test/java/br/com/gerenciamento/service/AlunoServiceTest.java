package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

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
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void atualizarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Pedro");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);

        aluno.setNome("Pedro Silva");
        this.serviceAluno.save(aluno);

        Aluno alunoAtualizado = this.serviceAluno.getById(2L);
        Assert.assertTrue(alunoAtualizado.getNome().equals("Pedro Silva"));
    }

    @Test
    public void testFindByStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(4L);
        aluno1.setNome("Carlos Silva"); 
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("111111");

        Aluno aluno2 = new Aluno();
        aluno2.setId(5L);
        aluno2.setNome("Ana Lima"); 
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("222222");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();
        Assert.assertNotNull(alunosAtivos);
        Assert.assertEquals(4, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.stream().anyMatch(a -> a.getNome().equals("Carlos Silva")));
        Assert.assertTrue(alunosAtivos.stream().anyMatch(a -> a.getNome().equals("Ana Lima")));
    }
}