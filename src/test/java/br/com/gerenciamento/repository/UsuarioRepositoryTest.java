package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("testeuser");
        usuario.setSenha("123456");
        usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("teste@exemplo.com");
        assertNotNull(usuarioEncontrado);
        assertEquals("teste@exemplo.com", usuarioEncontrado.getEmail());
    }

    @Test
    public void testBuscarLogin() {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@exemplo.com");
        usuario.setUser("loginuser");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("loginuser", "senha123");
        assertNotNull(usuarioEncontrado);
        assertEquals("loginuser", usuarioEncontrado.getUser());
        assertEquals("senha123", usuarioEncontrado.getSenha());
    }

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("novo@exemplo.com");
        usuario.setUser("novouser");
        usuario.setSenha("senha789");

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        assertNotNull(usuarioSalvo.getId());

        Usuario usuarioBuscado = usuarioRepository.findById(usuarioSalvo.getId()).orElse(null);
        assertNotNull(usuarioBuscado);
        assertEquals("novo@exemplo.com", usuarioBuscado.getEmail());
        assertEquals("novouser", usuarioBuscado.getUser());
        assertEquals("senha789", usuarioBuscado.getSenha());
    }

    @Test
    public void testFindByEmailNotFound() {
        Usuario usuarioEncontrado = usuarioRepository.findByEmail("naoexiste@exemplo.com");
        assertNull(usuarioEncontrado);
    }
}
