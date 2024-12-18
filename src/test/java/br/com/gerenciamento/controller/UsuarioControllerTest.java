package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração utilizando MockMvc.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Testa salvar usuário.
     */
    @Test
    public void salvarUsuario() throws Exception {
        mockMvc.perform(post("/salvarUsuario").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user", "user00")
                .param("senha", "password")
                .param("email", "user@email.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    /**
     * Testa o index.
     */
    @Test
    public void verificarIndex() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }
    /**
     * Testa o logout de um usuário.
     */
    @Test
    public void veririficarLogout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    /**
     * Testa o login de um usuário.
     */
    @Test
    public void verificarLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("user01@email.com");
        usuario.setUser("user01");
        usuario.setSenha("password");

        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/login")
                .param("user", "user01")
                .param("senha", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }
}
