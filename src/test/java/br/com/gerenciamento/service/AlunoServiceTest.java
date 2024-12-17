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

import java.util.NoSuchElementException;

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

    // #1 - Tenta salvar sem setar um numero de matricula
    @Test
    public void salvarSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setNome("Luis Felipe");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula(null);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    // #2 - Testa a funcionalidade de excluir cadastro de aluno
    @Test
    public void deletarAlunoExistente() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");

        this.serviceAluno.save(aluno);
        this.serviceAluno.deleteById(2L);

        Assert.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(2L);
        });
    }

    // #3 - Testa a funcionalidade de atualizar cadastro de aluno
    @Test
    public void atualizarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana Maria");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("789101");

        this.serviceAluno.save(aluno);

        Aluno alunoSalvo = this.serviceAluno.getById(aluno.getId());
        Assert.assertNotNull(alunoSalvo);

        alunoSalvo.setNome("Ana Maria da Silva");
        alunoSalvo.setCurso(Curso.CONTABILIDADE);
        this.serviceAluno.save(alunoSalvo);

        Aluno alunoAtualizado = this.serviceAluno.getById(alunoSalvo.getId());
        Assert.assertEquals("Ana Maria da Silva", alunoAtualizado.getNome());
        Assert.assertEquals(Curso.CONTABILIDADE, alunoAtualizado.getCurso());
    }

    // #4 - Testa o tratamento de exceções quando for realizado uma busca por um aluno com ID inexistente
    @Test
    public void buscarAlunoIdInexistente() {
        Assert.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(999L);
        });
    }


}