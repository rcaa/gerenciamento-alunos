package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.repository.AlunoRepository;

import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @Transactional
    public void Salvar() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Laysa");
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoAtivo.setMatricula("123456");
        this.alunoRepository.save(alunoAtivo);

        Aluno alunoAtivo1 = this.alunoRepository.findById(alunoAtivo.getId()).orElse(null);
        Assert.assertTrue(alunoAtivo1.getNome().equals("Laysa"));

    }

    @Transactional
    @Test
    public void FindByStatusAtivo() {

        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Laysa");
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoAtivo.setMatricula("123456");
        this.alunoRepository.save(alunoAtivo);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertNotNull(alunosAtivos);
        assertFalse(alunosAtivos.isEmpty());
        for (Aluno aluno : alunosAtivos) {
            assertEquals(alunoAtivo.getStatus(), aluno.getStatus());
        }
    }

    @Transactional
    @Test
    public void FindByStatusInativo() {

        Aluno alunoInativo = new Aluno();
        alunoInativo.setNome("Laysa");
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setCurso(Curso.ADMINISTRACAO);
        alunoInativo.setTurno(Turno.MATUTINO);
        alunoInativo.setMatricula("123456");
        this.alunoRepository.save(alunoInativo);

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertNotNull(alunosInativos);
        assertFalse(alunosInativos.isEmpty());
        for (Aluno aluno : alunosInativos) {
            assertEquals(alunoInativo.getStatus(), aluno.getStatus());
        }
    }

    @Transactional
    @Test
    public void testFindByNomeContainingIgnoreCase() {

        Aluno alunoTeste = new Aluno();
        alunoTeste.setNome("Silva");
        alunoTeste.setStatus(Status.ATIVO);
        alunoTeste.setCurso(Curso.ADMINISTRACAO);
        alunoTeste.setTurno(Turno.MATUTINO);
        alunoTeste.setMatricula("123456");
        this.alunoRepository.save(alunoTeste);

        String nome = "Silva";
        List<Aluno> alunosComNome = alunoRepository.findByNomeContainingIgnoreCase(nome);
        assertNotNull(alunosComNome);
        assertFalse(alunosComNome.isEmpty());
        for (Aluno aluno : alunosComNome) {
            assertTrue(aluno.getNome().toLowerCase().contains(nome.toLowerCase()));
        }
    }

}
