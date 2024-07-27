package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno alunoAtivo;
    private Aluno alunoInativo;

    @BeforeEach
    void setUp() {
        alunoAtivo = new Aluno();
        alunoAtivo.setNome("Vinicius");
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setMatricula("123456");
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setTurno(Turno.NOTURNO);
        alunoRepository.save(alunoAtivo);

        alunoInativo = new Aluno();
        alunoInativo.setNome("Carlos");
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("654321");
        alunoInativo.setCurso(Curso.ADMINISTRACAO);
        alunoInativo.setTurno(Turno.MATUTINO);
        alunoRepository.save(alunoInativo);
    }

    @Test
    public void findByStatusAtivo() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertNotNull(alunosAtivos);
        assertEquals(1, alunosAtivos.size());
        assertEquals("Vinicius", alunosAtivos.get(0).getNome());
    }

    @Test
    public void findByStatusInativo() {
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertNotNull(alunosInativos);
        assertEquals(1, alunosInativos.size());
        assertEquals("Carlos", alunosInativos.get(0).getNome());
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("vinicius");
        assertNotNull(alunos);
        assertEquals(1, alunos.size());
        assertEquals("Vinicius", alunos.get(0).getNome());

        alunos = alunoRepository.findByNomeContainingIgnoreCase("carLoS");
        assertNotNull(alunos);
        assertEquals(1, alunos.size());
        assertEquals("Carlos", alunos.get(0).getNome());
    }

    @Test
    public void findByNomeContainingIgnoreCaseNotFound() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("peter");
        assertNotNull(alunos);
        assertEquals(0, alunos.size());
    }
}
