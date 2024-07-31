package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testSaveAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("TestAluno");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        Optional<Aluno> alunoRetornado = alunoRepository.findById(aluno.getId());
        Assertions.assertTrue(alunoRetornado.isPresent());
        Assertions.assertEquals("TestAluno", alunoRetornado.get().getNome());
    }

    @Test
    public void testFindAlunoByNome() {
        Aluno aluno = new Aluno();
        aluno.setNome("FindAluno");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        alunoRepository.save(aluno);

        Aluno alunoEncontrado = alunoRepository.findByNomeContainingIgnoreCase("FindAluno").get(0);
        Assertions.assertNotNull(alunoEncontrado);
        Assertions.assertEquals("FindAluno", alunoEncontrado.getNome());
    }

    @Test
    public void testUpdateAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("UpdateAluno");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        aluno.setNome("UpdatedAluno");
        alunoRepository.save(aluno);

        Optional<Aluno> alunoAtualizado = alunoRepository.findById(aluno.getId());
        Assertions.assertTrue(alunoAtualizado.isPresent());
        Assertions.assertEquals("UpdatedAluno", alunoAtualizado.get().getNome());
    }

    @Test
    public void testDeleteAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("DeleteAluno");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        alunoRepository.save(aluno);

        alunoRepository.deleteById(aluno.getId());

        Optional<Aluno> alunoDeletado = alunoRepository.findById(aluno.getId());
        Assertions.assertFalse(alunoDeletado.isPresent());
    }
}
