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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno alunoAtivo;
    private Aluno alunoInativo;
    private Aluno alunoMatutino;
    private Aluno alunoNoturno;

    @BeforeEach
    public void setUp() {
        // Criando e salvando os alunos
        alunoAtivo = new Aluno();
        alunoAtivo.setNome("Carlos Ativo");
        alunoAtivo.setMatricula("A001");
        alunoAtivo.setCurso(Curso.INFORMATICA);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoRepository.save(alunoAtivo);

        alunoInativo = new Aluno();
        alunoInativo.setNome("Ana Inativa");
        alunoInativo.setMatricula("A002");
        alunoInativo.setCurso(Curso.ENFERMAGEM);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setTurno(Turno.NOTURNO);
        alunoRepository.save(alunoInativo);

        alunoMatutino = new Aluno();
        alunoMatutino.setNome("Ricardo Matutino");
        alunoMatutino.setMatricula("A003");
        alunoMatutino.setCurso(Curso.ADMINISTRACAO);
        alunoMatutino.setStatus(Status.ATIVO);
        alunoMatutino.setTurno(Turno.MATUTINO);
        alunoRepository.save(alunoMatutino);

        alunoNoturno = new Aluno();
        alunoNoturno.setNome("Mariana Noturno");
        alunoNoturno.setMatricula("A004");
        alunoNoturno.setCurso(Curso.BIOMEDICINA);
        alunoNoturno.setStatus(Status.INATIVO);
        alunoNoturno.setTurno(Turno.NOTURNO);
        alunoRepository.save(alunoNoturno);
    }

    @Test
    public void testFindByStatusAtivo() {
        // Testa se a consulta por status ATIVO retorna os alunos ativos corretamente
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertEquals(2, alunosAtivos.size());
        alunosAtivos.forEach(aluno -> assertEquals(Status.ATIVO, aluno.getStatus()));
    }

    @Test
    public void testFindByStatusInativo() {
        // Testa se a consulta por status INATIVO retorna os alunos inativos corretamente
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertEquals(2, alunosInativos.size());
        alunosInativos.forEach(aluno -> assertEquals(Status.INATIVO, aluno.getStatus()));
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        // Testa se a busca por nome, ignorando maiúsculas/minúsculas, retorna o aluno correto
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Carlos");
        assertEquals(1, alunos.size());
        assertEquals("Carlos Ativo", alunos.get(0).getNome());
    }

    @Test
    public void testFindByTurnoMatutino() {
        // Testa se a consulta por turno MATUTINO retorna os alunos corretos
        List<Aluno> alunosMatutino = alunoRepository.findByTurno(Turno.MATUTINO);
        assertEquals(2, alunosMatutino.size());
        alunosMatutino.forEach(aluno -> assertEquals(Turno.MATUTINO, aluno.getTurno()));
    }
}
