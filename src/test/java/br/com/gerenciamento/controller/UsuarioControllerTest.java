package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAcessoPaginaLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login/login"));
    }

    @Test
    public void testCadastroUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setUser("testuser");
        usuario.setSenha("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/salvarUsuario")
                        .param("email", usuario.getEmail())
                        .param("user", usuario.getUser())
                        .param("senha", usuario.getSenha()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
    @Test
    public void testLoginComCredenciaisValidas() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("valid@example.com");
        usuario.setUser("validuser");
        usuario.setSenha(Util.md5("validpassword")); // Certifique-se de que a senha está criptografada corretamente
        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("user", "validuser")
                        .param("senha", "validpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }


    @Test
    public void testLogout() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("user@example.com");
        usuario.setUser("user");
        usuario.setSenha(Util.md5("password"));
        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("user", "user")
                        .param("senha", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
