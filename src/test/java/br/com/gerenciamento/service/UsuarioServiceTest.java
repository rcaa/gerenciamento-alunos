package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Teste para verificar se o método salvarUsuario salva um usuário corretamente.
     */
    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@email.com");
        usuario.setUser("testUser");
        usuario.setSenha("password");

        serviceUsuario.salvarUsuario(usuario);

        Usuario savedUsuario = usuarioRepository.findByEmail("test@email.com");
        assertNotNull(savedUsuario);
        assertEquals("test@email.com", savedUsuario.getEmail());
    }

    /**
     * Teste para verificar se o método salvarUsuario lança uma exceção quando o email já está cadastrado.
     */
    @Test
    public void verificarEmailUnico() {
        Usuario usuario = new Usuario();
        usuario.setEmail("user@email.com");
        usuario.setSenha("password");

        usuarioRepository.save(usuario);

        Usuario newUsuario = new Usuario();
        newUsuario.setEmail("user@email.com");
        newUsuario.setSenha("newpassword");

        assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(newUsuario);
        });
    }


    /**
     * Teste para verificar se o método loginUser retorna um usuário corretamente.
     */
    @Test
    public void loginSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("user");
        usuario.setEmail("user@email.com");
        usuario.setSenha(Util.md5("password"));

        usuarioRepository.save(usuario);

        Usuario result = serviceUsuario.loginUser("user", Util.md5("password"));

        assertNotNull(result);
        assertEquals("user", result.getUser());
    }

    /**
     * Teste para verificar se o método loginUser retorna null quando o usuário não existe.
     */
    @Test
    public void loginUsuarioInexistente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@email.com");
        usuario.setSenha(Util.md5("password"));

        usuarioRepository.save(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser("login@email.com", Util.md5("senhaIncorreta"));

        assertNull(usuarioLogado);
    }
}