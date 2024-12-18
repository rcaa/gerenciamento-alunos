package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    private Aluno criarAlunoGenerico1() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        return aluno;
    }

    private Aluno criarAlunoGenerico2() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Jobson");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("654321");
        return aluno;
    }

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
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void deleteById() {
        Aluno aluno = criarAlunoGenerico1();
        serviceAluno.save(aluno);

        serviceAluno.deleteById(1L);
        assertThrows(Exception.class, () -> serviceAluno.getById(1L));
    }

    @Test
    public void findByNomeIgnoreCase() {
        Aluno aluno1 = criarAlunoGenerico1();

        serviceAluno.save(aluno1);

        List<Aluno> alunosEncontrados = serviceAluno.findByNomeContainingIgnoreCase("VINICIUS");
        assertEquals(1, alunosEncontrados.size());

    }

    @Test
    public void findByStatusInativo() {
        Aluno aluno1 = criarAlunoGenerico2();

        serviceAluno.save(aluno1);

        List<Aluno> alunosEncontrados = serviceAluno.findByStatusInativo();

        assertEquals(1, alunosEncontrados.size());
        assertEquals(Status.INATIVO, alunosEncontrados.get(0).getStatus());
    }

    @Test
    public void findByStatusAtivo() {
        Aluno aluno1 = criarAlunoGenerico1();

        serviceAluno.save(aluno1);

        List<Aluno> alunosEncontrados = serviceAluno.findByStatusAtivo();

        assertEquals(1, alunosEncontrados.size());
        assertEquals(Status.ATIVO, alunosEncontrados.get(0).getStatus());
    }
}