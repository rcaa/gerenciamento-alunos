package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setUser("testuser");
        usuario.setSenha("password");

        Usuario savedUsuario = usuarioRepository.save(usuario);

        assertNotNull(savedUsuario.getId());
        assertEquals("test@example.com", savedUsuario.getEmail());
    }

    @Test
    public void testFindByEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("find@example.com");
        usuario.setUser("finduser");
        usuario.setSenha("password");

        usuarioRepository.save(usuario);

        Usuario foundUsuario = usuarioRepository.findByEmail("find@example.com");

        assertNotNull(foundUsuario);
        assertEquals("finduser", foundUsuario.getUser());
    }

    @Test
    public void testUpdateUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("update@example.com");
        usuario.setUser("updateuser");
        usuario.setSenha("password");

        Usuario savedUsuario = usuarioRepository.save(usuario);

        savedUsuario.setUser("updateduser");
        Usuario updatedUsuario = usuarioRepository.save(savedUsuario);

        assertEquals("updateduser", updatedUsuario.getUser());
    }

    @Test
    public void testDeleteUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("delete@example.com");
        usuario.setUser("deleteuser");
        usuario.setSenha("password");

        Usuario savedUsuario = usuarioRepository.save(usuario);

        usuarioRepository.deleteById(savedUsuario.getId());
        Optional<Usuario> deletedUsuario = usuarioRepository.findById(savedUsuario.getId());

        assertTrue(deletedUsuario.isEmpty());
    }
}
