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
    public void salvarSemMatricula() {
        Aluno alunoSemMatricula = new Aluno();
        alunoSemMatricula.setId(2L);
        alunoSemMatricula.setNome("Ana");
        alunoSemMatricula.setTurno(Turno.NOTURNO);
        alunoSemMatricula.setCurso(Curso.ADMINISTRACAO);
        alunoSemMatricula.setStatus(Status.ATIVO);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(alunoSemMatricula);
        });
    }

    @Test
    public void findByStatusInativo() {
        Aluno alunoInativo = new Aluno();
        alunoInativo.setId(3L);
        alunoInativo.setNome("Maria");
        alunoInativo.setTurno(Turno.NOTURNO);
        alunoInativo.setCurso(Curso.ADMINISTRACAO);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("345678");
        this.serviceAluno.save(alunoInativo);
        
        List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Maria")));
    }

    @Test
    public void salvarSemCurso() {
        Aluno alunoSemCurso = new Aluno();
        alunoSemCurso.setNome("Pedro");
        alunoSemCurso.setTurno(Turno.NOTURNO);
        alunoSemCurso.setStatus(Status.ATIVO);
        alunoSemCurso.setMatricula("567890");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceAluno.save(alunoSemCurso);
        });
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno alunoRoberto = new Aluno();
        alunoRoberto.setNome("Maria Julia");
        alunoRoberto.setTurno(Turno.NOTURNO);
        alunoRoberto.setCurso(Curso.ADMINISTRACAO);
        alunoRoberto.setStatus(Status.ATIVO);
        alunoRoberto.setMatricula("789012");
        serviceAluno.save(alunoRoberto);


        List<Aluno> alunosEncontrados = serviceAluno.findByNomeContainingIgnoreCase("juli");
        Assert.assertTrue(alunosEncontrados.stream().anyMatch(aluno -> aluno.getNome().equals("Maria Julia")));
    }
}