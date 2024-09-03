package br.com.gerenciamento.service;

import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("UsuarioTeste");
        usuario.setSenha("senha");
        usuario.setEmail("emailTeste@teste.com");
        serviceUsuario.salvarUsuario(usuario);
        org.assertj.core.api.Assertions.assertThat(usuario.getUser()).isEqualTo("UsuarioTeste");
    }

    @Test
    public void salvarUsuarioEmailDuplicado() throws Exception {
        Usuario usuarioTeste1 = new Usuario();
        usuarioTeste1.setUser("UsuarioDuplicado");
        usuarioTeste1.setSenha("senha415");
        usuarioTeste1.setEmail("emailduplicado@email.com");
        serviceUsuario.salvarUsuario(usuarioTeste1);

        Usuario usuarioTeste2 = new Usuario();
        usuarioTeste2.setUser("UsuarioDuplicado2");
        usuarioTeste2.setSenha("senha213");
        usuarioTeste2.setEmail("emailduplicado@email.com");

        Assertions.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuarioTeste2);
        });
    }

    @Test
    public void salvarUsuarioNomeInvalido(){
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setUser("U");
        usuario.setSenha("senha");
        usuario.setEmail("usuarioteste@email.com");
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
        }
    
    @Test
    public void loginCredenciaisErradas() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("UsuarioTeste");
        usuario.setSenha("senha");
        usuario.setEmail("emailteste@teste.com");
        serviceUsuario.salvarUsuario(usuario);
        
        Usuario usuarioLogado = serviceUsuario.loginUser("UsuarioTeste", "senhaTeste");
        assertNull(usuarioLogado);
    }
    }