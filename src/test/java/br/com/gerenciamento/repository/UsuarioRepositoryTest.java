package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void procurarEmailInexistente() {

        Usuario email = usuarioRepository.findByEmail("emailErrado@gmail.com");
        Assert.assertNull(email);
    }

    @Test
    public void atualizarEmail() {
        
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");
        usuario.setUser("fulano");
        usuario.setSenha("12345");
        Usuario usuarioReturn = usuarioRepository.save(usuario);

        usuarioReturn.setEmail("emailAtualizado@example.com");
        usuarioRepository.save(usuarioReturn);

        Usuario updatedUser = usuarioRepository.findByEmail("emailAtualizado@example.com");
        assertNotNull(updatedUser);
        assertEquals("emailAtualizado@example.com", updatedUser.getEmail());
    }

    @Test
    public void buscarLogin(){
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");
        usuario.setUser("fulano");
        usuario.setSenha("12345");
        usuarioRepository.save(usuario);

        Usuario usuarioReturn = usuarioRepository.buscarLogin(usuario.getUser(), usuario.getSenha());
        Assert.assertNotNull(usuarioReturn);
        Assert.assertEquals("fulano", usuarioReturn.getUser());
    }

    @Test
    public void credenciaisInvalidas(){
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");
        usuario.setUser("fulano");
        usuario.setSenha("12345");
        usuarioRepository.save(usuario);

        Usuario usuarioReturn = usuarioRepository.buscarLogin(usuario.getUser(), "123");
        Assert.assertNull(usuarioReturn);
    }

}
