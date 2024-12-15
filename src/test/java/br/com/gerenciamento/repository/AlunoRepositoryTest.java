package br.com.gerenciamento.repository;

import static org.junit.Assert.assertEquals;

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
    public void buscarAlunoPorNome(){
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.BIOMEDICINA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        alunoRepository.save(a1);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Arthur");
        
        assertEquals(1, alunos.size());
        assertEquals("123", alunos.get(0).getMatricula());
    }
    
    @Test
    public void buscarAlunoInexistentePorNome(){
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Yuri Alves");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.INFORMATICA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        alunoRepository.save(a1);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Guilherme");

        assertEquals(0, alunos.size());
    }
    
    @Test
    public void buscarAlunoAtivo(){
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.INFORMATICA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        alunoRepository.save(a1);

        List<Aluno> alunos = alunoRepository.findByStatusAtivo();

        assertEquals(1, alunos.size());
        assertEquals("123", alunos.get(0).getMatricula());
        assertEquals("Arthur Tenorio", alunos.get(0).getNome());
    }

    @Test
    public void buscarAlunoInativo(){
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Manoel Elias");
        a1.setTurno(Turno.MATUTINO);
        a1.setCurso(Curso.DIREITO);
        a1.setStatus(Status.INATIVO);
        a1.setMatricula("125");

        alunoRepository.save(a1);

        List<Aluno> alunos = alunoRepository.findByStatusInativo();
        assertEquals(1, alunos.size());
        assertEquals("125", alunos.get(0).getMatricula());
        assertEquals("Manoel Elias", alunos.get(0).getNome());

    }
}
