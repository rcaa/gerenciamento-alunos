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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @BeforeEach
    public void setUp() {
        alunoRepository.deleteAll(); 
    }

    @Test
    public void testSaveAndFindById() {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana Costa");
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setMatricula("11223");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        aluno = alunoRepository.save(aluno);

        Aluno foundAluno = alunoRepository.findById(aluno.getId()).orElse(null);
        assertNotNull(foundAluno);
        assertEquals(aluno.getId(), foundAluno.getId());
        assertEquals(aluno.getNome(), foundAluno.getNome());
    }

    @Test
    public void testFindByStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Maria");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Carlos");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertThat(alunosAtivos).hasSize(1);
        assertThat(alunosAtivos.get(0).getNome()).isEqualTo("Maria");
    }

    @Test
    public void testFindByStatusInativo() {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos Pereira");
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setMatricula("54321");
        aluno.setStatus(Status.INATIVO);
        aluno.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno);

        List<Aluno> alunosInativos = alunoRepository.findAll();
        assertEquals(1, alunosInativos.size());
        assertTrue(alunosInativos.contains(aluno));
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("João da Silva");
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setMatricula("12345");
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno1);
        
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria Oliveira");
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setMatricula("67890");
        aluno2.setStatus(Status.ATIVO);
        aluno2.setTurno(Turno.NOTURNO);
        alunoRepository.save(aluno2);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("João");
        assertEquals(1, alunos.size());
        assertTrue(alunos.contains(aluno1));
    }
}
