package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import br.com.gerenciamento.util.Util;

import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private ServiceUsuario serviceUsuario;

    @Mock
    private HttpSession session;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        ModelAndView modelAndView = usuarioController.login();
        assertEquals("login/login", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("usuario"));
    }

    @Test
    public void testIndex() {
        ModelAndView modelAndView = usuarioController.index();
        assertEquals("home/index", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("aluno"));
    }

    @Test
    public void testCadastrar() {
        ModelAndView modelAndView = usuarioController.cadastrar();
        assertEquals("login/cadastro", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("usuario"));
    }

    @Test
    public void testSalvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Usuario Teste");
        usuario.setEmail("teste@example.com");
        usuario.setSenha("senha123");

        // Simula o comportamento do método save
        doNothing().when(serviceUsuario).save(any(Usuario.class));

        // Simula a chamada do método salvarUsuario do controller
        ModelAndView modelAndView = usuarioController.cadastrar(usuario);
        assertEquals("redirect:/", modelAndView.getViewName());

        // Verifica se o usuário foi salvo
        verify(serviceUsuario, times(1)).save(usuario);
    }

    @Test
    public void testLoginUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        Usuario userLogin = new Usuario();
        when(serviceUsuario.loginUser(usuario.getUser(), Util.md5(usuario.getSenha()))).thenReturn(userLogin);

        ModelAndView modelAndView = usuarioController.login(usuario, bindingResult, session);
        assertEquals("home/index", modelAndView.getViewName());
        verify(session, times(1)).setAttribute("usuarioLogado", userLogin);
    }

    @Test
    public void testLogout() {
        when(session.getAttribute("usuarioLogado")).thenReturn(new Usuario());

        ModelAndView modelAndView = usuarioController.logout(session);
        verify(session, times(1)).invalidate();
        assertEquals("login/login", modelAndView.getViewName());
    }
}
