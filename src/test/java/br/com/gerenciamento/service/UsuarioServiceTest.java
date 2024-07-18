package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@email.com.br");
        usuario.setUser("teste");
        usuario.setSenha("12345");
        Usuario novoUsuario = this.serviceUsuario.salvarUsuario(usuario);
        Assert.assertTrue(novoUsuario.getUser().equals("teste"));
    }

    @Test
    public void loginUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste-login@email.com.br");
        usuario.setUser("teste-login");
        usuario.setSenha("12345");
        Usuario novoUsuario = this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = this.serviceUsuario.loginUser(novoUsuario.getUser(), novoUsuario.getSenha());

        Assert.assertTrue(usuarioLogado.getUser().equals("teste-login"));
    }

    @Test
    public void salvarSemEmail() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("teste-salvar-sem-email");
        usuario.setSenha("12345");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    @Test
    public void salvarSemId() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste-salvar-sem-id");
        usuario.setUser("teste-salvar-sem-id");
        usuario.setSenha("12345");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }
}
