package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BeanPropertyBindingResult;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Test
    public void testLoginUsuarioNaoEncontrado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("invalidUser");
        usuario.setSenha("wrongPassword");

        BindingResult bindingResult = new BeanPropertyBindingResult(usuario, "usuario");

        ModelAndView modelAndView = usuarioController.login(usuario, bindingResult, null);

        assertThat(modelAndView.getViewName()).isEqualTo("login/cadastro");

        assertThat(modelAndView.getModel()).containsKey("usuario");
    }

    @Test
    public void testCadastroUsuarioComSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("user@example.com");
        usuario.setUser("newuser");
        usuario.setSenha("password123");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("email", "user@example.com");
        request.setParameter("user", "newuser");
        request.setParameter("senha", "password123");

        ModelAndView modelAndView = usuarioController.cadastrar(usuario);

        assertThat(modelAndView.getViewName()).isEqualTo("redirect:/");
    }

    @Test
    public void testCadastroPageLoad() {
        ModelAndView modelAndView = usuarioController.cadastrar();
        assertThat(modelAndView.getViewName()).isEqualTo("login/cadastro");
        assertThat(modelAndView.getModel()).containsKey("usuario");
    }

    @Test
    public void testLoginPageLoad() {
        ModelAndView modelAndView = usuarioController.login();
        assertThat(modelAndView.getViewName()).isEqualTo("login/login");
        assertThat(modelAndView.getModel()).containsKey("usuario");
    }
}