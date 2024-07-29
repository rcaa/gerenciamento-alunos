package br.com.gerenciamento.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    @Transactional
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(aluno.getId());
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    @Transactional
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    @Transactional
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(aluno.getId());
        Assert.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(aluno.getId());});
        
    }

    @Test
    @Transactional
    public void findAll() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Vinicius");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Alice");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123456");
        this.serviceAluno.save(aluno2);

        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertEquals(2, alunos.size());
        
    }

    @Test
    @Transactional
    public void findByStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Vinicius");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Alice");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("123456");
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosStatusAtivos = this.serviceAluno.findByStatusAtivo();
        Assert.assertEquals(1, alunosStatusAtivos.size());
        
    }

    @Test
    @Transactional
    public void findByStatusInativo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Vinicius");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("123456");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Alice");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("123456");
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosStatusInativos = this.serviceAluno.findByStatusInativo();
        Assert.assertEquals(2, alunosStatusInativos.size());
        
    }
}