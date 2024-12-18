package br.com.gerenciamento.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void loginPage() {
        ModelAndView mv = usuarioController.login();
        assertEquals("login/login", mv.getViewName()); // Valida se a página de login está correta
        assertNotNull(mv.getModel().get("usuario")); // Valida se o objeto 'usuario' está presente
    }

    @Test
    public void insertUser() throws Exception {

        usuarioRepository.deleteAll();

        long id = 1L;
        String username = "Guilherme";
        String senha = "password";
        String email = "user@gmail.com";

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setUser(username);
        usuario.setSenha(senha);

        ModelAndView mv = usuarioController.cadastrar(usuario);
        assertEquals("redirect:/", mv.getViewName());

        List<Usuario> usuarios = usuarioRepository.findAll();

        assertEquals(username, usuarios.get(0).getUser());

    }

    @Test
    public void index() throws Exception {

        ModelAndView mv = usuarioController.index();

        Aluno aluno = (Aluno) mv.getModel().get("aluno");

        assertNotNull(aluno);
    }

    @Test
    public void loginInvalido() throws Exception {

        usuarioRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("user@gmail.com");
        usuario.setUser("user");
        usuario.setSenha("password");

        usuarioController.cadastrar(usuario);

        BindingResult bindingResult = new BeanPropertyBindingResult(usuario, "usuario");
        HttpSession httpSession = Mockito.mock(HttpSession.class);

        usuario.setSenha(null);

        assertThrows(NullPointerException.class, () -> {
            usuarioController.login(usuario, bindingResult, httpSession);
        });
    }

}
