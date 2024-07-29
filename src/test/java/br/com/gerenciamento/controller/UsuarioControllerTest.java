package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testCadastroPage() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testCadastrarUsuario() throws Exception {
        mockMvc.perform(post("/salvarUsuario")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "novo@teste.com")
                .param("user", "novouser")
                .param("senha", "654321"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLogout() throws Exception {
        Usuario usuarioLogout = new Usuario();
        usuarioLogout.setEmail("logoutuser@teste.com");
        usuarioLogout.setUser("logoutuser");
        usuarioLogout.setSenha(Util.md5("123456"));
        serviceUsuario.salvarUsuario(usuarioLogout);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogado", usuarioLogout);

        mockMvc.perform(post("/logout").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

}
