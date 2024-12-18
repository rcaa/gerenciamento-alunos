package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceUsuario serviceUsuario;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLogin() throws Exception {
        // Simula que o usuário não existe
        when(serviceUsuario.loginUser("testUser", "testPassword"))
                .thenReturn(null);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user", "testUser")
                .param("senha", "testPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login")) // Verifica a view correta
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("msg", "Usuario não encontrado. Tente novamente")); // Verifica a mensagem de erro
    }

    @Test
    public void testCadastrarUsuario() throws Exception {
        mockMvc.perform(post("/salvarUsuario")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user", "novoUsuario")
                .param("senha", "novaSenha")
                .param("email", "novo@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testPaginaCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }
}
