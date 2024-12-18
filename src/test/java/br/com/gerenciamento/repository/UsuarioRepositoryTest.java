package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
    
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    private ServiceUsuario serviceUsuario;
    
    
    @Test
    public void buscarUsuarioPorEmail() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("João");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("123456");
        
        serviceUsuario.salvarUsuario(usuario);        
        
        Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());        
        Assert.assertNotNull(usuarioEncontrado.getId());
    }
    
    @Test
    public void buscarUsuarioPorLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("João");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("123456");
        
        serviceUsuario.salvarUsuario(usuario);        
        
        Usuario usuarioEncontrado = usuarioRepository.buscarLogin(usuario.getUser(),usuario.getSenha());        
        Assert.assertNotNull(usuarioEncontrado.getId());
    }
    
       @Test
    public void buscarUsuarioPorEmailNaoRegistrado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("João");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("123456");
        
        serviceUsuario.salvarUsuario(usuario);        
        
        Usuario usuarioEncontrado = usuarioRepository.findByEmail("pedro@example.com");        
        Assert.assertNotNull(usuarioEncontrado.getId());
    }
    
    @Test
    public void buscarUsuarioPorLoginNaoRegistrado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("João");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("123456");
        
        serviceUsuario.salvarUsuario(usuario);        
        
        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("pedro",usuario.getSenha());        
        Assert.assertNotNull(usuarioEncontrado.getId());
    }
    
}
