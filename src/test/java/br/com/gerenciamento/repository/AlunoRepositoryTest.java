package br.com.gerenciamento.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
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
    public void findByStatusAtivo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Marcia");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("789123");
    
        Aluno aluno3 = new Aluno();
        aluno3.setId(3L);
        aluno3.setNome("Paulo");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("555555");
    
        alunoRepository.saveAll(Arrays.asList(aluno, aluno2, aluno3));

        assertEquals(1, alunoRepository.findByStatusAtivo().size());
    }

    @Test
    public void findByStatusInativo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Marcia");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("789123");
    
        Aluno aluno3 = new Aluno();
        aluno3.setId(3L);
        aluno3.setNome("Paulo");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("555555");
    
        alunoRepository.saveAll(Arrays.asList(aluno, aluno2, aluno3));

        assertEquals(2, alunoRepository.findByStatusInativo().size());
    }

    @Test
    public void findByNomeContainingIgnoreCase(){
        Aluno aluno3 = new Aluno();
        aluno3.setId(3L);
        aluno3.setNome("Paulo");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("555555");
    
        alunoRepository.save(aluno3);

        assertEquals(1, alunoRepository.findByNomeContainingIgnoreCase("paulo").size());
    }

    @Test
    public void findByNomeContainingIgnoreCaseQuandoNaoEncontrado(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Marcia");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("789123");

        Aluno aluno3 = new Aluno();
        aluno3.setId(3L);
        aluno3.setNome("Paulo");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("555555");
    
        alunoRepository.saveAll(Arrays.asList(aluno, aluno2, aluno3));

        assertTrue(alunoRepository.findByNomeContainingIgnoreCase("joão").isEmpty());
    }
}