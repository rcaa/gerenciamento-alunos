package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuarioValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("rafaela@gmail.com");
        usuario.setUser("rafaela");
        usuario.setSenha("rafaela123");

        assertDoesNotThrow(() -> {
            serviceUsuario.salvarUsuario(usuario);
        });

        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setEmail("rafaela@gmail.com");
        usuarioDuplicado.setUser("rafaela2");
        usuarioDuplicado.setSenha("senha123");

        Assert.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuarioDuplicado);
        });
    }

    @Test
    public void salvarUsuarioComEmailExistente() throws Exception {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("rafaela@gmail.com");
        usuarioExistente.setUser("rafaela");
        usuarioExistente.setSenha("rafaela123");

        serviceUsuario.salvarUsuario(usuarioExistente);

        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setEmail("rafaela@gmail.com");
        usuarioDuplicado.setUser("rafinha");
        usuarioDuplicado.setSenha("senha456");

        Assert.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuarioDuplicado);
        });
    }

    @Test
    public void buscarUsuarioPorUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("rafaela@gmail.com");
        usuario.setUser("rafaela");
        usuario.setSenha("rafaela123");
    
        serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioBuscado = serviceUsuario.loginUser("rafaela", "rafaela123");

        Assert.assertNotNull(usuarioBuscado);
        Assert.assertEquals("rafaela", usuarioBuscado.getUser());
    }

    @Test
    public void salvarUsuarioSemNomeDeUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("rafaela@gmail.com");
        usuario.setUser(null);
        usuario.setSenha("rafaela123");
    
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });
    }
}
