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
        usuario.setEmail("barbie@email.com");
        usuario.setUser("BarbieMillicent");
        usuario.setSenha("011960");

        Usuario savedUsuario = usuarioRepository.save(usuario);

        assertNotNull(savedUsuario.getId());
        assertEquals("barbie@email.com", savedUsuario.getEmail());
    }

    @Test
    public void testFindByEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("barbara@email.com");
        usuario.setUser("BarbaraRoberts");
        usuario.setSenha("069101");

        usuarioRepository.save(usuario);

        Usuario foundUsuario = usuarioRepository.findByEmail("barbara@email.com");

        assertNotNull(foundUsuario);
        assertEquals("BarbaraRoberts", foundUsuario.getUser());
    }

    @Test
    public void testUpdateUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("serafina@email.com");
        usuario.setUser("SerafinaIan");
        usuario.setSenha("082128");

        Usuario savedUsuario = usuarioRepository.save(usuario);

        savedUsuario.setUser("SerafinaIan");
        Usuario updatedUsuario = usuarioRepository.save(savedUsuario);

        assertEquals("SerafinaIan", updatedUsuario.getUser());
    }

    @Test
    public void testDeleteUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("ursula@email.com");
        usuario.setUser("UrsulaBruxa");
        usuario.setSenha("666999");

        Usuario savedUsuario = usuarioRepository.save(usuario);

        usuarioRepository.deleteById(savedUsuario.getId());
        Optional<Usuario> deletedUsuario = usuarioRepository.findById(savedUsuario.getId());

        assertTrue(deletedUsuario.isEmpty());
    }
}
