package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @InjectMocks
    private ServiceAluno serviceAluno;

    @Mock
    private AlunoRepository alunoRepository;

    private Aluno aluno;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        // Configure outros atributos de aluno conforme necessário
    }

    @Test
    public void save() {
        serviceAluno.save(aluno);
        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    public void findAll() {
        when(alunoRepository.findAll()).thenReturn(Arrays.asList(aluno));
        List<Aluno> alunos = serviceAluno.findAll();
        assertNotNull(alunos);
        assertEquals(1, alunos.size());
        assertEquals("Vinicius", alunos.get(0).getNome());
    }

    @Test
    public void getById() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        Aluno alunoRetorno = serviceAluno.getById(1L);
        assertNotNull(alunoRetorno);
        assertEquals("Vinicius", alunoRetorno.getNome());
    }

    @Test
    public void deleteById() {
        serviceAluno.deleteById(1L);
        verify(alunoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findByStatusAtivo() {
        when(alunoRepository.findByStatusAtivo()).thenReturn(Arrays.asList(aluno));
        List<Aluno> alunos = serviceAluno.findByStatusAtivo();
        assertNotNull(alunos);
        assertEquals(1, alunos.size());
        assertEquals("Vinicius", alunos.get(0).getNome());
    }

    @Test
    public void findByStatusInativo() {
        when(alunoRepository.findByStatusInativo()).thenReturn(Arrays.asList(aluno));
        List<Aluno> alunos = serviceAluno.findByStatusInativo();
        assertNotNull(alunos);
        assertEquals(1, alunos.size());
        assertEquals("Vinicius", alunos.get(0).getNome());
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        when(alunoRepository.findByNomeContainingIgnoreCase("vinicius")).thenReturn(Arrays.asList(aluno));
        List<Aluno> alunos = serviceAluno.findByNomeContainingIgnoreCase("vinicius");
        assertNotNull(alunos);
        assertEquals(1, alunos.size());
        assertEquals("Vinicius", alunos.get(0).getNome());
    }
}
