package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setUser("testuser");
        usuario.setSenha("password");
        usuarioRepository.save(usuario);
    }

    @Test
    public void findByEmail() {
        Usuario foundUsuario = usuarioRepository.findByEmail("test@example.com");
        assertNotNull(foundUsuario);
        assertEquals("testuser", foundUsuario.getUser());
    }

    @Test
    public void buscarLogin() {
        Usuario foundUsuario = usuarioRepository.buscarLogin("testuser", "password");
        assertNotNull(foundUsuario);
        assertEquals("test@example.com", foundUsuario.getEmail());
    }

    @Test
    public void findByEmailNotFound() {
        Usuario foundUsuario = usuarioRepository.findByEmail("nonexistent@example.com");
        assertNull(foundUsuario);
    }

    @Test
    public void buscarLoginInvalidCredentials() {
        Usuario foundUsuario = usuarioRepository.buscarLogin("testuser", "wrongpassword");
        assertNull(foundUsuario);

        foundUsuario = usuarioRepository.buscarLogin("wronguser", "password");
        assertNull(foundUsuario);
    }
}
