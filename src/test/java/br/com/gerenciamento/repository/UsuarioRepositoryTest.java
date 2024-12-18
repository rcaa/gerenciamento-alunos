package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    public void criarRepositorioGenerico() {
        usuarioRepository.deleteAll();

        Usuario usuario1 = new Usuario();
        usuario1.setEmail("usuario1@gmail.com");
        usuario1.setUser("usuario1");
        usuario1.setSenha("12345");

        Usuario usuario2 = new Usuario();

        usuario2.setEmail("usuario2@gmail.com");
        usuario2.setUser("usuario2");
        usuario2.setSenha("54321");

        Usuario usuario3 = new Usuario();

        usuario3.setEmail("usuario3@gmail.com");
        usuario3.setUser("usuario3");
        usuario3.setSenha("12543");

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void findByEmailCorreto() {
        criarRepositorioGenerico();

        Usuario encontrado = usuarioRepository.findByEmail("usuario1@gmail.com");

        assertThat(encontrado.getUser()).isEqualTo("usuario1");
    }

    @Test
    public void buscarLoginCorreto() {
        criarRepositorioGenerico();

        Usuario encontrado = usuarioRepository.buscarLogin("usuario1", "12345");

        assertThat(encontrado.getUser()).isEqualTo("usuario1");
    }

    @Test
    public void buscarLoginErrado() {
        criarRepositorioGenerico();

        Usuario encontrado = usuarioRepository.buscarLogin("usuario", "191919");

        assertThat(encontrado).isNull();
    }

    @Test
    public void findByEmailErrado() {
        criarRepositorioGenerico();

        Usuario encontrado = usuarioRepository.findByEmail("usuario@gmail.com");

        assertThat(encontrado).isNull();
    }
}