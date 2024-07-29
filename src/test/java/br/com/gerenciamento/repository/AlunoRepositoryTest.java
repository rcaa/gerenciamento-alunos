package br.com.gerenciamento.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
    private AlunoRepository alunoRepository;

    @Before
    public void setUp() {
        // Limpa o banco de dados antes de cada teste
        alunoRepository.deleteAll();
    }

    @Test
    public void testFindByStatusAtivo() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Aluno Ativo");
        alunoAtivo.setTurno(Turno.NOTURNO);
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setMatricula("12345");
        alunoRepository.save(alunoAtivo);

        Aluno alunoInativo = new Aluno();
        alunoInativo.setNome("Aluno Inativo");
        alunoInativo.setTurno(Turno.MATUTINO);
        alunoInativo.setCurso(Curso.INFORMATICA);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("67890");
        alunoRepository.save(alunoInativo);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        Assert.assertTrue(alunosAtivos.size() == 1);
        Assert.assertEquals("Aluno Ativo", alunosAtivos.get(0).getNome());
    }

    @Test
    public void testFindByStatusInativo() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Aluno Ativo");
        alunoAtivo.setTurno(Turno.NOTURNO);
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setMatricula("12345");
        alunoRepository.save(alunoAtivo);

        Aluno alunoInativo = new Aluno();
        alunoInativo.setNome("Aluno Inativo");
        alunoInativo.setTurno(Turno.MATUTINO);
        alunoInativo.setCurso(Curso.INFORMATICA);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("67890");
        alunoRepository.save(alunoInativo);

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assert.assertTrue(alunosInativos.size() == 1);
        Assert.assertEquals("Aluno Inativo", alunosInativos.get(0).getNome());
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Teste");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("12345");
        alunoRepository.save(aluno);

        List<Aluno> alunosEncontrados = alunoRepository.findByNomeContainingIgnoreCase("teste");
        Assert.assertFalse(alunosEncontrados.isEmpty());
        Assert.assertEquals("Aluno Teste", alunosEncontrados.get(0).getNome());
    }

    @Test
    public void testFindByNomeContainingIgnoreCaseNaoEncontrado() {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Teste");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("12345");
        alunoRepository.save(aluno);

        List<Aluno> alunosEncontrados = alunoRepository.findByNomeContainingIgnoreCase("não encontrado");
        Assert.assertTrue(alunosEncontrados.isEmpty());
    }
}
