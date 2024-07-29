package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    @Transactional
    public void testGetById() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius Silva");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(aluno.getId());
        Assertions.assertEquals("Vinicius Silva", alunoRetorno.getNome());
    }

    @Test
    @Transactional
    public void testSalvarSemNome() {
        Aluno alunoSemNome = new Aluno();
        alunoSemNome.setTurno(Turno.NOTURNO);
        alunoSemNome.setCurso(Curso.ADMINISTRACAO);
        alunoSemNome.setStatus(Status.ATIVO);
        alunoSemNome.setMatricula("123456");
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(alunoSemNome);
        });
    }

    @Test
    @Transactional
    public void testAtualizarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius Silva");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        aluno.setNome("Carlos Souza");
        this.serviceAluno.save(aluno);

        Aluno alunoAtualizado = this.serviceAluno.getById(aluno.getId());
        Assertions.assertEquals("Carlos Souza", alunoAtualizado.getNome());
    }

    @Test
    @Transactional
    public void testDeletarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius Silva");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(aluno.getId());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(aluno.getId());
        });
    }

    @Test
    @Transactional
    public void testAtualizarStatus() {
        Aluno aluno = new Aluno();
        aluno.setNome("Julia Santos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("789");
        this.serviceAluno.save(aluno);

        aluno.setStatus(Status.INATIVO);
        this.serviceAluno.save(aluno);

        Aluno alunoAtualizado = this.serviceAluno.getById(aluno.getId());
        Assertions.assertEquals(Status.INATIVO, alunoAtualizado.getStatus());
    }

    @Test
    @Transactional
    public void testFindAll() {
        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setNome("Joana Silva");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123");

        aluno2.setNome("Juliana Souza");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("234");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunos = this.serviceAluno.findAll();
        Assertions.assertEquals(2, alunos.size());

        if (alunos.get(0).getId().equals(aluno1.getId())) {
            Assertions.assertEquals(alunos.get(1).getId(), aluno2.getId());
        } else if (alunos.get(0).getId().equals(aluno2.getId())) {
            Assertions.assertEquals(alunos.get(1).getId(), aluno1.getId());
        } else {
            Assertions.fail();
        }
    }

    @Test
    @Transactional
    public void testDeleteById() {
        Aluno aluno = new Aluno();
        aluno.setNome("Dora Adventurous");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("345");
        this.serviceAluno.save(aluno);

        Assertions.assertNotNull(aluno.getId(), "Aluno salvo sem id");
        this.serviceAluno.deleteById(aluno.getId());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.serviceAluno.getById(aluno.getId());
        });
    }

    @Test
    @Transactional
    public void testFindByStatusInativo() {
        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setNome("Anelise Genevieve");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("321");

        aluno2.setNome("Erica Lauren");
        aluno2.setTurno(Turno.VESPERTINO);
        aluno2.setCurso(Curso.MODA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("456");

        this.serviceAluno.save(aluno1);
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();
        Assertions.assertFalse(alunosInativos.isEmpty(), "Lista de alunos inativos está vazia");

        Assertions.assertTrue(alunosInativos.stream().allMatch(a -> Status.INATIVO.equals(a.getStatus())));
    }
}
