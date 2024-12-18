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
        usuario.setUser("isabelt");
        usuario.setEmail("isabel@test.com");
        usuario.setSenha("password"); 
        usuarioRepository.save(usuario);
    }

    @Test
    public void procurarPorEmail() {
        Usuario foundUsuario = usuarioRepository.findByEmail("isabel@test.com");
        assertThat(foundUsuario).isNotNull();
        assertThat(foundUsuario.getEmail()).isEqualTo("isabel@test.com");
    }

    @Test
    public void emailNoEncontrado() {
        Usuario foundUsuario = usuarioRepository.findByEmail("isabel@example.com");
        assertThat(foundUsuario).isNull();
    }

    @Test
    public void encontrarUser() {
        Usuario foundUsuario = usuarioRepository.buscarLogin("isabelt", "password");
        assertThat(foundUsuario).isNotNull();
        assertThat(foundUsuario.getUser()).isEqualTo("isabelt");
    }

    @Test
    public void userNoExiste() {
        Usuario foundUsuario = usuarioRepository.buscarLogin("isabel", "password123");
        assertThat(foundUsuario).isNull();
    }

}
