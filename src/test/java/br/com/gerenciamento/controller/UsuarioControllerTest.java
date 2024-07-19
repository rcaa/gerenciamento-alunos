package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ServiceUsuario serviceUsuario;

    private MockHttpSession session;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        session = new MockHttpSession();
    }

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testIndexPage() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void testCadastrarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("testUser");
        usuario.setSenha("testPassword");

        mockMvc.perform(post("/salvarUsuario")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("user", usuario.getUser())
                        .param("senha", usuario.getSenha()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Mockito.verify(serviceUsuario, Mockito.times(1)).salvarUsuario(Mockito.any(Usuario.class));
    }

    @Test
    public void testLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("testUser");
        usuario.setSenha("testPassword");

        Mockito.when(serviceUsuario.loginUser(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(usuario);

        mockMvc.perform(post("/login")
                        .session(session)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("user", usuario.getUser())
                        .param("senha", usuario.getSenha()))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));

        Mockito.verify(serviceUsuario, Mockito.times(1)).loginUser(Mockito.anyString(), Mockito.anyString());
    }
}
