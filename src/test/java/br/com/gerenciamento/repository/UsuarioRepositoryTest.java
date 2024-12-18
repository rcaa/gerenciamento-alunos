package br.com.gerenciamento.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    public void emailFound() {

        usuarioRepository.deleteAll();

        Usuario user = new Usuario();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setUser("user"); // Nome de usuário muito curto
        user.setSenha("password");

        usuarioRepository.save(user);

        Usuario foundUser = usuarioRepository.findByEmail("user@gmail.com");

        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void emailNotFound() {

        usuarioRepository.deleteAll();

        assertNull(usuarioRepository.findByEmail("absentEmail@gmail.com"));
    }

    @Test
    public void loginFound() {

        usuarioRepository.deleteAll();

        Usuario user = new Usuario();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setUser("user"); // Nome de usuário muito curto
        user.setSenha("password");

        usuarioRepository.save(user);

        Usuario foundUser = usuarioRepository.buscarLogin(user.getUser(), user.getSenha());

        assertEquals(user.getUser(), foundUser.getUser());
        assertEquals(user.getSenha(), foundUser.getSenha());

    }

    @Test
    public void loginNotFound() {

        usuarioRepository.deleteAll();

        assertNull(usuarioRepository.buscarLogin("absentUser", "absentPassword"));

    }
}
