package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ServiceUsuario serviceUsuario;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void testCadastroUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario");
        usuario.setSenha("senha");

        doNothing().when(serviceUsuario).salvarUsuario(any(Usuario.class));

        mockMvc.perform(post("/salvarUsuario")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user", usuario.getUser())
                .param("senha", usuario.getSenha()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLoginUsuarioValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario");
        usuario.setSenha("senha");

        when(serviceUsuario.loginUser(anyString(), anyString())).thenReturn(usuario);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user", usuario.getUser())
                .param("senha", usuario.getSenha()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }

    @Test
    public void testLoginUsuarioInvalido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario");
        usuario.setSenha("senha");

        when(serviceUsuario.loginUser(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user", usuario.getUser())
                .param("senha", usuario.getSenha()))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attribute("msg", "Usuario não encontrado. Tente novamente"));
    }

    @Test
    public void testLogoutUsuario() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
