package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoRepositoryTest alunoRepositoryTest;

    private Aluno aluno1;
    private Aluno aluno2;

    @Before
    public void setUp() {
        aluno1 = new Aluno();
        aluno1.setNome("Carlos Silva");
        aluno1.setStatus(Status.ATIVO);

        aluno2 = new Aluno();
        aluno2.setNome("Ana Souza");
        aluno2.setStatus(Status.INATIVO);
    }

    @Test
    public void testFindByStatusAtivo() {
        when(alunoRepository.findByStatusAtivo()).thenReturn(Arrays.asList(aluno1));

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();

        Assert.assertNotNull(alunosAtivos);
        Assert.assertEquals(1, alunosAtivos.size());
        Assert.assertEquals("Carlos Silva", alunosAtivos.get(0).getNome());
    }

    @Test
    public void testFindByStatusInativo() {
        when(alunoRepository.findByStatusInativo()).thenReturn(Arrays.asList(aluno2));

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();

        Assert.assertNotNull(alunosInativos);
        Assert.assertEquals(1, alunosInativos.size());
        Assert.assertEquals("Ana Souza", alunosInativos.get(0).getNome());
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        when(alunoRepository.findByNomeContainingIgnoreCase("Silva")).thenReturn(Arrays.asList(aluno1));

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Silva");

        Assert.assertNotNull(alunos);
        Assert.assertEquals(1, alunos.size());
        Assert.assertEquals("Carlos Silva", alunos.get(0).getNome());
    }

    @Test
    public void testFindByStatusAtivoListaVazia() {
        when(alunoRepository.findByStatusAtivo()).thenReturn(Arrays.asList());

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();

        Assert.assertNotNull(alunosAtivos);
        Assert.assertTrue(alunosAtivos.isEmpty());
    }
}
