package br.com.gerenciamento.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void findByStatusAtivo() {

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Jorge");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("369258");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("156563");
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Alice");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.DIREITO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("554136");
        alunoRepository.save(aluno3);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        Assert.assertEquals(2, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getMatricula().equals("369258")));
        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getMatricula().equals("156563")));
    }

    @Test
    public void findByStatusInativo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Jorge");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("369258");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("156563");
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Alice");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.DIREITO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("554136");
        alunoRepository.save(aluno3);

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assert.assertEquals(2, alunosInativos.size());
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getMatricula().equals("369258")));
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getMatricula().equals("156563")));
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Jorge");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("369258");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("156563");
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Alice");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.DIREITO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("554136");
        alunoRepository.save(aluno3);

        List<Aluno> alunosComNomeMaria = alunoRepository.findByNomeContainingIgnoreCase("Mari");
        Assert.assertEquals(1, alunosComNomeMaria.size());
        Assert.assertTrue(alunosComNomeMaria.stream().anyMatch(aluno -> aluno.getMatricula().equals("156563")));
    }

    @Test
    public void findByNomeContainingIgnoreCaseAndStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Jorge");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("369258");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("156563");
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Alice");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.DIREITO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("554136");
        alunoRepository.save(aluno3);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("maria");
        Assert.assertEquals(1, alunos.size());
        Assert.assertTrue(alunos.stream().anyMatch(aluno -> aluno.getMatricula().equals("156563")));
    }

}
