package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.*;
import org.junit.jupiter.api.Assertions;
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
    public void salvarAlunoValido() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");

        this.serviceAluno.save(aluno);
        Aluno alunoSalvo = this.serviceAluno.getById(2L);

        Assertions.assertNotNull(alunoSalvo);
        Assertions.assertEquals("Carlos", alunoSalvo.getNome());
        Assertions.assertEquals(Turno.MATUTINO, alunoSalvo.getTurno());
        Assertions.assertEquals(Curso.INFORMATICA, alunoSalvo.getCurso());
        Assertions.assertEquals(Status.ATIVO, alunoSalvo.getStatus());
        Assertions.assertEquals("654321", alunoSalvo.getMatricula());
    }



    @Test
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(1L);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(1L);
        });
    }

    @Test
    public void deleteByIdInexistente() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.deleteById(999L);
            this.serviceAluno.getById(999L);
        });
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Maria Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("João Pereira");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.CONTABILIDADE);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("654321");
        serviceAluno.save(aluno2);

        List<Aluno> alunosEncontrados = serviceAluno.findByNomeContainingIgnoreCase("maria");

        Assertions.assertEquals(1, alunosEncontrados.size());
        Assertions.assertEquals("Maria Silva", alunosEncontrados.get(0).getNome());
    }
}