package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.AssertTrue;
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

    @Test
    public void salvarUsuarioComUserForaDosLimites() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setUser("12");
        usuario.setSenha("senha");

        Assert.assertThrows(ConstraintViolationException.class,
                () -> this.serviceUsuario.salvarUsuario(usuario));

        usuario.setUser("1234567890123456789012");

        Assert.assertThrows(ConstraintViolationException.class,
                () -> this.serviceUsuario.salvarUsuario(usuario));

    }

    @Test
    public void salvarUsuarioComEmailInvalido() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste");
        usuario.setUser("usuario");
        usuario.setSenha("senha");

        Assert.assertThrows(ConstraintViolationException.class,
                () -> this.serviceUsuario.salvarUsuario(usuario));

        usuario.setEmail("teste.com");

        Assert.assertThrows(ConstraintViolationException.class,
                () -> this.serviceUsuario.salvarUsuario(usuario));
    }

    @Test
    public void loginUserComUsuarioInvalido() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setUser("usuario");
        usuario.setSenha("senha");

        Assert.assertNull(this.serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha()));

    }

    @Test
    public void loginUserComUsuarioValido() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setUser("usuario");
        usuario.setSenha("senha");
        this.serviceUsuario.salvarUsuario(usuario);

        Assert.assertEquals(this.serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha()).getId(),
                                                                                        usuario.getId());
    }

    @Test
    public void loginUserComUserExistenteSenhaInexistente() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setUser("usuario");
        usuario.setSenha("senha");
        this.serviceUsuario.salvarUsuario(usuario);

        Assert.assertNull(this.serviceUsuario.loginUser(usuario.getUser(), "naoEhSenha"));

    }

    @Test
    public void loginUserComUserInexistenteSenhaExistente() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setUser("usuario");
        usuario.setSenha("senha");
        this.serviceUsuario.salvarUsuario(usuario);

        Assert.assertNull(this.serviceUsuario.loginUser("naoEhUser", usuario.getSenha()));

    }

}
