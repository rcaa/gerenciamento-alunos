package br.com.gerenciamento.controller;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
    public void testeInserirAlunos() throws Exception {
        this.mockMvc
                .perform(post("/InsertAlunos")
                        .param("nome", "AlunoTeste")
                        .param("curso", "ADMINISTRACAO")
                        .param("matricula", "123456")
                        .param("turno", "MATUTINO")
                        .param("status", "ATIVO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    @Transactional
    public void testeInserirAlunoComErroDeValidacao() throws Exception {
        this.mockMvc
                .perform(post("/InsertAlunos")
                        .param("nome", "AlunoTeste")
                        .param("curso", "ADMINISTRACAO")
                        .param("matricula", "")
                        .param("turno", "MATUTINO")
                        .param("status", "ATIVO"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"));
    }

    @Test
    public void testeListarAlunos() throws Exception {
        this.mockMvc
                .perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));

            }
    @Test
    public void testeListarAlunosInativos() throws Exception {
        this.mockMvc
                .perform(get("/alunos-inativos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/alunos-inativos"))
                .andExpect(model().attributeExists("alunosInativos"));
    }

    @Test
    public void testeListarAlunosAtivos() throws Exception {
        this.mockMvc
                .perform(get("/alunos-ativos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/alunos-ativos"))
                .andExpect(model().attributeExists("alunosAtivos"));
    }

    @Test
    @Transactional
    public void removerAluno() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/remover/1"))
               .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
               .andExpect(MockMvcResultMatchers.view().name("redirect:/alunos-adicionados"));
    }

}