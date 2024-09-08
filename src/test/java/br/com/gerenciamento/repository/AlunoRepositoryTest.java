package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.repository.AlunoRepository;

import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {


    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @Transactional
    public void SalvarAluno(){
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Chico");
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoAtivo.setMatricula("123456");
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        this.alunoRepository.save(alunoAtivo);

        Aluno alunoAtivo1 = this.alunoRepository.findById(alunoAtivo.getId()).orElse(null);

        Assert.assertTrue(alunoAtivo1.getNome().equals("Chico"));
    }


    @Test
    @Transactional
    public void FindByStatusInativo(){
        Aluno inativoAluno = new Aluno();
        inativoAluno.setNome("Chico");
        inativoAluno.setStatus(Status.INATIVO);
        inativoAluno.setTurno(Turno.MATUTINO);
        inativoAluno.setMatricula("123456");
        inativoAluno.setCurso(Curso.ADMINISTRACAO);

        this.alunoRepository.save(inativoAluno);
        List<Aluno> inativoAlunos = alunoRepository.findByStatusInativo();
        assertNotNull(inativoAlunos);
        assertFalse(inativoAlunos.isEmpty());
        for (Aluno aluno : inativoAlunos) {
            assertEquals(inativoAluno.getStatus(), aluno.getStatus());
        }
    }

    @Test
    @Transactional
    public void FindByStatusAtivo(){
        Aluno ativoAluno = new Aluno();
        ativoAluno.setNome("Chico");
        ativoAluno.setStatus(Status.ATIVO);
        ativoAluno.setTurno(Turno.MATUTINO);
        ativoAluno.setMatricula("123456");
        ativoAluno.setCurso(Curso.ADMINISTRACAO);

        this.alunoRepository.save(ativoAluno);
        List<Aluno> ativoAlunos = alunoRepository.findByStatusAtivo();
        assertNotNull(ativoAlunos);
        assertFalse(ativoAlunos.isEmpty());
        for (Aluno aluno : ativoAlunos) {
            assertEquals(ativoAluno.getStatus(), aluno.getStatus());
        }
    }

    @Test
    @Transactional
    public void testAlunoSalvo(){
        Aluno aluno = new Aluno();
        aluno.setNome("Chico");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setMatricula("123456");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setId(1L);

        alunoRepository.save(aluno);
        assertNotNull(aluno.getId());

        Aluno alunoSalvo = alunoRepository.findById(aluno.getId()).orElse(null);
        assertNotNull(alunoSalvo);
        assertEquals("Chico", alunoSalvo.getNome());
        assertEquals(Status.ATIVO, alunoSalvo.getStatus());
    }



}
