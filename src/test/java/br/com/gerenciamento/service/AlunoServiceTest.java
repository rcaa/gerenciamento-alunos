package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.exception.AlunoNotSavedException;

import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;


import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    //Novo
    @Test
    public void salvarAlunoSemId(){
        Aluno aluno = new Aluno();
        aluno.setNome("Gustavo");
        aluno.setMatricula("24120101");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);
        if(aluno.getId() == null){
            throw new AlunoNotSavedException("O aluno foi salvo sem ID");
        }
    }
    //Novo
    @Test
    public void deletarAluno(){
        Aluno aluno = new Aluno();
        aluno.setNome("Gustavo");
        aluno.setMatricula("24120101");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);

        if(aluno.getId() == null){
            throw new AlunoNotSavedException("O aluno foi salvo sem ID");
        } else {
            serviceAluno.deleteById(1L);
        }
    }
    //Novo
    @Test
    public void atualizarAluno(){
        Aluno aluno = new Aluno();
        aluno.setNome("Gustavo");
        aluno.setMatricula("24120101");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);

        assertNotNull(aluno.getId());

        Aluno alunoAtualizado = null;

        alunoAtualizado = serviceAluno.getById(aluno.getId());
        alunoAtualizado.setNome("Irineu você não sabe e nem eu");
        serviceAluno.save(alunoAtualizado);


        Aluno alunoVerificado = serviceAluno.getById(aluno.getId());
        assertEquals("Irineu você não sabe e nem eu", alunoVerificado.getNome());
    }

    @Test
    public void testFindInativos() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Aluno ativo");
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoAtivo.setMatricula("123456");
        alunoAtivo.setCurso(Curso.ENFERMAGEM);
        serviceAluno.save(alunoAtivo);

        Aluno alunoInativo = new Aluno();
        alunoInativo.setNome("Aluno Inativo 1");
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setTurno(Turno.MATUTINO);
        alunoInativo.setMatricula("654321");
        alunoInativo.setCurso(Curso.BIOMEDICINA);
        serviceAluno.save(alunoInativo);

        Aluno alunoInativo2 = new Aluno();
        alunoInativo2.setNome("Aluno Inativo 2");
        alunoInativo2.setStatus(Status.INATIVO);
        alunoInativo2.setTurno(Turno.MATUTINO);
        alunoInativo2.setMatricula("24122001");
        alunoInativo2.setCurso(Curso.BIOMEDICINA);
        serviceAluno.save(alunoInativo2);

        List<Aluno> inativos = serviceAluno.findByStatusInativo();

        assertFalse(inativos.isEmpty());
        assertTrue(inativos.stream().allMatch(a -> Status.INATIVO.equals(a.getStatus())));
    }

}