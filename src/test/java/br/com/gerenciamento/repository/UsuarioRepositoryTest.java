package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUser("TestUser");
        usuario.setEmail("testuser@example.com");
        usuario.setSenha("password123");
        usuarioRepository.save(usuario);

        Optional<Usuario> usuarioRetornado = usuarioRepository.findById(usuario.getId());
        Assertions.assertTrue(usuarioRetornado.isPresent());
        Assertions.assertEquals("TestUser", usuarioRetornado.get().getUser());
    }

    @Test
    public void testFindUsuarioByEmail() {
        Usuario usuario = new Usuario();
        usuario.setUser("FindUser");
        usuario.setEmail("finduser@example.com");
        usuario.setSenha("password123");
        usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("finduser@example.com");
        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals("FindUser", usuarioEncontrado.getUser());
    }

    @Test
    public void testUpdateUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUser("UpdateUser");
        usuario.setEmail("updateuser@example.com");
        usuario.setSenha("password123");
        usuarioRepository.save(usuario);

        usuario.setUser("UpdatedUser");
        usuarioRepository.save(usuario);

        Optional<Usuario> usuarioAtualizado = usuarioRepository.findById(usuario.getId());
        Assertions.assertTrue(usuarioAtualizado.isPresent());
        Assertions.assertEquals("UpdatedUser", usuarioAtualizado.get().getUser());
    }

    @Test
    public void testDeleteUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUser("DeleteUser");
        usuario.setEmail("deleteuser@example.com");
        usuario.setSenha("password123");
        usuarioRepository.save(usuario);

        usuarioRepository.deleteById(usuario.getId());

        Optional<Usuario> usuarioDeletado = usuarioRepository.findById(usuario.getId());
        Assertions.assertFalse(usuarioDeletado.isPresent());
    }
}
