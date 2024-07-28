package br.com.gerenciamento.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gerenciamento.model.Usuario;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByEmail() {
        // Setup data for testing
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setUser("testUser");
        usuario.setSenha("password");
        usuarioRepository.save(usuario);

        Usuario foundUsuario = usuarioRepository.findByEmail("test@test.com");
        assertNotNull(foundUsuario);
    }

    @Test
    public void testBuscarLogin() {
        // Setup data for testing
        Usuario usuario = new Usuario();
        usuario.setUser("testUser");
        usuario.setSenha("password");
        usuarioRepository.save(usuario);

        Usuario foundUsuario = usuarioRepository.buscarLogin("testUser", "password");
        assertNotNull(foundUsuario);
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();
        usuario.setEmail("novo@test.com");
        usuario.setUser("novoUser");
        usuario.setSenha("newPassword");

        Usuario savedUsuario = usuarioRepository.save(usuario);
        assertNotNull(savedUsuario.getId());
    }

    @Test
    public void testDelete() {
        Usuario usuario = new Usuario();
        usuario.setEmail("delete@test.com");
        usuario.setUser("deleteUser");
        usuario.setSenha("deletePassword");
        usuarioRepository.save(usuario);

        usuarioRepository.delete(usuario);
        Usuario foundUsuario = usuarioRepository.findByEmail("delete@test.com");
        assertNull(foundUsuario);
    }
}