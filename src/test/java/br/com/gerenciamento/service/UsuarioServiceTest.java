package br.com.gerenciamento.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    
    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    @Transactional
    public void salvarUsuario()  {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("usuario");
        usuario.setSenha("123456");
        usuario.setEmail("nome@hotmail.com");
        assertDoesNotThrow(() -> this.serviceUsuario.salvarUsuario(usuario));        
    }

    @Test
    @Transactional
    public void salvarEmailMalFormatado() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("usuario");
        usuario.setSenha("123456");
        usuario.setEmail("igor.com");
        Assert.assertThrows(ConstraintViolationException.class, () -> this.serviceUsuario.salvarUsuario(usuario));
    }

    @Test
    @Transactional
    public void salvarEmailJaCadastrado() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("usuario");
        usuario.setSenha("123456");
        usuario.setEmail("EmailTeste@hotmail.com");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setUser("usuario2");
        usuario2.setSenha("12345678");
        usuario2.setEmail("EmailTeste@hotmail.com");
        Assert.assertThrows(EmailExistsException.class, () -> this.serviceUsuario.salvarUsuario(usuario2));
    }

    @Test
    @Transactional
    public void fazerLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("usuario");
        usuario.setSenha("123456");
        usuario.setEmail("usuario@hotmail.com");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario login = this.serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        Assert.assertEquals("usuario", login.getUser());
    }
}
