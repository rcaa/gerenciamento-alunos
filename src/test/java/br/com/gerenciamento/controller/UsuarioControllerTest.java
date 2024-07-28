package br.com.gerenciamento.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void login() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("login/login"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("usuario"));
    }

    @Test
    @Transactional
    public void index() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/index"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("home/index"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("aluno"));
    }

    @Test
    @Transactional
    public void cadastrar() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/cadastro"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("login/cadastro"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("usuario"));
    }

    @Test
    @Transactional
    public void testSalvarUsuario() throws Exception {
        mockMvc.perform(post("/salvarUsuario")
                .param("nome", "Test User")
                .param("senha", "12345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
