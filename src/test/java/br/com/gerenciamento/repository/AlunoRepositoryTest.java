package br.com.gerenciamento.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository repositoryAluno;

    @Test
    public void findByStatusAtivo() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.repositoryAluno.save(aluno);
        List<Aluno> alunosAtivos = this.repositoryAluno.findByStatusAtivo();

        Assert.assertNotNull(alunosAtivos);
    }

    @Test
    public void findByStatusInativo() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Marco");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("443322");
        this.repositoryAluno.save(aluno);
        List<Aluno> alunosInativos = this.repositoryAluno.findByStatusInativo();

        Assert.assertNotNull(alunosInativos);
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Helder");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("995522");
        this.repositoryAluno.save(aluno);
        List<Aluno> alunos = this.repositoryAluno.findByNomeContainingIgnoreCase("helder");

        Assert.assertNotNull(alunos);
    }

    @Test
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setId(4L);
        aluno.setNome("Joãozinho");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("883377");
        this.repositoryAluno.save(aluno);
        this.repositoryAluno.deleteById(aluno.getId());

        Assert.assertFalse(this.repositoryAluno.existsById(aluno.getId()));
    }

}
