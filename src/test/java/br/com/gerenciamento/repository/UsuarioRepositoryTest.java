package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.gerenciamento.model.Usuario;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByEmail() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("jeff23@gmail.com");
        usuario.setUser("JeffersonAlan");
        usuario.setSenha("jeff112233");

        this.usuarioRepository.save(usuario);

        assertEquals("JeffersonAlan", usuarioRepository.findByEmail("jeff23@gmail.com").getUser());
    }

    @Test
    public void testFindByEmail_NonExistentEmail() {
        Usuario foundUsuario = usuarioRepository.findByEmail("naoexiste@example.com");
        assertNull(foundUsuario);
    }

    @Test
    public void buscarLogin(){
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("jeff23@gmail.com");
        usuario.setUser("JeffersonAlan");
        usuario.setSenha("jeff112233");

        this.usuarioRepository.save(usuario);

        assertEquals("jeff23@gmail.com", usuarioRepository.buscarLogin("JeffersonAlan", "jeff112233").getEmail());
    }

    @Test
    public void testBuscarLogin_InvalidCredentials() {
        Usuario foundUsuario = usuarioRepository.buscarLogin("jefferson121123", "senhaErrada");
        assertNull(foundUsuario);
    }
}
