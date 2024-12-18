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

import java.util.List;

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

    /**
     * Teste para verificar se o método findAll retorna todos os alunos salvos.
     */
    @Test
    public void listarTodos() {
        Aluno aluno01 = new Aluno();
        aluno01.setNome("Aluno 01");
        aluno01.setTurno(Turno.NOTURNO);
        aluno01.setCurso(Curso.ADMINISTRACAO);
        aluno01.setStatus(Status.ATIVO);
        aluno01.setMatricula("001");

        this.serviceAluno.save(aluno01);

        Aluno aluno02 = new Aluno();
        aluno02.setNome("Aluno 02");
        aluno02.setTurno(Turno.MATUTINO);
        aluno02.setCurso(Curso.CONTABILIDADE);
        aluno02.setStatus(Status.INATIVO);
        aluno02.setMatricula("002");

        this.serviceAluno.save(aluno02);

        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertEquals(2, alunos.size());
    }

    /**
     * Teste para verificar se o método deleteById deleta um aluno corretamente.
     */
    @Test
    public void deletarAluno() {
        Aluno aluno03 = new Aluno();
        aluno03.setNome("Aluno 03");
        aluno03.setMatricula("003");
        aluno03.setTurno(Turno.NOTURNO);
        aluno03.setCurso(Curso.ADMINISTRACAO);
        aluno03.setStatus(Status.ATIVO);

        this.serviceAluno.save(aluno03);

        Aluno alunoRetornado = this.serviceAluno.getById(aluno03.getId());
        Assert.assertEquals("003", alunoRetornado.getMatricula());

        this.serviceAluno.deleteById(aluno03.getId());
        Assert.assertThrows(RuntimeException.class, () -> this.serviceAluno.getById(aluno03.getId()));
    }

    /**
     * Teste para verificar se o método findByStatusAtivo retorna apenas alunos ativos.
     */
    @Test
    public void buscarPorStatusAtivo() {
        Aluno aluno04 = new Aluno();
        aluno04.setNome("Aluno 04");
        aluno04.setMatricula("004");
        aluno04.setCurso(Curso.INFORMATICA);
        aluno04.setTurno(Turno.NOTURNO);
        aluno04.setStatus(Status.ATIVO);

        this.serviceAluno.save(aluno04);

        Aluno aluno05 = new Aluno();
        aluno05.setNome("Aluno 05");
        aluno05.setMatricula("005");
        aluno05.setCurso(Curso.ENFERMAGEM);
        aluno05.setTurno(Turno.NOTURNO);
        aluno05.setStatus(Status.INATIVO);

        this.serviceAluno.save(aluno05);

        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();

        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> "004".equals(aluno.getMatricula())));
        Assert.assertFalse(alunosAtivos.stream().anyMatch(aluno -> "005".equals(aluno.getMatricula())));
    }

    /**
     * Teste para verificar se o método findByStatusInativo retorna apenas alunos inativos.
     */
    @Test
    public void buscarPorStatusInativo() {
        Aluno aluno06 = new Aluno();
        aluno06.setNome("Aluno 06");
        aluno06.setTurno(Turno.NOTURNO);
        aluno06.setCurso(Curso.ADMINISTRACAO);
        aluno06.setStatus(Status.INATIVO);
        aluno06.setMatricula("006");
        this.serviceAluno.save(aluno06);

        Aluno aluno07 = new Aluno();
        aluno07.setNome("Aluno 07");
        aluno07.setTurno(Turno.MATUTINO);
        aluno07.setCurso(Curso.CONTABILIDADE);
        aluno07.setStatus(Status.ATIVO);
        aluno07.setMatricula("007");
        this.serviceAluno.save(aluno07);

        List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> "006".equals(aluno.getMatricula())));
        Assert.assertFalse(alunosInativos.stream().anyMatch(aluno -> "007".equals(aluno.getMatricula())));
    }
}