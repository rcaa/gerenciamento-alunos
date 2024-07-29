package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testSalvar() {
        Aluno aluno = new Aluno();
        aluno.setId(4L);
        aluno.setNome("Viniciusss");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("1234567");

        Aluno alunoNovo = alunoRepository.save(aluno);
        assertNotNull(alunoNovo.getId());
    }

    @Test
    public void testFindById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        Optional<Aluno> novoAlunoOptional = alunoRepository.findById(1L);
        assertTrue(novoAlunoOptional.isPresent());
        
        Aluno novoAluno = novoAlunoOptional.get();
        assertEquals("Vinicius", novoAluno.getNome());
    }

    @Test
    public void testFindAlunosAtivos() {

        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setId(1L);
        alunoAtivo.setNome("Vinicius Ativo");
        alunoAtivo.setTurno(Turno.NOTURNO);
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setMatricula("123456");
        alunoRepository.save(alunoAtivo);
    
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertFalse(alunosAtivos.isEmpty());
    
        Aluno alunoAtivoRetornado = alunosAtivos.get(0);
        assertEquals("Vinicius Ativo", alunoAtivoRetornado.getNome());
        assertEquals(Status.ATIVO, alunoAtivoRetornado.getStatus());
    }

    @Test
    public void testFindAlunosInativos() {

        Aluno alunoInativo = new Aluno();
        alunoInativo.setId(4L);
        alunoInativo.setNome("Vinicius Inativo");
        alunoInativo.setTurno(Turno.NOTURNO);
        alunoInativo.setCurso(Curso.ADMINISTRACAO);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("654321");
        alunoRepository.save(alunoInativo);
        
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertFalse(alunosInativos.isEmpty());

        Aluno alunoInativoRetornado = alunosInativos.get(0);
        assertEquals("Vinicius Inativo", alunoInativoRetornado.getNome());
        assertEquals(Status.INATIVO, alunoInativoRetornado.getStatus());
    }
}
