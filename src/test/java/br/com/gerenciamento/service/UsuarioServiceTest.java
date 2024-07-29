package br.com.gerenciamento.service;

import static org.junit.Assert.assertNull;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Teste1");
        usuario.setEmail("test@example.com");
        usuario.setSenha("senha");
        this.serviceUsuario.salvarUsuario(usuario);

        Assert.assertTrue(usuario.getUser().equals("Teste1"));

    }

    @Test
    public void salvarUsuarioComEmailDuplicado() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setUser("TesteDuplicado");
        usuario1.setEmail("duplicado@example.com");
        usuario1.setSenha("senha456");

        serviceUsuario.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setUser("TesteDuplicado2");
        usuario2.setEmail("duplicado@example.com");
        usuario2.setSenha("senha789");

        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });

    }

    @Test
    public void loginUserComCredenciaisInvalidas() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("UsuarioLogin");
        usuario.setEmail("login00@example.com");
        usuario.setSenha("senha");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser("UsuarioLogin", "senhaErrada");
        assertNull(usuarioLogado);
    }

    @Test
    public void loginUserComCredenciaisValidas() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("UsuarioLogin");
        usuario.setEmail("login@example.com");
        usuario.setSenha("senha");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        Assert.assertTrue(usuarioLogado.getUser().equals("UsuarioLogin"));
    }

}
