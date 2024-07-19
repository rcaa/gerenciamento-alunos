package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioTeste;

    @Before
    public void setUp() {
        usuarioTeste = new Usuario();
        usuarioTeste.setEmail("email@email.com");
        usuarioTeste.setUser("user");
        usuarioTeste.setSenha("senha");
        usuarioRepository.save(usuarioTeste);
    }

    @After
    public void tearDown() {
        usuarioRepository.delete(usuarioTeste);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void findByEmail() {
        Usuario usuario = usuarioRepository.findByEmail("email@email.com");
        Assert.assertNotNull(usuario);
        Assert.assertEquals("email@email.com", usuario.getEmail());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void buscarLogin() {
        Usuario usuario = usuarioRepository.buscarLogin("user", "senha");
        Assert.assertNotNull(usuario);
        Assert.assertEquals("user", usuario.getUser());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void findByEmailNull() {
        Usuario usuario = usuarioRepository.findByEmail(null);
        Assert.assertNull(usuario);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void buscarLoginNull() {
        Usuario usuario = usuarioRepository.buscarLogin(null, null);
        Assert.assertNull(usuario);
    }
}
