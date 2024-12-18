package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceUsuario serviceUsuario;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setUp() { mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .build(); }

    @Test
    public void loginUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario");
        usuario.setSenha("12345");

        // Configura o mock para retornar o usuário válido
        when(serviceUsuario.loginUser(anyString(), anyString())).thenReturn(usuario);

        mockMvc.perform(post("/login")
                .flashAttr("usuario", usuario))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index")) // Confirma que redireciona para a página inicial
                .andExpect(model().attributeExists("usuario")); // Verifica se o modelo tem "aluno"
    }

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@gmail.com");
        usuario.setUser("usuario");
        usuario.setSenha("12345");

        mockMvc.perform(post("/salvarUsuario")
                .flashAttr("usuario", usuario))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }

    @Test
    void cadastrarNovoUsuario() throws Exception {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUser("novoUsuario");
        novoUsuario.setSenha("54321");

        doNothing().when(serviceUsuario).salvarUsuario(any(Usuario.class));

        mockMvc.perform(post("/salvarUsuario")
                .flashAttr("usuario", novoUsuario))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void logoutUsuario() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogado", new Usuario());

        mockMvc.perform(post("/logout").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"));
    }
}