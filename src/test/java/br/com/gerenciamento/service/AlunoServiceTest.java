package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
    public void salvarAlunoSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Renata");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    @Test
    public void salvarAlunoComNome() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("7891011");

        this.serviceAluno.save(aluno);
        Aluno alunoSalvo = this.serviceAluno.getById(2L);
        Assert.assertNotNull(alunoSalvo);
        Assert.assertEquals("Carlos", alunoSalvo.getNome());
    }

    @Test
    public void buscarAlunosPorStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Lucia");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ENFERMAGEM);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("112233");
        this.serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Marcos");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("445566");
        this.serviceAluno.save(aluno2);

        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();
        Assert.assertTrue(alunosAtivos.size() > 0);
        Assert.assertTrue(alunosAtivos.stream().allMatch(a -> a.getStatus().equals(Status.ATIVO)));
    }

    @Test
    public void buscarAlunosPorStatusInativo() {
        Aluno aluno = new Aluno();
        aluno.setNome("Julia");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("998877");
        this.serviceAluno.save(aluno);

        List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();
        Assert.assertTrue(alunosInativos.size() > 0);
        Assert.assertTrue(alunosInativos.stream().allMatch(a -> a.getStatus().equals(Status.INATIVO)));
    }

    @Test
    public void buscarAlunosPorNome() {
        Aluno aluno = new Aluno();
        aluno.setNome("Gabriela");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123123");
        this.serviceAluno.save(aluno);

        List<Aluno> alunos = this.serviceAluno.findByNomeContainingIgnoreCase("gabr");
        Assert.assertTrue(alunos.size() > 0);
        Assert.assertTrue(alunos.get(0).getNome().contains("Gabriela"));
    }

    @Test
    public void deletarAlunoNaoExistente() {
        Long alunoIdNaoExistente = 999L;
        try {
            this.serviceAluno.deleteById(alunoIdNaoExistente);
        } catch (Exception e) {
            Assert.fail("Deveria não lançar exceção ao tentar excluir aluno inexistente");
        }
    }
}
