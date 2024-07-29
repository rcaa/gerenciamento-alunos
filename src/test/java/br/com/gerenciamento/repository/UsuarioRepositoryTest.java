package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNome("Usuario1");
        usuarioRepository.save(usuario);
    }

    @Test
    void testFindById() {
        Optional<Usuario> found = usuarioRepository.findById(usuario.getId());
        assertTrue(found.isPresent());
        assertEquals(usuario.getId(), found.get().getId());
    }

    @Test
    void testSave() {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Usuario2");
        Usuario savedUsuario = usuarioRepository.save(novoUsuario);
        assertNotNull(savedUsuario);
        assertEquals("Usuario2", savedUsuario.getNome());
    }

    @Test
    void testDelete() {
        usuarioRepository.delete(usuario);
        Optional<Usuario> deletedUsuario = usuarioRepository.findById(usuario.getId());
        assertFalse(deletedUsuario.isPresent());
    }

    @Test
    void testFindAll() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        assertNotNull(usuarios);
        assertTrue(usuarios.iterator().hasNext());
    }
}
