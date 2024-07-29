package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private Usuario usuario = new Usuario();
    
    @Before
    public void setUp() {
        usuario.setId(2L);
        usuario.setUser("usuario");
        usuario.setSenha("123456");
        usuario.setEmail("teste@hotmail.com");
        this.usuarioRepository.save(usuario);
    }
    
    @After
    public void reset() {
        this.usuarioRepository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Usuario> usuarioEncontrado = this.usuarioRepository.findAll();
        Assert.assertTrue(usuarioEncontrado.stream().anyMatch(u -> u.getUser().equals(usuario.getUser()))); 
    }

    @Test
    public void findById() {
        Usuario usuarioEncontrado = this.usuarioRepository.findById(2L).orElse(null);
        Assert.assertNotNull(usuarioEncontrado); 
        Assert.assertEquals(usuario.getUser(), usuarioEncontrado.getUser()); 
    }

    @Test
    public void findbyEmail() {
        Usuario usuarioEncontrado = this.usuarioRepository.findByEmail("teste@hotmail.com");
        Assert.assertEquals(usuario.getUser(), usuarioEncontrado.getUser()); 
    }

    @Test
    public void buscarLogin() {
        Usuario usuarioEncontrado = this.usuarioRepository.buscarLogin("usuario", "123456");
        Assert.assertEquals(usuario.getUser(), usuarioEncontrado.getUser()); 
    }
}
