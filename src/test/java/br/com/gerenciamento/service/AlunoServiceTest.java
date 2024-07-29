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
import org.springframework.transaction.TransactionSystemException;

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

    // teste 1 - Salvar sem o aluno digitar um turno
    @Test
    public void salvarSemTurno() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Gustavo Henrique");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    // teste 2 - salvar sem o aluno digitar o curso
    @Test
    public void salvarSemCurso() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Gustavo Henrique");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    // teste 3 - salvar aluno sem curso
    @Test
    public void salvarSemStatus() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Gustavo Henrique");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("123456");
        Assert.assertThrows(TransactionSystemException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    // teste 4 - salvar aluno sem matricula
    @Test
    public void salvarSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Gustavo Henrique");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        Assert.assertThrows(TransactionSystemException.class, () -> {
                this.serviceAluno.save(aluno);});
    }
}