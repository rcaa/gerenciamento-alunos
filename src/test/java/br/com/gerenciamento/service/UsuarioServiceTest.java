package br.com.gerenciamento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
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
        usuario.setUser("TestUser");
        usuario.setEmail("test@test.com");
        usuario.setSenha("senha");
        serviceUsuario.salvarUsuario(usuario);

        Optional<Usuario> usuarioSalvo = Optional.ofNullable(serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha()));
        assertTrue(usuarioSalvo.isPresent());
        assertEquals(usuario.getUser(), usuarioSalvo.get().getUser());
    }

    @Test
    public void loginUserDeveRetornarUsuarioExistente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("TestUser");
        usuario.setEmail("test@test.com");
        usuario.setSenha("senha");
        serviceUsuario.salvarUsuario(usuario);

        Optional<Usuario> usuarioLogado = Optional.ofNullable(serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha()));

        assertTrue(usuarioLogado.isPresent());
        assertEquals(usuario.getUser(), usuarioLogado.get().getUser());
    }

   @Test(expected = EmailExistsException.class)
    public void salvarUsuarioComEmailJaExistenteDeveFalhar() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setUser("TestUser");
        usuario1.setEmail("test@test.com");
        usuario1.setSenha("senha");
        serviceUsuario.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setUser("OutroUsuario");
        usuario2.setEmail("test@test.com");
        usuario2.setSenha("senha");
        serviceUsuario.salvarUsuario(usuario2);
    }

    @Test
    public void salvarUsuarioComEmailDuplicado() throws Exception {
        Usuario usuarioExemplo = new Usuario();
        usuarioExemplo.setUser("UsuarioExemplo");
        usuarioExemplo.setEmail("exemplo@test.com");
        usuarioExemplo.setSenha("senhaExemplo");
        serviceUsuario.salvarUsuario(usuarioExemplo);

        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setUser("UsuarioDuplicado");
        usuarioDuplicado.setEmail("exemplo@test.com");
        usuarioDuplicado.setSenha("senhaDuplicado");

        assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuarioDuplicado);
        });
    }

}
