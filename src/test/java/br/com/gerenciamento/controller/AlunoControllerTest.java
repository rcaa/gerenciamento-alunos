package br.com.gerenciamento.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc.perform(delete("/alunos"));
    }

    @Test
    public void testInserirAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"João Silva\",\"matricula\":\"12345\",\"curso\":\"ADMINISTRAÇÃO\",\"status\":\"ATIVO\",\"turno\":\"MATUTINO\"}"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testListagemAlunos() throws Exception {
        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void testEditarAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Carlos Lima\",\"matricula\":\"11223\",\"curso\":\"DIREITO\",\"status\":\"ATIVO\",\"turno\":\"NOTURNO\"}"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/editar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Carlos Lima Silva\",\"matricula\":\"11223\",\"curso\":\"DIREITO\",\"status\":\"ATIVO\",\"turno\":\"NOTURNO\"}"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testRemoverAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Ana Paula\",\"matricula\":\"44556\",\"curso\":\"ADMINISTRACAO\",\"status\":\"ATIVO\",\"turno\":\"MATUTINO\"}"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/remover/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }
}
