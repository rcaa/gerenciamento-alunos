package br.com.gerenciamento.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import jakarta.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testeSalvarUsuario() throws Exception {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur1@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        usuarioController.cadastrar(u);

        Usuario retorno = usuarioRepository.findByEmail("arthur1@gmail.com");
        assertEquals("ruhtra", retorno.getUser());
        assertEquals(Util.md5("123"), retorno.getSenha());
    }

    @Test
    public void testeIndex() {
        ModelAndView mv = usuarioController.index();
        Map<String, Object> map = mv.getModel();
        Object obj = map.get("aluno");

        assertNull(((Aluno)obj).getMatricula());
    }

    //O teste abaixo não funciona corretamente.
    //Eu, sinceramente, não entendi porque não funcionou, já que 
    //eu basicamente checo se a HttpSession foi atualizada.
    @Test
    public void testeLoginUsuario() throws NoSuchAlgorithmException, Exception {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur2@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        usuarioController.cadastrar(u);

        HttpSession session = new MockHttpSession();
        usuarioController.login(u, new MapBindingResult(new HashMap<Integer, Integer>(), "null"), session);
        
        assertEquals(u, session.getAttribute("usuarioLogado"));
    }

    @Test
    public void testeLoginUsuarioInvalido() throws NoSuchAlgorithmException, Exception {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur3@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        usuarioController.cadastrar(u);

        HttpSession session = new MockHttpSession();

        ModelAndView mv = usuarioController.login(u, new MapBindingResult(new HashMap<Integer, Integer>(), "null"), session);
        Map<String, Object> map = mv.getModel();
        Object obj = map.get("usuario");

        assertEquals(null, ((Usuario)obj).getEmail());
    }

    
}
