package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuarioRepository.deleteAll(); 

        usuario = new Usuario();
        usuario.setUser("testuser");
        usuario.setEmail("testuser@example.com");
        usuario.setSenha("password123"); 
        usuarioRepository.save(usuario);
    }

    @Test
    public void testFindByEmail() {
        Usuario foundUsuario = usuarioRepository.findByEmail("testuser@example.com");
        assertThat(foundUsuario).isNotNull();
        assertThat(foundUsuario.getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    public void testFindByEmailNotFound() {
        Usuario foundUsuario = usuarioRepository.findByEmail("nonexistent@example.com");
        assertThat(foundUsuario).isNull();
    }

    @Test
    public void testBuscarLogin() {
        Usuario foundUsuario = usuarioRepository.buscarLogin("testuser", "password123");
        assertThat(foundUsuario).isNotNull();
        assertThat(foundUsuario.getUser()).isEqualTo("testuser");
    }

    @Test
    public void testBuscarLoginIncorrectPassword() {
        Usuario foundUsuario = usuarioRepository.buscarLogin("testuser", "wrongpassword");
        assertThat(foundUsuario).isNull();
    }

    @Test
    public void testBuscarLoginUserNotFound() {
        Usuario foundUsuario = usuarioRepository.buscarLogin("nonexistentuser", "password123");
        assertThat(foundUsuario).isNull();
    }
}
