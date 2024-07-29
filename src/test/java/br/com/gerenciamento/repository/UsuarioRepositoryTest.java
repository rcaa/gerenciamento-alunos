package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario1;
    private Usuario usuario2;

    @Before
    public void setUp() {
        usuario1 = new Usuario();
        usuario1.setEmail("test1@example.com");
        usuario1.setUser("user1");
        usuario1.setSenha("password1");

        usuario2 = new Usuario();
        usuario2.setEmail("test2@example.com");
        usuario2.setUser("user2");
        usuario2.setSenha("password2");

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
    }

    @Test
    @Transactional
    public void testFindByEmail() {
        Usuario foundUser = usuarioRepository.findByEmail("test1@example.com");
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUser());
    }

    @Test
    @Transactional
    public void testFindByEmailNotFound() {
        Usuario foundUser = usuarioRepository.findByEmail("nonexistent@example.com");
        assertNull(foundUser);
    }

    @Test
    @Transactional
    public void testBuscarLogin() {
        Usuario foundUser = usuarioRepository.buscarLogin("user2", "password2");
        assertNotNull(foundUser);
        assertEquals("test2@example.com", foundUser.getEmail());
    }

}
