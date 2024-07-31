package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

     @Autowired
        private UsuarioRepository usuarioRepository;
    
    @Test
    public void testeDeletarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario5");
        usuario.setEmail("usuario5@example.com");
        usuario.setSenha(Util.md5("senha5"));
        usuarioRepository.save(usuario);

        usuarioRepository.delete(usuario);

        Usuario foundUser = usuarioRepository.buscarLogin("usuario5", Util.md5("senha5"));
        Assert.assertNull(foundUser);
}
    @Test
    public void testeBuscarEmail() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario2");
        usuario.setEmail("usuario2@example.com");
        usuario.setSenha(Util.md5("senha2"));
        usuarioRepository.save(usuario);

        Usuario foundUser = usuarioRepository.findByEmail("usuario2@example.com");
        Assert.assertNotNull(foundUser);
        Assert.assertEquals("usuario2", foundUser.getUser());
        }
    @Test
    public void testeLoginIncorreto() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario3");
        usuario.setEmail("usuario3@example.com");
        usuario.setSenha(Util.md5("senha3"));
        usuarioRepository.save(usuario);
    
        Usuario foundUser = usuarioRepository.buscarLogin("usuario3", Util.md5("senhaIncorreta"));
        Assert.assertNull(foundUser);
    }
    @Test
    public void testeAtualizarSenha() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario4");
        usuario.setEmail("usuario4@example.com");
        usuario.setSenha(Util.md5("senha4"));
        usuarioRepository.save(usuario);
    
        usuario.setSenha(Util.md5("novaSenha4"));
        usuarioRepository.save(usuario);
    
        Usuario foundUser = usuarioRepository.buscarLogin("usuario4", Util.md5("novaSenha4"));
        Assert.assertNotNull(foundUser);
        Assert.assertEquals("usuario4", foundUser.getUser());
    }
    
}