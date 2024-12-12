package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static br.com.gerenciamento.enums.Status.ATIVO;
import static br.com.gerenciamento.enums.Status.INATIVO;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoRepositoryTest alunoRepositoryTest;

    private Aluno alunoAtivo;
    private Aluno alunoInativo;

    @Before
    public void setUp() {
        alunoAtivo = new Aluno();
        alunoAtivo.setNome("Aluno Ativo");
        alunoAtivo.setStatus(ATIVO);

        alunoInativo = new Aluno();
        alunoInativo.setNome("Aluno Inativo");
        alunoInativo.setStatus(INATIVO);
    }

    @Test
    public void testFindByStatusAtivo() {
        when(alunoRepository.findByStatusAtivo()).thenReturn(Arrays.asList(alunoAtivo));

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();

        Assert.assertNotNull(alunosAtivos);
        Assert.assertEquals(1, alunosAtivos.size());
        Assert.assertEquals("Aluno Ativo", alunosAtivos.get(0).getNome());
    }

    @Test
    public void testFindByStatusInativo() {
        when(alunoRepository.findByStatusInativo()).thenReturn(Arrays.asList(alunoInativo));

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();

        Assert.assertNotNull(alunosInativos);
        Assert.assertEquals(1, alunosInativos.size());
        Assert.assertEquals("Aluno Inativo", alunosInativos.get(0).getNome());
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        when(alunoRepository.findByNomeContainingIgnoreCase("Ativo")).thenReturn(Arrays.asList(alunoAtivo));

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Ativo");

        Assert.assertNotNull(alunos);
        Assert.assertEquals(1, alunos.size());
        Assert.assertEquals("Aluno Ativo", alunos.get(0).getNome());
    }

    @Test
    public void testFindByStatusAtivoListaVazia() {
        when(alunoRepository.findByStatusAtivo()).thenReturn(Arrays.asList());

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();

        Assert.assertNotNull(alunosAtivos);
        Assert.assertTrue(alunosAtivos.isEmpty());
    }
}
