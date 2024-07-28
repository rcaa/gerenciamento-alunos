package br.com.gerenciamento.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.model.Usuario;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Test
    public void cadastrar() throws Exception{
        Usuario user = new Usuario();
        user.setId(1L);
        user.setEmail("teste@email.com");
        user.setUser("teste");
        user.setSenha("teste123");

        ModelAndView modelAndView = this.usuarioController.cadastrar(user);

        assertEquals("redirect:/", modelAndView.getViewName());
    }

    @Test
    public void cadastrarUsuarioSemUserName() {
        Usuario user = new Usuario();
        user.setId(2L);
        user.setEmail("teste2@email.com");
        user.setUser("");
        user.setSenha("teste321");

        assertThrows(ConstraintViolationException.class, () -> {
            this.usuarioController.cadastrar(user);
        });
    }

    @Test
    public void loginComUsuarioInexistente() throws Exception {
        Usuario user = new Usuario();
        user.setId(2L);
        user.setEmail("teste2@email.com");
        user.setUser("teste2");
        user.setSenha("teste321");

        HttpSession mockSession = Mockito.mock(HttpSession.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "usuario");

        ModelAndView modelAndView = this.usuarioController.login(user, bindingResult, mockSession);

        assertEquals("login/cadastro", modelAndView.getViewName());
    }

    @Test
    public void logout() {
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        ModelAndView modelAndView = this.usuarioController.logout(mockSession);

        assertEquals("login/login", modelAndView.getViewName());
    }

}
