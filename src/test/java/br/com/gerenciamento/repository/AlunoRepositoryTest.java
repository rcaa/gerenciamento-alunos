package br.com.gerenciamento.repository;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void findByStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Pedro");
        aluno1.setMatricula("12345");
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ENFERMAGEM);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Luisa");
        aluno2.setMatricula("67890");
        aluno2.setStatus(Status.ATIVO);
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        alunoRepository.save(aluno2);

        List<Aluno> ativos = alunoRepository.findByStatusAtivo();
        assertNotNull(ativos);
        assertEquals(2, ativos.size());
        assertTrue(ativos.stream().anyMatch(a -> a.getNome().equals("Pedro")));
        assertTrue(ativos.stream().anyMatch(a -> a.getNome().equals("Luisa")));
    }

    @Test
    public void findByStatusInativo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Pedro");
        aluno1.setMatricula("12345");
        aluno1.setStatus(Status.INATIVO);
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ENFERMAGEM);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Luisa");
        aluno2.setMatricula("67890");
        aluno2.setStatus(Status.INATIVO);
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        alunoRepository.save(aluno2);

        List<Aluno> inativos = alunoRepository.findByStatusInativo();
        assertNotNull(inativos);
        assertEquals(2, inativos.size());
        assertTrue(inativos.stream().anyMatch(a -> a.getNome().equals("Pedro")));
        assertTrue(inativos.stream().anyMatch(a -> a.getNome().equals("Luisa")));
    }

    @Test
    public void buscarPorMatricula() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);

        Optional<Aluno> alunoRetorno = this.alunoRepository.getByMatricula("123456");
        Assert.assertTrue(alunoRetorno.isPresent());
        Assert.assertEquals("Vinicius", alunoRetorno.get().getNome());
    }

    @Test
    public void findByCurso() {

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Pedro");
        aluno1.setMatricula("12345");
        aluno1.setStatus(Status.INATIVO);
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ENFERMAGEM);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Luisa");
        aluno2.setMatricula("67890");
        aluno2.setStatus(Status.INATIVO);
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        alunoRepository.save(aluno2);

        List<Aluno> cursos = alunoRepository.findByCurso(Curso.DIREITO);
        Assert.assertNotNull(cursos);
        Assert.assertEquals(1,cursos.size());
    }
}
