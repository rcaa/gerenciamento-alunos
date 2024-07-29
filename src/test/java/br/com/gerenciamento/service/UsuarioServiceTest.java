package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("novo@dominio.com");
        usuario.setUser("usuario3");
        usuario.setSenha("senha123");

        serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioSalvo = usuarioRepository.findByEmail("novo@dominio.com");
        Assert.assertNotNull(usuarioSalvo);
        Assert.assertEquals("usuario3", usuarioSalvo.getUser());
        Assert.assertEquals("novo@dominio.com", usuarioSalvo.getEmail());
        
        Assert.assertNotEquals("senha123", usuarioSalvo.getSenha());
    }


    @Test
    public void salvarUsuarioComEmailExistente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("existente@dominio.com");
        usuario.setUser("usuario1");
        usuario.setSenha("senha123");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("existente@dominio.com");
        usuarioRepository.save(usuarioExistente);

        try {
            serviceUsuario.salvarUsuario(usuario);
            Assert.fail("Deveria ter lançado uma EmailExistsException");
        } catch (EmailExistsException e) {
            Assert.assertEquals("Este email já esta cadastrado: existente@dominio.com", e.getMessage());
        }
    }

    

    @Test
    public void loginUserComSenhaCorreta() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@dominio.com");
        usuario.setUser("usuario");
        usuario.setSenha(Util.md5("senhaCorreta"));

        usuarioRepository.save(usuario);

        Usuario usuarioRetornado = serviceUsuario.loginUser("usuario", Util.md5("senhaCorreta"));

        Assert.assertNotNull(usuarioRetornado);
        Assert.assertEquals("usuario", usuarioRetornado.getUser());
        Assert.assertEquals("usuario@dominio.com", usuarioRetornado.getEmail());
    }
    
    @Test
    public void loginUserComSenhaIncorreta() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@dominio.com");
        usuario.setUser("usuario");
        usuario.setSenha(Util.md5("senhaCorreta"));

        usuarioRepository.save(usuario);

        Usuario usuarioRetornado = serviceUsuario.loginUser("usuario", Util.md5("senhaIncorreta"));

        Assert.assertNull(usuarioRetornado);
    }
}


