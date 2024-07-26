package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void testFindByEmail() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario1");
        usuario.setEmail("usuario1@example.com");
        usuario.setSenha(Util.md5("senha1"));
        usuarioRepository.save(usuario);

        Usuario foundUser = usuarioRepository.findByEmail("usuario1@example.com");
        Assert.assertNotNull(foundUser);
        Assert.assertEquals("usuario1", foundUser.getUser());
    }

    @Test
    @Transactional
    public void testBuscarLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario2");
        usuario.setEmail("usuario2@example.com");
        usuario.setSenha(Util.md5("senha2"));
        usuarioRepository.save(usuario);

        Usuario foundUser = usuarioRepository.buscarLogin("usuario2", Util.md5("senha2"));
        Assert.assertNotNull(foundUser);
        Assert.assertEquals("usuario2", foundUser.getUser());
    }

    @Test
    @Transactional
    public void testFindByEmailNotFound() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario3");
        usuario.setEmail("usuario3@example.com");
        usuario.setSenha(Util.md5("senha3"));
        usuarioRepository.save(usuario);

        Usuario foundUser = usuarioRepository.findByEmail("naoexiste@example.com");
        Assert.assertNull(foundUser);
    }

    @Test
    @Transactional
    public void testBuscarLoginInvalido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario4");
        usuario.setEmail("usuario4@example.com");
        usuario.setSenha(Util.md5("senha4"));
        usuarioRepository.save(usuario);

        Usuario foundUser = usuarioRepository.buscarLogin("usuario4", Util.md5("senhaInvalida"));
        Assert.assertNull(foundUser);
    }
}
