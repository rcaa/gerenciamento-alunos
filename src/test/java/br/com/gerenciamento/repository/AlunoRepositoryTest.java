package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.model.Aluno;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @MockBean
    private AlunoRepository mockAlunoRepository;

    private List<Aluno> alunosAtivos;
    private List<Aluno> alunosInativos;

    @Before
    public void setUp() {
        // Alunos ativos
        Aluno alunoAtivo1 = new Aluno();
        alunoAtivo1.setId(1L);
        alunoAtivo1.setNome("alunoAtivo1");
        alunoAtivo1.setStatus(Status.ATIVO);

        Aluno alunoAtivo2 = new Aluno();
        alunoAtivo2.setId(2L);
        alunoAtivo2.setNome("alunoAtivo2");
        alunoAtivo2.setStatus(Status.ATIVO);

        alunosAtivos = Arrays.asList(alunoAtivo1, alunoAtivo2);

        // Alunos inativos
        Aluno alunoInativo1 = new Aluno();
        alunoInativo1.setId(3L);
        alunoInativo1.setNome("alunoInativo1");
        alunoInativo1.setStatus(Status.INATIVO);

        Aluno alunoInativo2 = new Aluno();
        alunoInativo2.setId(4L);
        alunoInativo2.setNome("alunoInativo2");
        alunoInativo2.setStatus(Status.INATIVO);

        alunosInativos = Arrays.asList(alunoInativo1, alunoInativo2);
    }

    // #1 encontrar todos os alunos com o status ativo
    @Test
    public void encontrarAlunosAtivos() {
        Mockito.when(mockAlunoRepository.findByStatusAtivo()).thenReturn(alunosAtivos);

        List<Aluno> result = mockAlunoRepository.findByStatusAtivo();

        assertEquals(2, result.size());
        assertEquals("alunoAtivo1", result.get(0).getNome());
        assertEquals("alunoAtivo2", result.get(1).getNome());
    }

    // #2 encontrar todos os alunos com o status inativo
    @Test
    public void encontrarAlunosInativos() {
        Mockito.when(mockAlunoRepository.findByStatusInativo()).thenReturn(alunosInativos);

        List<Aluno> result = mockAlunoRepository.findByStatusInativo();

        assertEquals(2, result.size());
        assertEquals("alunoInativo1", result.get(0).getNome());
        assertEquals("alunoInativo2", result.get(1).getNome());
    }

    // #3 encontrar todos os alunos ignorando maisculas e minusculas
    @Test
    public void encontrarNomeContendoIgnoreCase() {
        List<Aluno> todosOsAlunos = new ArrayList<>(alunosAtivos);
        todosOsAlunos.addAll(alunosInativos);

        Mockito.when(mockAlunoRepository.findByNomeContainingIgnoreCase("aLuNo")).thenReturn(todosOsAlunos);

        List<Aluno> result = mockAlunoRepository.findByNomeContainingIgnoreCase("aLuNo");

        assertEquals(4, result.size());
        assertEquals("alunoAtivo1", result.get(0).getNome());
        assertEquals("alunoAtivo2", result.get(1).getNome());
        assertEquals("alunoInativo1", result.get(2).getNome());
        assertEquals("alunoInativo2", result.get(3).getNome());
    }

    // #4 não encontrar nenhum aluno quando a pesquisa não tiver resultados
    @Test
    public void naoEncontrarAlunosQuandoVazia() {
        Mockito.when(mockAlunoRepository.findByNomeContainingIgnoreCase("aLuNo")).thenReturn(null);

        List<Aluno> result = mockAlunoRepository.findByNomeContainingIgnoreCase("aLuNo");

        assertNull(result);
    }
}
