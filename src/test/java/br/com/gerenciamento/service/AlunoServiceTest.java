package br.com.gerenciamento.service;

import java.util.List;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

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

    //Não sei por que esse tá dando erro, nem toquei nisso...
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
    public void deleteById(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        int qtdAlunos = this.serviceAluno.findAll().size();
        this.serviceAluno.deleteById(1L);
        int qtdAlunos2 = this.serviceAluno.findAll().size();

        assertTrue(qtdAlunos - 1 == qtdAlunos2);
    }

    @Test
    public void findByStatusAtivo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> alunoRetorno = this.serviceAluno.findByStatusAtivo();
        Aluno alunoAtivo = alunoRetorno.get(0);
        assertTrue(alunoAtivo.getNome().equals("Vinicius"));
    }

    @Test
    public void findByStatusInativo(){
        Aluno aluno = new Aluno();
        aluno.setId(14L);
        aluno.setNome("Matheus");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);

        List<Aluno> alunoRetorno = this.serviceAluno.findByStatusInativo();
        Aluno alunoInativo = alunoRetorno.get(1);
        assertEquals("Matheus", alunoInativo.getNome());
    }

    @Test
    public void findByNomeContainingIgnoreCase(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        List<Aluno> alunoRetorno = this.serviceAluno.findByNomeContainingIgnoreCase("Vinicius");
        Aluno alunoPeloNome = alunoRetorno.get(0);
        assertTrue(aluno.getNome().equals(alunoPeloNome.getNome()) && aluno.getMatricula().equals(alunoPeloNome.getMatricula()));
    }
}