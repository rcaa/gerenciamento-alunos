package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private void criarAlunos() {
        alunoRepository.deleteAll();

        Aluno aluno00 = new Aluno();
        aluno00.setNome("Aluno 00");
        aluno00.setTurno(Turno.NOTURNO);
        aluno00.setCurso(Curso.ADMINISTRACAO);
        aluno00.setStatus(Status.ATIVO);
        aluno00.setMatricula("000");

        alunoRepository.save(aluno00);

        Aluno aluno01 = new Aluno();
        aluno01.setNome("Aluno 01");
        aluno01.setTurno(Turno.MATUTINO);
        aluno01.setCurso(Curso.CONTABILIDADE);
        aluno01.setStatus(Status.INATIVO);
        aluno01.setMatricula("001");

        alunoRepository.save(aluno01);
    }

    /**
     * Teste para verificar se findByStatusAtivo retorna todos os alunos ativos.
     */
    @Test
    public void verificarBuscaPorStatusAtivo() {
        criarAlunos();
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertThat(alunosAtivos).hasSize(1);
        assertThat(alunosAtivos.get(0).getNome()).isEqualTo("Aluno 00");
    }

    /**
     * Teste para verificar se findByStatusInativo retorna todos os alunos inativos.
     */
    @Test
    public void verificarBuscaPorStatusInativo() {
        criarAlunos();
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertThat(alunosInativos).hasSize(1);
        assertThat(alunosInativos.get(0).getNome()).isEqualTo("Aluno 01");
    }

    /**
     * Teste para verificar se findByNomeContainingIgnoreCase retorna todos os alunos que contém o nome informado.
     */
    @Test
    public void verificarBuscaPorNome() {
        criarAlunos();
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("00");
        assertThat(alunos).hasSize(1);
        assertThat(alunos.get(0).getNome()).isEqualTo("Aluno 00");

        alunos = alunoRepository.findByNomeContainingIgnoreCase("1");
        assertThat(alunos).hasSize(1);
        assertThat(alunos.get(0).getNome()).isEqualTo("Aluno 01");
    }

    /**
     * Teste para verificar se findByNomeContainingIgnoreCase um nome inexistente retorna vazio.
     */
    @Test
    public void verificarBuscaPorNomeInexistente() {
        criarAlunos();
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Aluno 02");
        assertThat(alunos).isEmpty();
    }
}