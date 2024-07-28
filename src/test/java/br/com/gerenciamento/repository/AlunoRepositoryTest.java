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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void findByStatusAtivo() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Pedro");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("111111");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Lucas");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("222222");
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Moises");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("333333");
        alunoRepository.save(aluno3);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        Assert.assertEquals(2, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getNome().equals("Lucas")));
        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getNome().equals("Moises")));
    }

    @Test
    public void findByStatusInativo() {
        alunoRepository.deleteAll();
    
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Pedro");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("111111");
        alunoRepository.save(aluno1);
    
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Lucas");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("222222");
        alunoRepository.save(aluno2);
    
        Aluno aluno3 = new Aluno();
        aluno3.setNome("Moises");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("333333");
        alunoRepository.save(aluno3);
    
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assert.assertEquals(2, alunosInativos.size());
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Pedro")));
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Moises")));
    }
    
    @Test
    public void salvarEEncontrarPorId() {
        alunoRepository.deleteAll();

        Aluno aluno = new Aluno();
        aluno.setNome("Gustavo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("444444");
        Aluno alunoSalvo = alunoRepository.save(aluno);

        Aluno alunoEncontrado = alunoRepository.findById(alunoSalvo.getId()).orElse(null);
        Assert.assertNotNull(alunoEncontrado);
        Assert.assertEquals("Gustavo", alunoEncontrado.getNome());
    }
    
    @Test
    public void testeSalvar() {
        Aluno aluno = new Aluno();
        aluno.setNome("Rafael");
        aluno.setMatricula("6666666");
        aluno.setCurso(Curso.ADMINISTRACAO); 
        aluno.setStatus(Status.ATIVO); 
        aluno.setTurno(Turno.NOTURNO); 

        Aluno savedAluno = alunoRepository.save(aluno);
        assertNotNull(savedAluno.getId());
    }

}
