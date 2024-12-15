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
    public void procurarUsuarioPeloEmail(){
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");

        this.usuarioRepository.save(usuario);

        assertEquals("Vinicius", usuarioRepository.findByEmail("vinicius123@gmail.com").getUser());
    }

    @Test
    public void buscarLogin(){
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");

        this.usuarioRepository.save(usuario);

        assertEquals("vinicius123@gmail.com", usuarioRepository.buscarLogin("Vinicius", "vinicius123").getEmail());
    }

    @Test
    public void procurarPeloEmailInexistente(){
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");

        this.usuarioRepository.save(usuario);

        assertNull(usuarioRepository.buscarLogin("Dieguinho", "abc123"));
    }

    @Test
    public void deletarUsuarioById(){
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setEmail("jorge123@gmail.com");
        usuario.setUser("Jorge");
        usuario.setSenha("jorge123");

        this.usuarioRepository.save(usuario);

        int qtdUsuario1 = usuarioRepository.findAll().size();
        this.usuarioRepository.deleteById(3L);
        int qtdUsuario2 = usuarioRepository.findAll().size();
        assertEquals(qtdUsuario1 - 1, qtdUsuario2);
    }
}
