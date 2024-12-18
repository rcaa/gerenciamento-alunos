package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuarioComEmailExistente() {
        Usuario usuario1 = new Usuario();
        usuario1.setEmail("teste@dominio.com");
        usuario1.setSenha("senha123");

        try {
            this.serviceUsuario.salvarUsuario(usuario1);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof EmailExistsException);
            Assert.assertEquals("Este email já esta cadastrado: teste@dominio.com", e.getMessage());
        }

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("teste@dominio.com");
        usuario2.setSenha("senha456");

        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });
    }

    @Test
    public void loginComEmailInvalido() {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@dominio.com");
        usuario.setSenha("senha123");

        Usuario usuarioLogado = this.serviceUsuario.loginUser("emailinvalido@dominio.com", "senha123");

        Assert.assertNull(usuarioLogado);
    }

    @Test
    public void loginComCredenciaisInvalidas() {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@dominio.com");
        usuario.setSenha("senha123");

        try {
            this.serviceUsuario.salvarUsuario(usuario);
        } catch (Exception e) {
            Assert.fail("Erro ao salvar usuário: " + e.getMessage());
        }

        Usuario usuarioLogado = this.serviceUsuario.loginUser("login@dominio.com", "senhaErrada");

        Assert.assertNull(usuarioLogado);
    }

    @Test
    public void salvarUsuarioComErroCriptografia() {
        Usuario usuario = new Usuario();
        usuario.setEmail("erro@dominio.com");
        usuario.setSenha("senha123");

        // Simulando erro de criptografia
        try {
            this.serviceUsuario.salvarUsuario(usuario);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CriptoExistsException);
            Assert.assertEquals("Error na criptografia da senha", e.getMessage());
        }
    }
}