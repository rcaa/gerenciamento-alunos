package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    AlunoRepository alunoRepository;

    @Test
    public void findByStatusAtivo() {
        // Verifica a busca por alunos com status ativo
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Mayara");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("654321");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Karol");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123456");
        alunoRepository.save(aluno2);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertEquals(2, alunosAtivos.size()); // Verifica se a lista tem o tamanho correto
        // Verifica se os alunos cadastrados correspondem aos da lista
        assertEquals(aluno1.getId(), alunosAtivos.get(0).getId());
        assertEquals(aluno2.getId(), alunosAtivos.get(1).getId());
    }

    @Test
    public void findByStatusInativo() {
        // Verifica a busca por alunos com status inativo
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Mayara");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("654321");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Karol");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("123456");
        alunoRepository.save(aluno2);

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertEquals(2, alunosInativos.size()); // Verifica se a lista está com o tamanho correto
        // Verifica se os alunos cadastrados correspondem aos da lista
        assertEquals(aluno1.getId(), alunosInativos.get(0).getId());
        assertEquals(aluno2.getId(), alunosInativos.get(1).getId());
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        // Verifica a busca por nome com ignore case
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Mayara");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("654321");
        alunoRepository.save(aluno);

        List<Aluno> alunoEncontrado = alunoRepository.findByNomeContainingIgnoreCase("mayara");
        assertEquals(1, alunoEncontrado.size()); // Verifica se a lista está do tamanho correto
        // Verifica se o aluno cadastrado corresponde ao da lista
        assertEquals(aluno.getNome(), alunoEncontrado.get(0).getNome());
    }

    @Test
    public void deleteById() {
        // Verifica a exclusão por id
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Mayara");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("654321");
        alunoRepository.save(aluno);
        alunoRepository.deleteById(aluno.getId());
        Optional<Aluno> alunoDeletado = alunoRepository.findById(aluno.getId());
        assertTrue(alunoDeletado.isEmpty());
    }
}
