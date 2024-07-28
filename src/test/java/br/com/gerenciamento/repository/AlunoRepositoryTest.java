package br.com.gerenciamento.repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testFindByStatusAtivo() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertFalse(alunosAtivos.isEmpty());
    }

    @Test
    public void testFindByStatusInativo() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertFalse(alunosInativos.isEmpty());
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Vinicius");
        assertFalse(alunos.isEmpty());
    }

    @Test
    public void testSave() {
        Aluno aluno = new Aluno();
        aluno.setNome("Novo Aluno");
        aluno.setMatricula("123");
        aluno.setCurso(Curso.ADMINISTRACAO); 
        aluno.setStatus(Status.ATIVO); 
        aluno.setTurno(Turno.MATUTINO); 

        Aluno savedAluno = alunoRepository.save(aluno);
        assertNotNull(savedAluno.getId());
    }
}
