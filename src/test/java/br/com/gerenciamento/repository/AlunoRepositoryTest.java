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
    private AlunoRepository alunoRepository;
     
    @Test
     public void findByCursoAdministracao() {
        alunoRepository.deleteAll();
    
        Aluno aluno1 = new Aluno();
        aluno1.setNome("João Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("874635");
        alunoRepository.save(aluno1);
    
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria Souza");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("987123");
        alunoRepository.save(aluno2);
    
        Aluno aluno3 = new Aluno();
        aluno3.setNome("Pedro Santos");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("234789");
        alunoRepository.save(aluno3);
    
        List<Aluno> alunosAdministracao = alunoRepository.findByCurso(Curso.ADMINISTRACAO);
        Assert.assertEquals(3, alunosAdministracao.size());
        Assert.assertTrue(alunosAdministracao.stream().anyMatch(aluno -> aluno.getNome().equals("João Silva")));
        Assert.assertTrue(alunosAdministracao.stream().anyMatch(aluno -> aluno.getNome().equals("Maria Souza")));
        Assert.assertTrue(alunosAdministracao.stream().anyMatch(aluno -> aluno.getNome().equals("Pedro Santos")));
    }
    
    @Test
    public void findByTurnoNoturno() {
        alunoRepository.deleteAll();
    
        Aluno aluno1 = new Aluno();
        aluno1.setNome("João Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("874635");
        alunoRepository.save(aluno1);
    
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria Souza");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("987123");
        alunoRepository.save(aluno2);
    
        Aluno aluno3 = new Aluno();
        aluno3.setNome("Pedro Santos");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("234789");
        alunoRepository.save(aluno3);
    
        List<Aluno> alunosNoturno = alunoRepository.findByTurno(Turno.NOTURNO);
        Assert.assertEquals(3, alunosNoturno.size());
        Assert.assertTrue(alunosNoturno.stream().anyMatch(aluno -> aluno.getNome().equals("João Silva")));
        Assert.assertTrue(alunosNoturno.stream().anyMatch(aluno -> aluno.getNome().equals("Maria Souza")));
        Assert.assertTrue(alunosNoturno.stream().anyMatch(aluno -> aluno.getNome().equals("Pedro Santos")));
    }
    @Test
    public void findByMatricula() {
        alunoRepository.deleteAll();
    
        Aluno aluno1 = new Aluno();
        aluno1.setNome("João Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("874635");
        alunoRepository.save(aluno1);
    
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria Souza");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("987123");
        alunoRepository.save(aluno2);
    
        Aluno aluno3 = new Aluno();
        aluno3.setNome("Pedro Santos");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("234789");
        alunoRepository.save(aluno3);
    
        Aluno alunoEncontrado = (Aluno) alunoRepository.findByMatricula("874635");
        Assert.assertNotNull(alunoEncontrado);
        Assert.assertEquals("João Silva", alunoEncontrado.getNome());
    }
    @Test
    public void findByStatusInativo() {
        alunoRepository.deleteAll();
    
        Aluno aluno1 = new Aluno();
        aluno1.setNome("João Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("874635");
        alunoRepository.save(aluno1);
    
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria Souza");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("987123");
        alunoRepository.save(aluno2);
    
        Aluno aluno3 = new Aluno();
        aluno3.setNome("Pedro Santos");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("234789");
        alunoRepository.save(aluno3);
    
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assert.assertEquals(1, alunosInativos.size());
        Assert.assertTrue(alunosInativos.stream().anyMatch(aluno -> aluno.getNome().equals("Maria Souza")));
    }
    
}
