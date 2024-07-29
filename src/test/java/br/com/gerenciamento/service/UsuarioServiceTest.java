package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");
        usuario.setSenha("12345");

        serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioReturn = serviceUsuario.findByEmail("teste@example.com");

        Assert.assertNotNull(usuarioReturn);
        Assert.assertEquals("teste@example.com", usuario.getEmail());
    }

    @Test
    public void salvarUsuarioComEmailExistente() {
        try {
            Usuario usuario1 = new Usuario();
            usuario1.setEmail("existente@example.com");
            usuario1.setUser("Usuario1");
            usuario1.setSenha("senha1");
            serviceUsuario.salvarUsuario(usuario1);

            Usuario usuario2 = new Usuario();
            usuario2.setEmail("existente@example.com");
            usuario2.setUser("Usuario2");
            usuario2.setSenha("senha2");

            Assert.assertThrows(EmailExistsException.class, () -> {
                serviceUsuario.salvarUsuario(usuario2);
            });
        } catch (Exception e) {
            Assert.fail("Exceção: " + e.getMessage());
        }
    }

    @Test
    public void loginUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");
        usuario.setUser("Teste");
        usuario.setSenha(Util.md5("12345"));

        serviceUsuario.salvarUsuario(usuario);
        Usuario usuarioReturn = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        Assert.assertNotNull(usuarioReturn);
        Assert.assertEquals("Teste", usuarioReturn.getUser());
    }

    @Test
    public void loginCredenciaisIncorretas() {
        try {
            Usuario usuario = new Usuario();
            usuario.setEmail("teste@exemplo.com");
            usuario.setUser("Teste");
            usuario.setSenha(Util.md5("123"));
    
            Usuario usuarioReturn = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
            Assert.assertNull(usuarioReturn);
        } catch (Exception e) {
            Assert.fail("Erro: " + e.getMessage());
        }
    }
    
}
