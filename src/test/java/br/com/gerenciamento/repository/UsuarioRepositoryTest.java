package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll();

        Usuario usuario1 = new Usuario();
        usuario1.setEmail("joao@gmail.com");
        usuario1.setUser("joaosilva");
        usuario1.setSenha("senha123");

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("maria@gmail.com");
        usuario2.setUser("mariasouza");
        usuario2.setSenha("senha456");

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
    }

    @Test
    void testFindByEmail() {
        Usuario usuario = usuarioRepository.findByEmail("joao@gmail.com");
        assertThat(usuario).isNotNull();
        assertThat(usuario.getUser()).isEqualTo("joaosilva");
    }

    @Test
    void testBuscarLogin() {
        Usuario usuario = usuarioRepository.buscarLogin("mariasouza", "senha456");
        assertThat(usuario).isNotNull();
        assertThat(usuario.getEmail()).isEqualTo("maria@gmail.com");
    }

    @Test
    void testSaveAndFindAll() {
        Usuario usuario = new Usuario();
        usuario.setEmail("carlos@gmail.com");
        usuario.setUser("carlosalmeida");
        usuario.setSenha("senha789");

        usuarioRepository.save(usuario);

        assertThat(usuarioRepository.findAll()).hasSize(3);
    }

    @Test
    void testDeleteById() {
        Usuario usuario = usuarioRepository.findByEmail("joao@gmail.com");
        usuarioRepository.deleteById(usuario.getId());

        assertThat(usuarioRepository.findByEmail("joao@gmail.com")).isNull();
    }
}
