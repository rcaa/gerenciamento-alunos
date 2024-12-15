package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
        aluno.setId(77L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void TesteBuscarTodosAlunos() {
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.INFORMATICA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        Aluno a2 = new Aluno();
        a2.setId(2L);
        a2.setNome("Jeasiel Abner");
        a2.setTurno(Turno.NOTURNO);
        a2.setCurso(Curso.CONTABILIDADE);
        a2.setStatus(Status.INATIVO);
        a2.setMatricula("126");

        serviceAluno.save(a1);
        serviceAluno.save(a2);

        List<Aluno> alunos = serviceAluno.findAll();
        assertEquals(2, alunos.size());
        assertEquals("123", alunos.get(0).getMatricula());
        assertEquals("126", alunos.get(1).getMatricula());
    }

    @Test
    public void TesteBuscarAlunoPorNome() {
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.INFORMATICA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        serviceAluno.save(a1);

        List<Aluno> alunos = serviceAluno.findByNomeContainingIgnoreCase("Arthur");
        assertEquals(1, alunos.size());
        assertEquals("123", alunos.get(0).getMatricula());
    }

    
    @Test
    public void TesteBuscarAlunoAtivo() {
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.INFORMATICA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        Aluno a2 = new Aluno();
        a2.setId(2L);
        a2.setNome("Jeasiel Abner");
        a2.setTurno(Turno.NOTURNO);
        a2.setCurso(Curso.CONTABILIDADE);
        a2.setStatus(Status.INATIVO);
        a2.setMatricula("126");

        serviceAluno.save(a1);
        serviceAluno.save(a2);

        List<Aluno> alunos = serviceAluno.findByStatusAtivo();
        assertEquals(1, alunos.size());
        assertEquals("123", alunos.get(0).getMatricula());
    }

    @Test
    public void TesteBuscarAlunoInativo() {
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.INFORMATICA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        Aluno a2 = new Aluno();
        a2.setId(2L);
        a2.setNome("Jeasiel Abner");
        a2.setTurno(Turno.NOTURNO);
        a2.setCurso(Curso.CONTABILIDADE);
        a2.setStatus(Status.INATIVO);
        a2.setMatricula("126");

        serviceAluno.save(a1);
        serviceAluno.save(a2);

        List<Aluno> alunos = serviceAluno.findByStatusInativo();
        assertEquals(1, alunos.size());
        assertEquals("126", alunos.get(0).getMatricula());
    }
    //Houve uma tentativa de testar a função de deleção, mas a mesma simplesmente
    //se recusa a funcionar, ou faz com que outros testes deem errado.
    //Então, os testes são muito parecidos com os de AlunoRepositoryTest.
}