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
    public void encontrarPorStatusAtivo() {
        Aluno teste1 = new Aluno();
        teste1.setNome("Maria");
        teste1.setTurno(Turno.NOTURNO);
        teste1.setCurso(Curso.ADMINISTRACAO);
        teste1.setStatus(Status.ATIVO);
        teste1.setMatricula("123456");

        Aluno teste2 = new Aluno();
        teste2.setNome("Tiago");
        teste2.setTurno(Turno.NOTURNO);
        teste2.setCurso(Curso.INFORMATICA);
        teste2.setStatus(Status.INATIVO);
        teste2.setMatricula("290290");

        alunoRepository.save(teste1);
        alunoRepository.save(teste2);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertThat(alunosAtivos).hasSize(1);
        assertThat(alunosAtivos.get(0).getNome()).isEqualTo("Maria");
    }

    @Test
    public void encontrarPorStatusInativo() {
        Aluno aluno = new Aluno();
        aluno.setNome("Lucas Emanuel");
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setMatricula("143535");
        aluno.setStatus(Status.INATIVO);
        aluno.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno);

        List<Aluno> alunosInativos = alunoRepository.findAll();
        assertEquals(1, alunosInativos.size());
        assertTrue(alunosInativos.contains(aluno));
    }

    @Test
    public void encontrarPorNomeContainingIgnoreCase() {
        Aluno teste1 = new Aluno();
        teste1.setNome("Teste 01");
        teste1.setCurso(Curso.ADMINISTRACAO);
        teste1.setMatricula("191054");
        teste1.setStatus(Status.ATIVO);
        teste1.setTurno(Turno.MATUTINO);
        alunoRepository.save(teste1);
        
        Aluno teste2 = new Aluno();
        teste2.setNome("Maria Isabel");
        teste2.setCurso(Curso.INFORMATICA);
        teste2.setMatricula("643652");
        teste2.setStatus(Status.ATIVO);
        teste2.setTurno(Turno.NOTURNO);
        alunoRepository.save(teste2);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Teste");
        assertEquals(1, alunos.size());
        assertTrue(alunos.contains(teste1));
    }


    @Test
    public void salvarEncontrarPorID() {
        Aluno aluno = new Aluno();
        aluno.setNome("Sandra Oliver");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setMatricula("029338");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        aluno = alunoRepository.save(aluno);

        Aluno foundAluno = alunoRepository.findById(aluno.getId()).orElse(null);
        assertNotNull(foundAluno);
        assertEquals(aluno.getId(), foundAluno.getId());
        assertEquals(aluno.getNome(), foundAluno.getNome());
    }

}
