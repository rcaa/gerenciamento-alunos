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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @MockBean
    private AlunoRepository mockAlunoRepository;

    private List<Aluno> alunosAtivos;
    private List<Aluno> alunosInativos;

    @Before
    public void setUp() {
        // Alunos com status ATIVO
        Aluno alunoAtivo1 = new Aluno();
        alunoAtivo1.setId(1L);
        alunoAtivo1.setNome("Luis Felipe");
        alunoAtivo1.setStatus(Status.ATIVO);

        Aluno alunoAtivo2 = new Aluno();
        alunoAtivo2.setId(2L);
        alunoAtivo2.setNome("Maria Fernanda");
        alunoAtivo2.setStatus(Status.ATIVO);

        alunosAtivos = Arrays.asList(alunoAtivo1, alunoAtivo2);

        // Alunos com status INATIVO
        Aluno alunoInativo1 = new Aluno();
        alunoInativo1.setId(3L);
        alunoInativo1.setNome("Gustavo Henrique");
        alunoInativo1.setStatus(Status.INATIVO);

        Aluno alunoInativo2 = new Aluno();
        alunoInativo2.setId(4L);
        alunoInativo2.setNome("Carol Souza");
        alunoInativo2.setStatus(Status.INATIVO);

        alunosInativos = Arrays.asList(alunoInativo1, alunoInativo2);
    }

    // #1 - Encontrar alunos com status ATIVO
    @Test
    public void encontrarAlunosAtivos() {
        Mockito.when(mockAlunoRepository.findByStatusAtivo()).thenReturn(alunosAtivos);

        List<Aluno> result = mockAlunoRepository.findByStatusAtivo();

        assertEquals(2, result.size());
        assertEquals("Luis Felipe", result.get(0).getNome());
        assertEquals("Maria Fernanda", result.get(1).getNome());
    }

    // #2 - Encontrar alunos com status INATIVO
    @Test
    public void encontrarAlunosInativos() {
        Mockito.when(mockAlunoRepository.findByStatusInativo()).thenReturn(alunosInativos);

        List<Aluno> result = mockAlunoRepository.findByStatusInativo();

        assertEquals(2, result.size());
        assertEquals("Gustavo Henrique", result.get(0).getNome());
        assertEquals("Carol Souza", result.get(1).getNome());
    }

    // #3 - Encontrar alunos pelo nome ignorando maiúsculas e minúsculas
    @Test
    public void encontrarNomeContendoIgnoreCase() {
        List<Aluno> todosOsAlunos = new ArrayList<>(alunosAtivos);
        todosOsAlunos.addAll(alunosInativos);

        Mockito.when(mockAlunoRepository.findByNomeContainingIgnoreCase("aLuNo")).thenReturn(todosOsAlunos);

        List<Aluno> result = mockAlunoRepository.findByNomeContainingIgnoreCase("aLuNo");

        assertEquals(4, result.size());
        assertEquals("Luis Felipe", result.get(0).getNome());
        assertEquals("Maria Fernanda", result.get(1).getNome());
        assertEquals("Gustavo Henrique", result.get(2).getNome());
        assertEquals("Carol Souza", result.get(3).getNome());
    }

    // #4 - Não encontrar alunos quando a pesquisa não retorna resultados
    @Test
    public void naoEncontrarAlunosQuandoVazia() {
        Mockito.when(mockAlunoRepository.findByNomeContainingIgnoreCase("inexistente")).thenReturn(new ArrayList<>());

        List<Aluno> result = mockAlunoRepository.findByNomeContainingIgnoreCase("inexistente");

        assertEquals(0, result.size());
    }
}
