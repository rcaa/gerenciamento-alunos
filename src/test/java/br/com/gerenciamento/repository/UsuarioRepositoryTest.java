package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Before
    public void setUp() {
        usuarioRepository.deleteAll();

        Usuario usuario1 = new Usuario();
        usuario1.setEmail("user1@dominio.com");
        usuario1.setUser("usuario1");
        usuario1.setSenha("senha1");
        usuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("user2@dominio.com");
        usuario2.setUser("usuario2");
        usuario2.setSenha("senha2");
        usuarioRepository.save(usuario2);
    }

    @Test
    public void testFindByEmail() {
        Usuario usuario = usuarioRepository.findByEmail("user1@dominio.com");
        assertNotNull(usuario);
        assertEquals("usuario1", usuario.getUser());
        assertEquals("senha1", usuario.getSenha());
    }

    @Test
    public void testFindByEmailNotFound() {
        Usuario usuario = usuarioRepository.findByEmail("naoexiste@dominio.com");
        assertNull(usuario);
    }

    @Test
    public void testBuscarLogin() {
        Usuario usuario = usuarioRepository.buscarLogin("usuario2", "senha2");
        assertNotNull(usuario);
        assertEquals("user2@dominio.com", usuario.getEmail());
    }

    @Test
    public void testBuscarLoginComSenhaIncorreta() {
        Usuario usuario = usuarioRepository.buscarLogin("usuario2", "senhaIncorreta");
        assertNull(usuario);
    }
}
