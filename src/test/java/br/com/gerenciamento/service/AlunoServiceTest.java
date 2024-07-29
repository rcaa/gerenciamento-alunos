package br.com.gerenciamento.service;

import java.util.List;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    private AlunoRepository alunoRepository;

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
    public void salvarSemMatricula(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João da Silva");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void findByInativo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João da Silva");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();

        Assert.assertTrue(alunosInativos.contains(aluno));

    }

    @Test
    public void findAll(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("João da Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("123456");
        this.serviceAluno.save(aluno1);

        List<Aluno> resultado = serviceAluno.findAll();
        Assert.assertEquals(1, resultado.size());
    }

    @Test
    public void findByNomeContainingIgnoreCase(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("João da Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("123456");
        this.serviceAluno.save(aluno1);

        List<Aluno> resultado = serviceAluno.findByNomeContainingIgnoreCase("joão");
        Assert.assertEquals(1, resultado.size());
    }


}