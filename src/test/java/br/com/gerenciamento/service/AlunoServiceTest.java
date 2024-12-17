package br.com.gerenciamento.service;

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
import jakarta.validation.ConstraintViolationException;
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
    public void salvarComNomeValido() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Jefferson");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");

        this.serviceAluno.save(aluno);

        Aluno alunoSalvo = this.serviceAluno.getById(1L);
        Assert.assertNotNull(alunoSalvo); 
        Assert.assertEquals("Jefferson", alunoSalvo.getNome());
        Assert.assertEquals("654321", alunoSalvo.getMatricula());
    }

    @Test
    public void salvarComMatriculaInvalida() {
    Aluno aluno = new Aluno();
    aluno.setId(1L);
    aluno.setNome("Carlos");
    aluno.setTurno(Turno.NOTURNO);
    aluno.setCurso(Curso.DIREITO);
    aluno.setStatus(Status.ATIVO);
    aluno.setMatricula("12"); 
    
    Assert.assertThrows(ConstraintViolationException.class, () -> {
        this.serviceAluno.save(aluno);
    });
}

}