package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceAluno serviceAluno;

    private Aluno aluno;

    @BeforeEach
    public void setUp() {
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aluno Teste");
    }

    @Test
    public void testInsertAlunosPage() throws Exception {
        // Testa se a página de inserção de alunos está sendo carregada corretamente
        mockMvc.perform(MockMvcRequestBuilders.get("/inserirAlunos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Aluno/formAluno"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("aluno"));
    }
    
    @Test
    public void testListarAlunos() throws Exception {
        // Simula a listagem de alunos
        List<Aluno> alunos = Arrays.asList(aluno);
        when(serviceAluno.findAll()).thenReturn(alunos);

        mockMvc.perform(MockMvcRequestBuilders.get("/alunos-adicionados"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Aluno/listAlunos"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("alunosList"));
    }
    
    @Test
    public void testPesquisarAluno() throws Exception {
        // Simula a pesquisa de um aluno
        List<Aluno> alunos = Arrays.asList(aluno);
        when(serviceAluno.findByNomeContainingIgnoreCase("Aluno Teste")).thenReturn(alunos);

        mockMvc.perform(MockMvcRequestBuilders.post("/pesquisar-aluno")
                        .param("nome", "Aluno Teste"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Aluno/pesquisa-resultado"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ListaDeAlunos"));
    }
    
    @Test
    public void testListaAlunosInativos() throws Exception {
        // Simula a listagem de alunos inativos
        List<Aluno> alunosInativos = Arrays.asList(aluno);
        when(serviceAluno.findByStatusInativo()).thenReturn(alunosInativos);

        mockMvc.perform(MockMvcRequestBuilders.get("/alunos-inativos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Aluno/alunos-inativos"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("alunosInativos"));
    }
}