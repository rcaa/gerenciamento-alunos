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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    public void criarRepositoryGenerico() {
        alunoRepository.deleteAll();
        Aluno aluno1 = new Aluno();

        aluno1.setNome("Jubscleison");
        aluno1.setMatricula("001");
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.NOTURNO);

        Aluno aluno2 = new Aluno();

        aluno2.setNome("Aristoteles");
        aluno2.setMatricula("002");
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setTurno(Turno.NOTURNO);

        Aluno aluno3 = new Aluno();

        aluno3.setNome("Geronimo");
        aluno3.setMatricula("003");
        aluno3.setCurso(Curso.DIREITO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setTurno(Turno.MATUTINO);

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);
        alunoRepository.save(aluno3);
    }

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void findByStatusAtivo() {
        criarRepositoryGenerico();

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();

        assertThat(alunosAtivos).hasSize(2);
        assertThat(alunosAtivos.get(0).getNome()).isEqualTo("Jubscleison");
        assertThat(alunosAtivos.get(1).getNome()).isEqualTo("Aristoteles");
    }

    @Test
    public void findByStatusInativo() {
        criarRepositoryGenerico();

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();

        assertThat(alunosInativos).hasSize(1);
        assertThat(alunosInativos.get(0).getNome()).isEqualTo("Geronimo");
    }

    @Test
    public void findByNomeIgnoreCase() {
        criarRepositoryGenerico();

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("GERONIMO");

        assertThat(alunos).hasSize(1);
    }

    @Test
    public void SalvarBemSucedido() {
        criarRepositoryGenerico();

        Aluno novoAluno = new Aluno();

        novoAluno.setNome("Aristeu");
        novoAluno.setMatricula("004");
        novoAluno.setCurso(Curso.BIOMEDICINA);
        novoAluno.setStatus(Status.ATIVO);
        novoAluno.setTurno(Turno.MATUTINO);

        Aluno alunoSalvo = alunoRepository.save(novoAluno);

        assertThat(alunoSalvo.getNome()).isEqualTo("Aristeu");

        List<Aluno> todosAlunos = alunoRepository.findAll();
        assertThat(todosAlunos).hasSize(4);
    }
}