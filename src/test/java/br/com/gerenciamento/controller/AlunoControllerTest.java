package br.com.gerenciamento.controller;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

 
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void inserirAlunos() throws Exception {
        this.mockMvc
                .perform(post("/InsertAlunos")
                        .param("nome", "Laysa")
                        .param("curso", "CONTABILIDADE")
                        .param("matricula", "123456")
                        .param("turno", "NOTURNO")
                        .param("status", "ATIVO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    @Transactional
    public void inserirAlunoComErroDeValidacao() throws Exception {
        this.mockMvc
                .perform(post("/InsertAlunos")
                        .param("nome", "")
                        .param("curso", "ADMINISTRACAO")
                        .param("matricula", "12345")
                        .param("turno", "MATUTINO")
                        .param("status", "ATIVO"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"));
    }

    @Test
    public void listarAlunos() throws Exception {
        this.mockMvc
                .perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void listarAlunosAtivos() throws Exception {
        this.mockMvc
                .perform(get("/alunos-ativos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/alunos-ativos"))
                .andExpect(model().attributeExists("alunosAtivos"));
    }

    @Test
    public void listarAlunosInativos() throws Exception {
        this.mockMvc
                .perform(get("/alunos-inativos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/alunos-inativos"))
                .andExpect(model().attributeExists("alunosInativos"));
    }

}
