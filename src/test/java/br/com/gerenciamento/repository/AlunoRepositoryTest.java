package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// tudo certo
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @BeforeEach
    void setup() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setNome("João Silva");
        aluno1.setMatricula("MAT001");
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.MATUTINO);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria Souza");
        aluno2.setMatricula("MAT002");
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setTurno(Turno.NOTURNO);

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);
    }

    @Test
    void testFindByStatusAtivo() {
        List<Aluno> ativos = alunoRepository.findByStatusAtivo();
        assertThat(ativos).hasSize(1);
        assertThat(ativos.get(0).getNome()).isEqualTo("João Silva");
    }

    @Test
    void testFindByStatusInativo() {
        List<Aluno> inativos = alunoRepository.findByStatusInativo();
        assertThat(inativos).hasSize(1);
        assertThat(inativos.get(0).getNome()).isEqualTo("Maria Souza");
    }

    @Test
    void testFindByNomeContainingIgnoreCase() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("joão");
        assertThat(alunos).hasSize(1);
        assertThat(alunos.get(0).getNome()).isEqualTo("João Silva");
    }

    @Test
    void testSaveAndFindAll() {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos Almeida");
        aluno.setMatricula("MAT003");
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);

        alunoRepository.save(aluno);

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(3);
    }
}
