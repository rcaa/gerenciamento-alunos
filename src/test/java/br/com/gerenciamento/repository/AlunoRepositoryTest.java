package br.com.gerenciamento.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Test;
import org.junit.Assert;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    // teste 1 - salvar o aluno
    @Test
    public void testeSalvar() {
        Aluno aluno = new Aluno();
        aluno.setNome("Gustavo");
        aluno.setMatricula("123456");
        aluno.setCurso(Curso.INFORMATICA); 
        aluno.setStatus(Status.ATIVO); 
        aluno.setTurno(Turno.NOTURNO); 

        Aluno savedAluno = alunoRepository.save(aluno);
        assertNotNull(savedAluno.getId());
    }

    // teste 2 - mostra status de alunos ativos
    @Test
    public void testeMostrarAlunosAtivos(){
        List <Aluno> listAlunosAtivos = alunoRepository.findByStatusAtivo();
        assertNotNull(listAlunosAtivos);

        for( Aluno aluno : listAlunosAtivos){
            assertEquals(Status.ATIVO, aluno.getStatus());
        }
    }

    // teste 3 - mostra status de alunos inativos
    @Test
    public void testeMostrarAlunosInativos(){
        List <Aluno> listAlunosInativos = alunoRepository.findByStatusInativo();
        assertNotNull(listAlunosInativos);

        for( Aluno aluno : listAlunosInativos){
            assertEquals(Status.INATIVO, aluno.getStatus());
        }
    }

    // teste 4 - buscar alunos pelo nome 
    @Test
    public void testeBuscarNomeContainingIgnoreCase() {
        List <Aluno> listaAlunos = alunoRepository.findByNomeContainingIgnoreCase("gustavo");
        assertNotNull(listaAlunos);

        for (Aluno aluno : listaAlunos) {
            assertTrue(aluno.getNome().toLowerCase().contains("gustavo"));
        }
    }
}
