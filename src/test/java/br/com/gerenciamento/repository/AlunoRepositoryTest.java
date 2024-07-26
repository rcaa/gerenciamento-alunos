package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void findByStatusAtivo() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Analice Melo");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Felipe Battisti");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("234567");
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Thiago Almeida");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("345678");
        alunoRepository.save(aluno3);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        Assert.assertEquals(2, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getNome().equals("Analice Melo")));
        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getNome().equals("Thiago Almeida")));
    }

    @Test
    public void findByStatusInativo() {
        alunoRepository.deleteAll();
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Analice Melo");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Felipe Battisti");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("234567");
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Thiago Almeida");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("345678");
        alunoRepository.save(aluno3);

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assert.assertEquals(1, alunosInativos.size());
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Felipe Battisti")));
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Analice Melo");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Felipe Battisti");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("234567");
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Thiago Almeida");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("345678");
        alunoRepository.save(aluno3);

        List<Aluno> alunosEncontrados = alunoRepository.findByNomeContainingIgnoreCase("lice");
        Assert.assertEquals(1, alunosEncontrados.size());
        Assert.assertTrue(alunosEncontrados.stream().anyMatch(aluno -> aluno.getNome().equals("Analice Melo")));
    }

    @Test
    public void salvarEEncontrarPorId() {
        alunoRepository.deleteAll();

        Aluno aluno = new Aluno();
        aluno.setNome("Analice Melo");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("456789");
        Aluno alunoSalvo = alunoRepository.save(aluno);

        Aluno alunoEncontrado = alunoRepository.findById(alunoSalvo.getId()).orElse(null);
        Assert.assertNotNull(alunoEncontrado);
        Assert.assertEquals("Analice Melo", alunoEncontrado.getNome());
    }
}
