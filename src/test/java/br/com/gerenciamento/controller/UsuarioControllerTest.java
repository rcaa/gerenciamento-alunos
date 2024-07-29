package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ServiceUsuario serviceUsuario;

    private Usuario usuario;

    @BeforeEach
    public void setup() throws Exception {
        usuario = new Usuario();
        usuario.setUser("usuario de teste");
        usuario.setEmail("testuser@email.com");
        usuario.setSenha(Util.md5("password"));
        serviceUsuario.salvarUsuario(usuario);
    }

    @Test
    public void testLoginUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", instanceOf(Usuario.class)));
    }

    @Test
    public void testLoginUser() throws Exception {
        mvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));
    }

    @Test
    public void testCadastroPage() throws Exception {
        mvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", instanceOf(Usuario.class)));
    }

    @Test
    public void testLogout() throws Exception {
        HttpSession session = mvc.perform(post("/login")
                .param("user", "testuser")
                .param("senha", "password"))
                .andReturn()
                .getRequest()
                .getSession();

        mvc.perform(post("/logout").session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(request().sessionAttributeDoesNotExist("usuario"));
    }

}
