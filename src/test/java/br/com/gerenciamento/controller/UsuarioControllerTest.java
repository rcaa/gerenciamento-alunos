package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @Test
    public void testLoginPage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();

        // Testa se a página de login é carregada corretamente
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testCadastroUsuario() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();

        // Cria um novo usuário para testar o cadastro
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setUser("testeusuario");
        usuario.setSenha("senha123");

        // Testa se o cadastro do usuário é bem-sucedido
        mockMvc.perform(MockMvcRequestBuilders.post("/salvarUsuario")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", usuario.getEmail())
                        .param("user", usuario.getUser())
                        .param("senha", usuario.getSenha()))
                .andExpect(status().is3xxRedirection()) 
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLoginUsuario() throws Exception {
        // Configura o MockMvc para a nossa aplicação
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();

        // Cria um novo usuário para testar o login
        Usuario usuario = new Usuario();
        usuario.setUser("testeusuario");
        usuario.setSenha("senha123");

        // Testa se o login do usuário é bem-sucedido
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("user", usuario.getUser())
                        .param("senha", usuario.getSenha()))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void testLogoutUsuario() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();

        // Realiza o logout
        mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
