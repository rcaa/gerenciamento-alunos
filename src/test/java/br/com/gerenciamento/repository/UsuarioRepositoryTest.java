package br.com.gerenciamento.repository;

import org.junit.Assert;
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
    public void deveriaRetornarUsuarioPorEmail() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("john@doe.com");
        usuario.setSenha("123456");
        usuarioRepository.save(usuario);

        // Act
        Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());

        // Assert
        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(usuario.getEmail(), usuarioEncontrado.getEmail());
    }

    @Test
    public void deveriaRetornarNullQuandoNaoEncontrarUsuarioPorEmail() {
        // Act
        Usuario usuarioEncontrado = usuarioRepository.findByEmail("john@doe.com");

        // Assert
        Assert.assertNull(usuarioEncontrado);
    }

    @Test
    public void deveriaRetornarUsuarioPorEmailESenha() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("john@doe.com");
        usuario.setSenha("123456");
        usuarioRepository.save(usuario);

        // Act
        Usuario usuarioEncontrado = usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());

        // Assert
        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(usuario.getEmail(), usuarioEncontrado.getEmail());
        Assert.assertEquals(usuario.getSenha(), usuarioEncontrado.getSenha());
    }
}
