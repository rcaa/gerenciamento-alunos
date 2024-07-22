package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

	@Test
    public void findAll()
    {
        for (int a = 1; a <= 3; a++)
        {
            Aluno aluno = new Aluno();
            aluno.setId((long)a);
            aluno.setNome("aaaaa"+a);
            aluno.setTurno(Turno.MATUTINO);
            aluno.setCurso(Curso.ADMINISTRACAO);
            aluno.setStatus(Status.ATIVO);
            aluno.setMatricula("aaa"+a);
            this.serviceAluno.save(aluno);
        }
        List<Aluno> alunos = this.serviceAluno.findAll();
        assertEquals(3, alunos.size());
    }

    @Test
    public void deleteById()
    {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
        this.serviceAluno.save(aluno);
        this.serviceAluno.deleteById(1L);
        assertThrows(java.util.NoSuchElementException.class, () -> {this.serviceAluno.getById(1L);});
    }

    @Test
    public void findByStatusAtivo()
    {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
        this.serviceAluno.save(aluno);
        List<Aluno> alunos = this.serviceAluno.findByStatusAtivo();
        Assert.assertTrue(alunos.get(0).getNome().equals("aaaaa"));
    }

    @Test
    public void findByNomeContainingIgnoreCase()
    {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("AaAaA");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
        this.serviceAluno.save(aluno);
        List<Aluno> alunos = this.serviceAluno.findByNomeContainingIgnoreCase("aAaAa");
        Assert.assertTrue(alunos.get(0).getNome().equals("AaAaA"));
	}
}