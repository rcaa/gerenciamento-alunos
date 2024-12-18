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
    public void testCreateAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos Silva");
        aluno.setEmail("carlos@gmail.com");
        aluno.setCurso(Curso.CIENCIA_DA_COMPUTACAO);
        alunoService.salvarAluno(aluno);

        Assertions.assertNotNull(aluno.getId());
    }

    @Test
    public void salvarComTurnoInvalido() {
    Aluno aluno = new Aluno();
    aluno.setNome("Joana Silva");
    aluno.setTurno(null); // Turno inválido
    aluno.setCurso(Curso.DIREITO);
    aluno.setStatus(Status.ATIVO);
    aluno.setMatricula("456789");

    Assert.assertThrows(ConstraintViolationException.class, () -> {
        this.serviceAluno.save(aluno);
    });
}

    @Test
    public void testUpdateAluno() {
        Aluno aluno = alunoService.buscarPorId(1L);
        aluno.setNome("Carlos Atualizado");
        alunoService.salvarAluno(aluno);

        Aluno atualizado = alunoService.buscarPorId(1L);
        Assertions.assertEquals("Carlos Atualizado", atualizado.getNome());
    }

    @Test
    public void testDeleteAluno() {
        alunoService.excluirAluno(2L);
        Aluno aluno = alunoService.buscarPorId(2L);
        Assertions.assertNull(aluno);
    }
}