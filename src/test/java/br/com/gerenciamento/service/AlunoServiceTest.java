package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
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
    public void encontrarPorID() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Juliana Natasha");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("160502");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Juliana Natasha"));
    }

    @Test
    public void localizaTodos() {
        Aluno teste1 = new Aluno();
        teste1.setNome("Teste 01");
        teste1.setTurno(Turno.MATUTINO);
        teste1.setCurso(Curso.ENFERMAGEM);
        teste1.setStatus(Status.ATIVO);
        teste1.setMatricula("150898");

        Aluno teste2 = new Aluno();
        teste2.setNome("Teste 02");
        teste2.setTurno(Turno.NOTURNO);
        teste2.setCurso(Curso.CONTABILIDADE);
        teste2.setStatus(Status.ATIVO);
        teste2.setMatricula("040402");

        this.serviceAluno.save(teste1);
        this.serviceAluno.save(teste2);

        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertEquals(2, alunos.size());
    }

    @Test
    public void procurarAlunosAtivos() {
       
        Aluno inativo = new Aluno();
        inativo.setNome("Aluno Inativo");
        inativo.setTurno(Turno.NOTURNO);
        inativo.setCurso(Curso.ADMINISTRACAO);
        inativo.setStatus(Status.INATIVO);
        inativo.setMatricula("789101");
       
        Aluno ativo = new Aluno();
        ativo.setNome("Aluno Ativo");
        ativo.setTurno(Turno.MATUTINO);
        ativo.setCurso(Curso.DIREITO);
        ativo.setStatus(Status.ATIVO);
        ativo.setMatricula("400289");

        this.serviceAluno.save(ativo);
        this.serviceAluno.save(inativo);

        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();
        Assert.assertEquals(1, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.get(0).getStatus() == Status.ATIVO);
    }

    @Test
    public void excluirAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Fulano");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("110572");
        this.serviceAluno.save(aluno);
        this.serviceAluno.deleteById(1L);
        Assert.assertThrows(RuntimeException.class, () -> {
            this.serviceAluno.getById(1L);
        });
    }

    @Test
    public void salvarNomeVazio() {
        Aluno teste = new Aluno();
        teste.setId(1L);
        teste.setTurno(Turno.NOTURNO);
        teste.setCurso(Curso.BIOMEDICINA);
        teste.setStatus(Status.ATIVO);
        teste.setMatricula("090909");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(teste);
        });
    }

}