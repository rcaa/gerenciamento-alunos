package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void buscarListaDeAlunosAtivosVazio() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        List<Aluno> alunos = this.alunoRepository.findByStatusAtivo();

        Assert.assertEquals(0, alunos.size());
    }

    @Test
    public void buscarListaDeAlunosInativosVazio() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");

        List<Aluno> alunos = this.alunoRepository.findByStatusInativo();

        Assert.assertEquals(0, alunos.size());
    }

    @Test
    public void buscarListaDeAlunosAtivosComVariosAlunos() {

        for(long i = 1L; i <= 4L; i++){
            Aluno aluno = new Aluno();
            aluno.setId(i);
            aluno.setNome("Vinicius");
            aluno.setTurno(Turno.NOTURNO);
            aluno.setCurso(Curso.ADMINISTRACAO);
            aluno.setStatus(Status.ATIVO);
            aluno.setMatricula("123456");
            alunoRepository.save(aluno);
        }

        Aluno aluno = new Aluno();
        aluno.setId(5L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> alunos = this.alunoRepository.findByStatusAtivo();

        Assert.assertEquals(4, alunos.size());
    }

    @Test
    public void buscarListaDeAlunosInativosComVariosAlunos() {

        for(long i = 1L; i <= 4L; i++){
            Aluno aluno = new Aluno();
            aluno.setId(i);
            aluno.setNome("Vinicius");
            aluno.setTurno(Turno.NOTURNO);
            aluno.setCurso(Curso.ADMINISTRACAO);
            aluno.setStatus(Status.ATIVO);
            aluno.setMatricula("123456");
            alunoRepository.save(aluno);
        }

        Aluno aluno = new Aluno();
        aluno.setId(5L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> alunos = this.alunoRepository.findByStatusInativo();

        Assert.assertEquals(1, alunos.size());
    }

    @Test
    public void buscarPorNomeCompleto() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> alunos = this.alunoRepository.findByNomeContainingIgnoreCase("Vinicius");

        Optional<Aluno> alunoEncontrado = alunos.stream().filter(alunoLista -> alunoLista.getId().equals(aluno.getId())).findFirst();

        Assert.assertEquals(alunoEncontrado.get().getId(), aluno.getId());
    }

    @Test
    public void buscarPorNomeIncompleto() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> alunos = this.alunoRepository.findByNomeContainingIgnoreCase("vini");

        Optional<Aluno> alunoEncontrado = alunos.stream().filter(alunoLista -> alunoLista.getId().equals(aluno.getId())).findFirst();

        Assert.assertEquals(alunoEncontrado.get().getId(), aluno.getId());
    }


}
