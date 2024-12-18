package br.com.gerenciamento.repository;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void testeSemAlunosAtivos() {
        List<Aluno> alunosAtivos = repositoryAluno.findByStatusAtivo();
        Assert.assertTrue(alunosAtivos.isEmpty());

    }

    @Test
    public void testeSemAlunosInativos() {
        List<Aluno> alunosInativos = repositoryAluno.findByStatusInativo();
        Assert.assertTrue(alunosInativos.isEmpty());

    }

    @Test
    public void testeExistemAlunosInativos() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.repositoryAluno.save(aluno);

        List<Aluno> alunosAtivos = this.repositoryAluno.findByStatusAtivo();

        Assert.assertNotNull(alunosAtivos);
    }

    @Test

    public void testeBuscarPorNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.repositoryAluno.save(aluno);

        List<Aluno> alunoEncontrado = repositoryAluno.findByNomeContainingIgnoreCase("vinicius");
        Assert.assertEquals("Vinicius", alunoEncontrado.get(0).getNome());

    }
}
