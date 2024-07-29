package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;

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
    public void save () {
        // Verifica se o aluno está sendo salvo corretamente
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Mayara");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);
        Assert.assertNotNull(aluno.getId());
    }

    @Test
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Mayara");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);
        this.serviceAluno.deleteById(1L);
        try {
            this.serviceAluno.getById(1L);
            Assert.fail("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // O teste passa quando a exceção esperada é lançada
        }
    }

    @Test
    public void findAll() {
        // Verifica se os alunos estão sendo listados corretamente
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Mayara");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);
        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertFalse(alunos.isEmpty()); // Verifica se a lista está vazia
        boolean alunoPresente = false;
        for (Aluno a : alunos) { // Verifica se todos os atributos estão corretos
            if (a.getId().equals(aluno.getId())) {
                alunoPresente = true;
                Assert.assertEquals(aluno.getNome(), a.getNome());
                Assert.assertEquals(aluno.getTurno(), a.getTurno());
                Assert.assertEquals(aluno.getCurso(), a.getCurso());
                Assert.assertEquals(aluno.getStatus(), a.getStatus());
                Assert.assertEquals(aluno.getMatricula(), a.getMatricula());
                break;
            }
        }
        Assert.assertTrue(alunoPresente);
    }

    @Test
    public void findByStatusAtivo() {
        // Verifica se os alunos estão sendo encontrados pelo seu status corretamente
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Mayara");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);
        List<Aluno> alunos = this.serviceAluno.findByStatusAtivo();
        Assert.assertFalse(alunos.isEmpty()); // Verifica se a lista está vazia
        boolean alunoPresente = false;
        for (Aluno a : alunos) { // Verifica se os todos os atributos estão corretos
            if (a.getId().equals(aluno.getId())) {
                alunoPresente = true;
                Assert.assertEquals(aluno.getNome(), a.getNome());
                Assert.assertEquals(aluno.getTurno(), a.getTurno());
                Assert.assertEquals(aluno.getCurso(), a.getCurso());
                Assert.assertEquals(aluno.getStatus(), a.getStatus());
                Assert.assertEquals(aluno.getMatricula(), a.getMatricula());
                break;
            }
        }
        Assert.assertTrue(alunoPresente);
    }
}