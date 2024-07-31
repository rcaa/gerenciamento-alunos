package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void buscarPorEmail() {

        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setUser("usuario");
        usuario.setSenha("senha");
        usuarioRepository.save(usuario);


        Usuario usuarioEncontrado = usuarioRepository.findByEmail("test@example.com");

        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals("test@example.com", usuarioEncontrado.getEmail());
    }

    @Test
    public void buscarLoginComCredenciaisValidas() {

        Usuario usuario = new Usuario();
        usuario.setEmail("login@example.com");
        usuario.setUser("loginUser");
        usuario.setSenha("password123");
        usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("loginUser", "password123");

        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals("loginUser", usuarioEncontrado.getUser());
        Assertions.assertEquals("password123", usuarioEncontrado.getSenha());
    }

    @Test
    public void bscarLoginComCredenciaisInvalidas() {

        Usuario usuario = new Usuario();
        usuario.setEmail("loginFail@example.com");
        usuario.setUser("failUser");
        usuario.setSenha("failPassword");
        usuarioRepository.save(usuario);


        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("failUser", "wrongPassword");


        Assertions.assertNull(usuarioEncontrado);
    }

    @Test
    public void buscarPorEmailInexistente() {

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("nonexistent@example.com");


        Assertions.assertNull(usuarioEncontrado);
    }

    @Test
    public void salvarNovoUsuario() {

        Usuario usuario = new Usuario();
        usuario.setEmail("novo@example.com");
        usuario.setUser("novoUsuario");
        usuario.setSenha("novaSenha");


        Usuario usuarioSalvo = usuarioRepository.save(usuario);


        Assertions.assertNotNull(usuarioSalvo.getId());
        Assertions.assertEquals("novo@example.com", usuarioSalvo.getEmail());
        Assertions.assertEquals("novoUsuario", usuarioSalvo.getUser());
    }

    @Test
    public void atualizarSenhaUsuario() {

        Usuario usuario = new Usuario();
        usuario.setEmail("update@example.com");
        usuario.setUser("updateUser");
        usuario.setSenha("oldPassword");
        usuarioRepository.save(usuario);


        usuario.setSenha("newPassword");
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);


        Assertions.assertEquals("newPassword", usuarioAtualizado.getSenha());
    }

}
