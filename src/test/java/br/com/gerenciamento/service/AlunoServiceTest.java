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
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);

        aluno.setNome("Carlos Silva");
        this.serviceAluno.save(aluno);

        Aluno alunoAtualizado = this.serviceAluno.getById(2L);
        Assert.assertEquals("Carlos Silva", alunoAtualizado.getNome());
    }

    @Test
    public void deletarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Ana");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("789012");
        this.serviceAluno.save(aluno);

        this.serviceAluno.delete(aluno.getId());

        Aluno alunoDeletado = this.serviceAluno.getById(3L);
        Assert.assertNull(alunoDeletado);
    }

    @Test
    public void buscarAlunoPorId() {
        Aluno aluno = new Aluno();
        aluno.setId(4L);
        aluno.setNome("Mariana");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("345678");
        this.serviceAluno.save(aluno);

        Aluno alunoEncontrado;
        alunoEncontrado = this.serviceAluno.getById(4L);
        Assert.assertNotNull(alunoEncontrado);
        Assert.assertEquals("Mariana", alunoEncontrado.getNome());
    }

    @Test
    public void salvarAlunoComMatriculaDuplicada() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(5L);
        aluno1.setNome("Pedro");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.BIOMEDICINA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("112233");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(6L);
        aluno2.setNome("Paulo");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("112233");
        ConstraintViolationException constraintViolationException = Assert.assertThrows(ConstraintViolationException.class, () ->
                this.serviceAluno.save(aluno2));
    }
}
