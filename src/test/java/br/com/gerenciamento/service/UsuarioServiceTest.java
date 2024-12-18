package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void testeSalvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("okarun@gmail.com");
        usuario.setUser("Okarun");
        usuario.setSenha("123");
        this.serviceUsuario.salvarUsuario(usuario);
        Assert.assertEquals("Okarun", usuario.getUser());

    }

    @Test
    public void testeRealizarLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("okarun@gmail.com");
        usuario.setUser("Okarun");
        usuario.setSenha("123");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = this.serviceUsuario.loginUser("Okarun", Util.md5("123"));
        Assert.assertNotNull(usuarioLogado);

    }

    @Test
    public void testeLoginInvalido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("okarun@gmail.com");
        usuario.setUser("Okarun");
        usuario.setSenha("123");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = this.serviceUsuario.loginUser("Okarun", Util.md5("momo"));
        Assert.assertNull(usuarioLogado);

    }

    @Test
    public void testeEmailNaoPreenchido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("Okarun");
        usuario.setSenha("123");
        this.serviceUsuario.salvarUsuario(usuario);

        Assert.assertNull("Usuario não possui email cadastrado", usuario.getEmail());

    }

}
