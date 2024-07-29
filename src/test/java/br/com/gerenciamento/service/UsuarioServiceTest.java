package br.com.gerenciamento.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.exception.UsuarioNotFoundException;
import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    // teste 1 - salvar usuario
    @Test
    public void salvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@gmail.com");
        usuario.setSenha("123456");
        usuario.setUser("usuario");
        assertDoesNotThrow(() -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    // teste 2 - criar usuario invalido (quantidade de caracteres maior ou menor que o permitido)
    @Test
    public void salvarUserInvalido () throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@gmail.com");
        usuario.setSenha("123456");
        usuario.setUser("us");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario); 
        });
    }

    // teste 3 - salvar um email que ja existe
    @Test
    public void salvarEmailExistente () throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setEmail("usuarioexist@gmail.com");
        usuario1.setSenha("123456");
        usuario1.setUser("usuario");

        this.serviceUsuario.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("usuarioexist@gmail.com");
        usuario2.setSenha("654321");
        usuario2.setUser("usuario2");

        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });
    }

    // teste 4 - salvar sem usuario
    @Test
    public void salvarSemUsuario () throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("gustavo@gmail.com");
        usuario.setSenha("112233");
        
        Assert.assertThrows(UsuarioNotFoundException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario); 
        });
    }

    
}
