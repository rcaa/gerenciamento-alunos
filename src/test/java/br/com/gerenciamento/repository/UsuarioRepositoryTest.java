package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

import static org.junit.Assert.assertNull;

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
    public void testeBuscarLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario");
        usuario.setEmail("usuario@example.com");
        usuario.setSenha(Util.md5("senha"));
        usuarioRepository.save(usuario);

        Usuario foundUser = usuarioRepository.buscarLogin("usuario", Util.md5("senha"));
        Assert.assertNotNull(foundUser);
        Assert.assertEquals("usuario", foundUser.getUser());
    }

    @Test
    public void testeDeletaUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("delete@example.com");
        usuario.setUser("deleteUser");
        usuario.setSenha("deletePassword");
        usuarioRepository.save(usuario);

        usuarioRepository.delete(usuario);
        Usuario foundUsuario = usuarioRepository.findByEmail("delete@example.com");
        assertNull(foundUsuario);
    }
        
    @Test
    public void testFindByEmail() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario");
        usuario.setEmail("usuario1@example.com");
        usuario.setSenha(Util.md5("senha"));
        usuarioRepository.save(usuario);
    
        Usuario foundUser = usuarioRepository.findByEmail("usuario1@example.com");
        Assert.assertNotNull(foundUser);
        Assert.assertEquals("usuario", foundUser.getUser());
    }
    
    @Test
public void testUpdateUser() throws Exception {

    Usuario usuario = new Usuario();
    usuario.setUser("usuarioOriginal");
    usuario.setEmail("usuarioOriginal@example.com");
    usuario.setSenha(Util.md5("senhaOriginal"));
    usuarioRepository.save(usuario);

    usuario.setUser("usuarioAtualizado");
    usuario.setSenha(Util.md5("senhaAtualizada"));
    usuarioRepository.save(usuario);

    Usuario foundUser = usuarioRepository.findByEmail("usuarioOriginal@example.com");
    
    Assert.assertNotNull(foundUser);
    Assert.assertEquals("usuarioAtualizado", foundUser.getUser());
    Assert.assertEquals(Util.md5("senhaAtualizada"), foundUser.getSenha());
}

    
}
