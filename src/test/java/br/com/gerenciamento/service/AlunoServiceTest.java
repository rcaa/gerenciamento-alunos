package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("Vinicius", alunoRetorno.getNome());
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    @Test
    public void salvarAlunoValido() {
        // criação de um aluno válido para teste
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Maria");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("987654");

        // salvando e verificando o aluno
        this.serviceAluno.save(aluno);
        Aluno alunoRetornado = this.serviceAluno.getById(3L);

        assertNotNull(alunoRetornado);
        assertEquals("Maria", alunoRetornado.getNome());
        assertEquals("987654", alunoRetornado.getMatricula());
    }

    @Test
    public void deletarAluno() {
        // criação de um aluno para ser deletado.
        Aluno aluno = new Aluno();
        aluno.setId(4L);
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("654987");

        // salvando e deletando o aluno que foi feito
        this.serviceAluno.save(aluno);
        this.serviceAluno.deleteById(4L);

        // verificando se o aluno foi deletado
        assertThrows(RuntimeException.class, () -> {
            this.serviceAluno.getById(4L);
        });
    }

}