package br.com.gerenciamento.controller;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
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
    public void cadastrar() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(6L);
        usuario.setEmail("douglas@gmail.com");
        usuario.setUser("Douglas");
        usuario.setSenha("douglas123");

        int qtdInicialUsuario = usuarioRepository.findAll().size();
        usuarioController.cadastrar(usuario);
        int qtdFinalUsuario = usuarioRepository.findAll().size();

        assertEquals(qtdInicialUsuario + 1, qtdFinalUsuario);
        
    }

    @Test
    public void login() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(8L);
        usuario.setEmail("Marcelo@gmail.com");
        usuario.setUser("Marcelo");
        usuario.setSenha("Marcelo123");

        usuarioController.cadastrar(usuario);

        BindingResult br = new BeanPropertyBindingResult(usuario, "usuario");
        HttpSession session =  Mockito.mock(HttpSession.class);
        

        ModelAndView mv =usuarioController.login(usuario, br, session);
        Map<String,Object> map =  mv.getModel();
        Usuario usuarioLogado =  (Usuario) map.get("usuario");

        assertNotNull(usuarioLogado);
    }


    @Test
    public void loginInvalido() throws Exception{
        
        Usuario usuario = new Usuario();
        usuario.setId(8L);
        usuario.setEmail("Josue@gmail.com");
        usuario.setUser("Josue");
        usuario.setSenha("Josue123");

        usuarioController.cadastrar(usuario);

        

        BindingResult br = new BeanPropertyBindingResult(usuario, "usuario");
        HttpSession session =  Mockito.mock(HttpSession.class);
        
        usuario.setSenha(null);
        assertThrows(NullPointerException.class, () -> {
            usuarioController.login(usuario, br, session);
        }); 
    }

    @Test
    public void index() throws Exception{
        
        ModelAndView mv = usuarioController.index();
        Map<String, Object> map = mv.getModel();
        Aluno indexAluno = (Aluno) map.get("aluno");

        assertNotNull(indexAluno);
    }
}
