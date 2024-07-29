package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    private ServiceUsuario serviceUsuario;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Alice");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetorno = this.usuarioRepository.findByEmail("teste@email.com");
        Assert.assertTrue(usuarioRetorno.getUser().equals("Alice"));
    }

    @Test
    @Transactional
    public void loginInválido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Alice");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetorno = this.serviceUsuario.loginUser("alice", Util.md5("1234"));
        Assert.assertNull(usuarioRetorno);
    }

    @Test
    @Transactional
    public void loginVálido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Alice");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetorno = this.serviceUsuario.loginUser("Alice", Util.md5("12345678"));
        Assert.assertNotNull(usuarioRetorno); 
       
    }

    @Test
    @Transactional
    public void EmailExistsException() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setEmail("teste@email.com");
        usuario1.setSenha("12345678");
        usuario1.setUser("Alice");
        this.serviceUsuario.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("teste@email.com");
        usuario2.setSenha("123");
        usuario2.setUser("Daniel");
       
        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);});
    }
}
