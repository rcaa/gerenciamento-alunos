package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
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
    public void salvarAlunoComDadosValidos() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Carlos Silva");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("20241234");

        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertEquals("Carlos Silva", alunoRetorno.getNome());
    }

    @Test
    public void naoSalvarAlunoSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("20241234");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceAluno.save(aluno);
        });
    }

    @Test
    public void atualizarStatusAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Lucas Oliveira");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("20242345");
        serviceAluno.save(aluno);

        aluno.setStatus(Status.ATIVO);
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);

        Assert.assertEquals(Status.ATIVO, alunoRetorno.getStatus());
    }

    @Test
    public void deletarAlunoPorId() {
        Aluno aluno = new Aluno();
        aluno.setNome("Fernanda Lima");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("20243456");
        
        
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);


        serviceAluno.deleteById(alunoRetorno.getId());

        Assert.assertThrows(NoSuchElementException.class, () -> {
            serviceAluno.getById(alunoRetorno.getId());
        });
    }
}