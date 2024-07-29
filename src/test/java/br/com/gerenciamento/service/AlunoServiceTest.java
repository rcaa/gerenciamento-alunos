package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    @Transactional
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
    @Transactional
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
    @Transactional
    public void findByStatusAtivo() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("alunoo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> encontrados = this.serviceAluno.findByStatusAtivo();
        Assert.assertTrue(encontrados.size() >= 1);
        Assert.assertTrue(encontrados.stream().anyMatch((a) -> a.getNome().equals("alunoo")));
    }

    @Test
    @Transactional
    public void findByStatusInativo() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("alunoo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> encontrados = this.serviceAluno.findByStatusInativo();
        Assert.assertTrue(encontrados.size() >= 1);
        Assert.assertTrue(encontrados.stream().anyMatch((a) -> a.getNome().equals("alunoo")));
    }

    @Test
    @Transactional
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("alunoo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> encontrados = this.serviceAluno.findByNomeContainingIgnoreCase("AlUnO");
        Assert.assertTrue(encontrados.size() >= 1);
        Assert.assertTrue(encontrados.stream().anyMatch((a) -> a.getNome().equals("alunoo")));
    }

    @Test
    @Transactional
    public void findAll() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("alunoo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> encontrados = this.serviceAluno.findAll();
        Assert.assertTrue(encontrados.size() >= 1);
        Assert.assertTrue(encontrados.stream().anyMatch((a) -> a.getNome().equals("alunoo")));
    }
}