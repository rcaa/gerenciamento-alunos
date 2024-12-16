package br.com.gerenciamento.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // #1 Verifica se a página de login é carregada corretamente
    @Test
    public void testPaginaLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    // #2 Verifica se a página inicial é carregada corretamente
    @Test
    public void testPaginaIndex() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }

    // #3 Verifica se a página de cadastro é carregada corretamente
    @Test
    public void testPaginaCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }

    // #4 Verifica se acessar uma página inexistente retorna erro
    @Test
    public void testPaginaInexistenteRetornaErro() throws Exception {
        mockMvc.perform(get("/pagina-inexistente"))
                .andExpect(status().isNotFound());
    }
}
