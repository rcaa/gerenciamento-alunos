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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceUsuario serviceUsuario;

    private Usuario usuario;

    @BeforeEach
    public void setup() throws Exception {
        usuario = new Usuario();
        usuario.setUser("testuser");
        usuario.setEmail("testuser@example.com");
        usuario.setSenha(Util.md5("testpassword"));
        serviceUsuario.salvarUsuario(usuario);
    }

    @Test
    @Transactional
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", instanceOf(Usuario.class)));
    }

    @Test
    @Transactional
    public void testCadastroPage() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", instanceOf(Usuario.class)));
    }

    @Test
    @Transactional
    public void testLoginUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("existinguser");
        usuario.setEmail("existinguser@example.com");
        usuario.setSenha(Util.md5("existingpassword"));
        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user", "existinguser")
                .param("senha", "existingpassword"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    @Transactional
    public void testLoginUsuarioInvalido() throws Exception {
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user", "invaliduser")
                .param("senha", "invalidpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("msg", "Usuario não encontrado. Tente novamente"));
    }
}