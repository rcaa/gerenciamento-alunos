package br.com.gerenciamento.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testSalvar() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("vinicius@example.com");
        usuario.setUser("vinicius");
        usuario.setSenha("senha123");

        Usuario usuarioNovo = usuarioRepository.save(usuario);
        assertNotNull(usuarioNovo.getId());
        assertEquals("vinicius@example.com", usuarioNovo.getEmail());
        assertEquals("vinicius", usuarioNovo.getUser());
    }

    @Test
    public void testFindById() {
    
        Usuario usuario = new Usuario();
        usuario.setId(7L);
        usuario.setEmail("mario@example2.com");
        usuario.setUser("marioo");
        usuario.setSenha("senha4567");
        Usuario novoUsuario = usuarioRepository.save(usuario);

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(novoUsuario.getId());
        assertTrue(usuarioOptional.isPresent());

        Usuario usuarioRetornado = usuarioOptional.get();
        
        assertEquals("mario@example2.com", usuarioRetornado.getEmail());
        assertEquals("marioo", usuarioRetornado.getUser());
    }   

    @Test
    public void testLoginValido() {

        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example4.com");
        usuario.setUser("teste4");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        Usuario usuarioRetornado = usuarioRepository.buscarLogin("teste4", "senha123");
        assertNotNull(usuarioRetornado);
        assertEquals("teste4", usuarioRetornado.getUser());
        assertEquals("teste@example4.com", usuarioRetornado.getEmail());
    }

    @Test
    public void testSenhaInvalida() {
        
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example5.com");
        usuario.setUser("teste5");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        Usuario usuarioRetornado = usuarioRepository.buscarLogin("teste5", "senhaErrada");
        assertNull(usuarioRetornado);
    }


}
