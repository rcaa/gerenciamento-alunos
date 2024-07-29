package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ServiceAluno serviceAluno;
    @Test
    public void listAlunosAtivo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Carla");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123456789");
        this.serviceAluno.save(aluno2);
        List<Aluno> alunos=alunoRepository.findByStatusAtivo();
        Assert.assertTrue(alunos.size()==1);
    }
    @Test
    public void listAlunosInativo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Carla");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123456789");
        this.serviceAluno.save(aluno2);
        List<Aluno> alunos=alunoRepository.findByStatusInativo();
        Assert.assertTrue(alunos.size()==1);
    }
    @Test
    public void listAlunosIgnoreCase(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Carla Daniela");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("carla daniela");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123456789");
        this.serviceAluno.save(aluno2);
        List<Aluno> alunos=alunoRepository.findByNomeContainingIgnoreCase("Carla Daniela");
        Assert.assertTrue(alunos.size()==2);
    }

    @Test
    public void semRetorno(){
        List<Aluno> alunos=alunoRepository.findByNomeContainingIgnoreCase("inexistente");
        Assert.assertTrue(alunos.isEmpty());
    }
}
