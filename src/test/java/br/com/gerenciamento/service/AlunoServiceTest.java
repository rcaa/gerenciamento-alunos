package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
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
        Assert.assertEquals(5, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.stream().anyMatch(a -> a.getNome().equals("Carlos Silva")));
        Assert.assertTrue(alunosAtivos.stream().anyMatch(a -> a.getNome().equals("Ana Lima")));
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(6L);
        aluno1.setNome("Bruno Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("333333");

        Aluno aluno2 = new Aluno();
        aluno2.setId(7L);
        aluno2.setNome("Bruna Almeida");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("444444");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosEncontrados = this.serviceAluno.findByNomeContainingIgnoreCase("bru");
        Assert.assertNotNull("Lista de alunos encontrados não pode ser nula", alunosEncontrados);
        Assert.assertEquals("Número de alunos encontrados está incorreto", 2, alunosEncontrados.size());
        Assert.assertTrue("Aluno Bruno Silva não encontrado", alunosEncontrados.stream().anyMatch(a -> a.getNome().equals("Bruno Silva")));
        Assert.assertTrue("Aluno Bruna Almeida não encontrado", alunosEncontrados.stream().anyMatch(a -> a.getNome().equals("Bruna Almeida")));
    }

    @Test
    public void deletarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(8L);
        aluno.setNome("Mariana Costa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("555555");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(8L);

        Assert.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(8L);
        });
    }
}