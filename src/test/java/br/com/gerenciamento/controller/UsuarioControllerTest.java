package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void loginView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void cadastroView() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void loginUsuarioValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");

        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/login")
                        .param("user", "usuarioTeste")
                        .param("senha", "senhaTeste"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }

    @Test
    public void loginUsuarioInvalido() throws Exception {
        mockMvc.perform(post("/login")
                        .param("user", "usuarioInvalido")
                        .param("senha", "senhaErrada"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("msg"));
    }
}
