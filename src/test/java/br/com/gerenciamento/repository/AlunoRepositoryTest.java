package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno alunoAtivo;
    private Aluno alunoInativo;

    @Before
    public void setUp() {
        alunoAtivo = new Aluno();
        alunoAtivo.setNome("Vinicius");
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setCurso(Curso.INFORMATICA);
        alunoAtivo.setTurno(Turno.NOTURNO);
        alunoAtivo.setMatricula("123456");
        alunoRepository.save(alunoAtivo);

        alunoInativo = new Aluno();
        alunoInativo.setNome("Carlos");
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setCurso(Curso.ADMINISTRACAO);
        alunoInativo.setTurno(Turno.MATUTINO);
        alunoInativo.setMatricula("123457");
        alunoRepository.save(alunoInativo);
    }

    @After
    public void tearDown() {
        alunoRepository.delete(alunoAtivo);
        alunoRepository.delete(alunoInativo);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void findByStatusAtivo() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        Assert.assertNotNull(alunosAtivos);
        Assert.assertFalse(alunosAtivos.isEmpty());
        Assert.assertTrue(alunosAtivos.stream().allMatch(aluno -> Status.ATIVO.equals(aluno.getStatus())));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void findByStatusInativo() {
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assert.assertNotNull(alunosInativos);
        Assert.assertFalse(alunosInativos.isEmpty());
        Assert.assertTrue(alunosInativos.stream().allMatch(aluno -> Status.INATIVO.equals(aluno.getStatus())));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void findByNomeContainingIgnoreCase() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Vinicius");
        Assert.assertNotNull(alunos);
        Assert.assertFalse(alunos.isEmpty());
        Assert.assertTrue(alunos.stream().allMatch(aluno -> aluno.getNome().toLowerCase().contains("vinicius".toLowerCase())));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void findByNomeContainingIgnoreCaseNull() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase(null);
        Assert.assertNotNull(alunos);
        Assert.assertTrue(alunos.isEmpty());
    }
}
