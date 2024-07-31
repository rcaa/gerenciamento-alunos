package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc //Faz o trabalho do Before para configurar o MockMvc
public class UsuarioControllerTest {

    @MockBean
    private ServiceUsuario serviceUsuario;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioController usuarioController;

    @Test
    public void testarTelaLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void cadastrarNovoUsuario() throws Exception {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUser("novoUsuario");
        novoUsuario.setEmail("novo@example.com");
        novoUsuario.setSenha("senhaSegura");

        ModelAndView modelAndView = this.usuarioController.cadastrar(novoUsuario);
        Assert.assertEquals("redirect:/", modelAndView.getViewName());
    }

    @Test
    public void loginUsuario() throws Exception {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setId(1L);
        novoUsuario.setUser("novoUsuario");
        novoUsuario.setEmail("novo@example.com");
        novoUsuario.setSenha("senhaSegura");

        when(serviceUsuario.loginUser(eq("novoUsuario"), eq(Util.md5("senhaSegura")))).thenReturn(novoUsuario);

        HttpSession mockSession = Mockito.mock(HttpSession.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(novoUsuario, "usuario");
        ModelAndView modelAndView = this.usuarioController.login(novoUsuario, bindingResult, mockSession);

        Assert.assertEquals("home/index", modelAndView.getViewName());
    }

    @Test
    public void LogoutUsuario() throws Exception {
        HttpSession mockSession = Mockito.mock(HttpSession.class);
        ModelAndView modelAndView = this.usuarioController.logout(mockSession);
        Assert.assertEquals("login/login", modelAndView.getViewName());
    }
}
