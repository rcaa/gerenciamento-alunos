package br.com.gerenciamento.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;
    
    @Test
    public void quantidadeAlunosAtivos(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(99L);
        aluno1.setNome("Elias");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setId(99L);
        aluno2.setNome("Aline");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("145621");

        this.alunoRepository.save(aluno1);
        this.alunoRepository.save(aluno2);


        assertEquals(2, alunoRepository.findByStatusAtivo().size());
    }


    @Test
    public void procurarNomeInexistente(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(99L);
        aluno1.setNome("Douglas");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("456321");
        this.alunoRepository.save(aluno1);

        assertEquals(0, this.alunoRepository.findByNomeContainingIgnoreCase("Yolanda").size());
    }

    @Test
    public void salvarSemMatricula(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(99L);
        aluno1.setNome("Douglas");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);

        assertThrows(ConstraintViolationException.class, () ->{
            alunoRepository.save(aluno1);
        });
    }

    @Test
    public void salvarSemCurso(){
        Aluno aluno1 = new Aluno();
        aluno1.setId(99L);
        aluno1.setNome("Diego");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("125423");

        assertThrows(ConstraintViolationException.class, () ->{
            alunoRepository.save(aluno1);
        });
    }




}
