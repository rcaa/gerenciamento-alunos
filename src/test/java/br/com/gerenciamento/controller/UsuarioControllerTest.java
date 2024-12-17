package br.com.gerenciamento.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void deveRetornarPaginaDeLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void deveCadastrarNovoUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Julio");
        usuario.setSenha("julio123");
    
        // Enviar dados como parâmetros de formulário
        mockMvc.perform(post("/salvarUsuario")
                        .param("user", "julio")
                        .param("senha", "julio123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
    

    @Test
    public void deveFazerLoginComUsuarioValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Allana");
        usuario.setSenha("123allana");
        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/login")
                        .param("user", "Allana")
                        .param("senha", "123allana"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));
    }

    @Test
    public void deveRetornarErroAoTentarLoginComUsuarioInvalido() throws Exception {
        mockMvc.perform(post("/login")
                        .param("user", "julia")
                        .param("senha", "julaE23"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeDoesNotExist("msg"));
    }    
}
