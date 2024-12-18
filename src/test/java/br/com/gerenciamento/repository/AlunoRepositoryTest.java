package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

public class AlunoRepositoryTest {
    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno aluno1;
    private Aluno aluno2;

    @BeforeEach
    public void setup() {
        // configura dois alunos no banco de dados em memória
        aluno1 = new Aluno();
        aluno1.setNome("Carlos Silva");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("MAT123");

        aluno2 = new Aluno();
        aluno2.setNome("Ana Beatriz Sousa");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.CONTABILIDADE);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("MAT456");

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);
    }

    @Test
    public void testFindByStatusAtivo() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();

        assertNotNull(alunosAtivos);
        assertEquals(1, alunosAtivos.size());
        assertEquals("Carlos Silva", alunosAtivos.get(0).getNome());
    }

    @Test
    public void testFindByStatusInativo() {
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();

        assertNotNull(alunosInativos);
        assertEquals(1, alunosInativos.size());
        assertEquals("Ana Beatriz Sousa", alunosInativos.get(0).getNome());
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        List<Aluno> alunosEncontrados = alunoRepository.findByNomeContainingIgnoreCase("car");

        assertNotNull(alunosEncontrados);
        assertEquals(1, alunosEncontrados.size());
        assertEquals("Carlos Silva", alunosEncontrados.get(0).getNome());
    }

    @Test
    public void testSaveAluno() {
        Aluno novoAluno = new Aluno();
        novoAluno.setNome("Beatriz Silva");
        novoAluno.setTurno(Turno.MATUTINO);
        novoAluno.setCurso(Curso.DIREITO);
        novoAluno.setStatus(Status.ATIVO);
        novoAluno.setMatricula("MAT789");

        Aluno alunoSalvo = alunoRepository.save(novoAluno);

        assertNotNull(alunoSalvo.getId());
        assertEquals("Beatriz Silva", alunoSalvo.getNome());
    }
}
