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
    public void buscarAlunoInexistente() {
        Assert.assertThrows(RuntimeException.class, () -> {
            this.serviceAluno.getById(999L);
        });
    }

    @Test
    public void atualizarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Gerson");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");

        this.serviceAluno.save(aluno);

        aluno.setNome("Gerson Atualizado");
        aluno.setTurno(Turno.NOTURNO);

        this.serviceAluno.save(aluno);

        Assert.assertEquals("Gerson Atualizado", aluno.getNome());
        Assert.assertEquals(Turno.NOTURNO, aluno.getTurno());
    }

    @Test
    public void excluirAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(6L);
        aluno.setNome("Everton");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("222333");

        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(6L);

        Assert.assertThrows(RuntimeException.class, () -> {
            this.serviceAluno.getById(6L);
        });
    }

    @Test
    public void testSalvarNomeComTamanhoInvalido() {
        // Teste para nome com menos de 5 caracteres
        Aluno alunoComNomeCurto = new Aluno();
        alunoComNomeCurto.setId(8L);
        alunoComNomeCurto.setNome("Luis");
        alunoComNomeCurto.setTurno(Turno.NOTURNO);
        alunoComNomeCurto.setCurso(Curso.ADMINISTRACAO);
        alunoComNomeCurto.setStatus(Status.ATIVO);
        alunoComNomeCurto.setMatricula("987654");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(alunoComNomeCurto);
        });

        // Teste para nome com mais de 35 caracteres
        Aluno alunoComNomeLongo = new Aluno();
        alunoComNomeLongo.setId(9L);
        alunoComNomeLongo.setNome("Pedro Guilherme Gabriel Barbosa Henrique Arthur");
        alunoComNomeLongo.setTurno(Turno.MATUTINO);
        alunoComNomeLongo.setCurso(Curso.BIOMEDICINA);
        alunoComNomeLongo.setStatus(Status.ATIVO);
        alunoComNomeLongo.setMatricula("112233");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(alunoComNomeLongo);
        });
    }
}