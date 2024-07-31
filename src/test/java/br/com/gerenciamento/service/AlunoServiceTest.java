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
    public void findByStatusAtivoAndCount() {
        //Adiciona 1 aluno ativo
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        //Adiciona 1 aluno inativo
        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Rodrigo");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("213456");
        this.serviceAluno.save(aluno2);

        //busca pelos alunos ativos
        List<Aluno> alunoList = serviceAluno.findByStatusAtivo();

        //Verifica se a resposta do serviço retornou uma lista contendo o aluno ativo
        Assert.assertTrue(alunoList.stream().anyMatch(alunoAtivo -> alunoAtivo.getId().equals(aluno.getId())));

        //Verifica se a resposta retornou uma lista unitária
        Assert.assertEquals(1, alunoList.size());
    }

    @Test
    public void buscarPorAlunoPorNome() {
        //Salva um aluno com nome composto por exemplo
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius da Mata Rocha");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        //Salva um aluno com nome composto por exemplo
        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Carlos Vinicius Rodrigues");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123456");
        this.serviceAluno.save(aluno2);

        List<Aluno> alunoVini = serviceAluno.findByNomeContainingIgnoreCase("vini");

        Assert.assertEquals(2, alunoVini.size());
    }

    @Test
    public void buscarTodosAlunos() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius da Mata Rocha");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Carlos Vinicius Rodrigues");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("423658");
        this.serviceAluno.save(aluno2);

        List<Aluno> alunos = serviceAluno.findAll();

        Assert.assertEquals(2, alunos.size());
    }

    @Test
    public void salvarAlunoComNome3caracteres() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vin");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});
    }

}