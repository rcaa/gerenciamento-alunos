package br.com.gerenciamento.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        Usuario usuario1 = new Usuario();
        usuario1.setEmail("usuario1@example.com");
        usuario1.setUser("usuario1");
        usuario1.setSenha("senha123");
        usuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("usuario2@example.com");
        usuario2.setUser("usuario2");
        usuario2.setSenha("senha456");
        usuarioRepository.save(usuario2);
    }

    @Test
    void testFindByEmail() {
        Usuario encontrado = usuarioRepository.findByEmail("usuario1@example.com");

        assertEquals("usuario1@example.com", encontrado.getEmail());
        assertEquals("usuario1", encontrado.getUser());
    }

    @Test
    void testBuscarLogin() {
        Usuario encontrado = usuarioRepository.buscarLogin("usuario1", "senha123");

        assertEquals("usuario1@example.com", encontrado.getEmail());
        assertEquals("usuario1", encontrado.getUser());
    }

    @Test
    void testFindByEmailComEmailNaoExistente() {
        Usuario encontrado = usuarioRepository.findByEmail("emailNaoExistente@example.com");

        assertNull(encontrado);
    }

    @Test
    void testBuscarLoginComCredenciaisInvalidas() {
        Usuario encontrado = usuarioRepository.buscarLogin("usuarioInvalido", "senhaInvalida");

        assertNull(encontrado);
    }
}
