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
    public void salvarAlunoSemCurso(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456789");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});
    }
    @Test
    public void salvarAlunoSemTurno(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("1234");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});
    }
    @Test
    public void salvarAlunoSemMatricula(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(Status.ATIVO);
        aluno.setCurso(Curso.ENFERMAGEM);
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});
    }

    @Test
    public void alterarNomeAluno(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setMatricula("0098737");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(Status.ATIVO);
        aluno.setCurso(Curso.ENFERMAGEM);
        this.serviceAluno.save(aluno);
        aluno.setNome("Carla");
        this.serviceAluno.save(aluno);
        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Carla"));
    }

}