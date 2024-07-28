package br.com.gerenciamento.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInsertAlunos() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeExists("aluno"));
    }
    

    @Test
    public void testRemoverAluno() throws Exception {
        // Supondo que um aluno com ID 1 existe no banco de dados para fins de teste
        mockMvc.perform(get("/remover/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    public void testFiltroAlunos() throws Exception {
        mockMvc.perform(get("/filtro-alunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/filtroAlunos"));
    }

    @Test
    public void testListaAlunosAtivos() throws Exception {
        mockMvc.perform(get("/alunos-ativos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/alunos-ativos"))
                .andExpect(model().attributeExists("alunosAtivos"));
    }

    
}
