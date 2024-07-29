package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

     @Test
    public void salvarSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    @Test
    public void findByStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(3L);
        aluno1.setNome("Luiza");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("925436");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(4L);
        aluno2.setNome("Mauricio");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("335411");
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();
        Assert.assertTrue(alunosAtivos.stream().anyMatch(aluno -> aluno.getNome().equals("Luiza")));
    }

    @Test
    public void findAll() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(5L);
        aluno1.setNome("Rafael");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("789012");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(6L);
        aluno2.setNome("Juliana");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("654321");
        this.serviceAluno.save(aluno2);

        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertEquals(2, alunos.size());
        Assert.assertTrue(alunos.stream().anyMatch(aluno -> aluno.getNome().equals("Rafael")));
        Assert.assertTrue(alunos.stream().anyMatch(aluno -> aluno.getNome().equals("Juliana")));
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(7L);
        aluno1.setNome("Maria");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.BIOMEDICINA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("987654");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(8L);
        aluno2.setNome("Mariana");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123789");
        this.serviceAluno.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setId(9L);
        aluno3.setNome("Carlos");
        aluno3.setTurno(Turno.MATUTINO);
        aluno3.setCurso(Curso.CONTABILIDADE);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("328564");
        this.serviceAluno.save(aluno3);

        List<Aluno> alunos = this.serviceAluno.findByNomeContainingIgnoreCase("mari");
        Assert.assertEquals(2, alunos.size());
        Assert.assertTrue(alunos.stream().anyMatch(aluno -> aluno.getNome().equals("Maria")));
        Assert.assertTrue(alunos.stream().anyMatch(aluno -> aluno.getNome().equals("Mariana")));
    }
}
