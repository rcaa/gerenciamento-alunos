package br.com.gerenciamento.controller;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import br.com.gerenciamento.model.Usuario;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.service.ServiceUsuario;
import jakarta.validation.ConstraintViolationException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {
     @Autowired
    private UsuarioController usuarioController;

    // teste 1 - cadastrar um usuario
    @Test
    public void testeCadastrarUsuario() throws Exception{
        Usuario usuario= new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Gustavo@email.com");
        usuario.setUser("GustavoUsuario");
        usuario.setSenha("15680");

        ModelAndView modelAndView = this.usuarioController.cadastrar(usuario);
        assertEquals("redirect:/", modelAndView.getViewName());
    }

    //teste 2 - teste cadastrar usuario sem email
     @Test
    public void testeCadastrarUsuarioInvalido() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("Gustavo@gmail.com");
        usuario.setUser("Gu");
        usuario.setSenha("112233");

        assertThrows(ConstraintViolationException.class, () -> {
            this.usuarioController.cadastrar(usuario);
        });
    }

    // teste 3 - teste login com usuario que nao existe
    @Test
    public void testeloginUsuarioNaoExiste() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("testeUsuario@email.com");
        usuario.setUser("usuario");
        usuario.setSenha("123456");

        HttpSession mockSession = Mockito.mock(HttpSession.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(usuario, "usuario");
        ModelAndView modelAndView = this.usuarioController.login(usuario, bindingResult, mockSession);
        assertEquals("login/cadastro", modelAndView.getViewName());
    }

    // teste 4 - sair 
    @Test
    public void sair() {
        HttpSession mockSession = Mockito.mock(HttpSession.class);
        ModelAndView modelAndView = this.usuarioController.logout(mockSession);
        assertEquals("login/login", modelAndView.getViewName());
    }
}
