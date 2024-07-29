package br.com.gerenciamento.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @Transactional
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);

        List<Aluno> alunoRetorno = this.alunoRepository.findByNomeContainingIgnoreCase("vinicius");
        Assert.assertNotNull(alunoRetorno);
        Assert.assertTrue(alunoRetorno.get(0).getId().equals(aluno.getId()));
    }

    @Test
    @Transactional
    public void findByStatusInativo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Vinicius");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("123456");
        this.alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Alice");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123");
        this.alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Daniel");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.CONTABILIDADE);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("456");
        this.alunoRepository.save(aluno3);

        List<Aluno> alunosInativos = this.alunoRepository.findByStatusInativo();
        Assert.assertEquals(aluno1.getId(), alunosInativos.get(0).getId());
        Assert.assertEquals(1, alunosInativos.size());
    }

    @Test
    @Transactional
    public void findByStatusAtivo() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Vinicius");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("123456");
        this.alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Alice");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123");
        this.alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Daniel");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.CONTABILIDADE);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("456");
        this.alunoRepository.save(aluno3);

        List<Aluno> alunosAtivos = this.alunoRepository.findByStatusAtivo();
        List<Long> alunosEsperados = List.of(aluno2.getId(), aluno3.getId());
        List<Long> alunosRetorno = List.of(alunosAtivos.get(0).getId(), alunosAtivos.get(1).getId());
        Assert.assertEquals(2, alunosAtivos.size());
        Assert.assertTrue(alunosEsperados.containsAll(alunosRetorno) && alunosRetorno.containsAll(alunosEsperados));
    }

    @Test
    @Transactional
    public void salvarSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.alunoRepository.save(aluno);});
    }

}
