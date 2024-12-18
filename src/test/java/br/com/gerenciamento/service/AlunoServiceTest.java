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
    public void atualizarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Maria");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);

        // Atualizar o nome e o turno do aluno
        aluno.setNome("Maria Silva");
        aluno.setTurno(Turno.NOTURNO);
        this.serviceAluno.save(aluno);

        Aluno alunoAtualizado = this.serviceAluno.getById(2L);
        Assert.assertEquals("Maria Silva", alunoAtualizado.getNome());
        Assert.assertEquals(Turno.NOTURNO, alunoAtualizado.getTurno());
    }

    @Test
    public void deletarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("789123");
        this.serviceAluno.save(aluno);

        // Deletar o aluno
        this.serviceAluno.deleteById(3L);

        // Verificar se o aluno foi deletado
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            this.serviceAluno.getById(3L);
        });
    }

    @Test
    public void buscarAlunoInexistente() {
        // Buscar por um ID que não existe
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            this.serviceAluno.getById(999L);
        });
    }

    @Test
    public void salvarAlunoComMatriculaDuplicada() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(4L);
        aluno1.setNome("João");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("111222");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(5L);
        aluno2.setNome("Ana");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("111222"); // Mesma matrícula do aluno1

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno2);
        });
    }
}