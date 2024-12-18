package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    public void setup() {
        // Configurando dois usuários no banco de dados em memória
        usuario1 = new Usuario();
        usuario1.setEmail("user1@example.com");
        usuario1.setUser("usuario1");
        usuario1.setSenha("senha123");

        usuario2 = new Usuario();
        usuario2.setEmail("user2@example.com");
        usuario2.setUser("usuario2");
        usuario2.setSenha("senha456");

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
    }

    @Test
    public void testBuscarLoginValido() {
        // Testando login válido
        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("usuario1", "senha123");

        assertNotNull(usuarioEncontrado);
        assertEquals("user1@example.com", usuarioEncontrado.getEmail());
    }

    @Test
    public void testBuscarLoginInvalido() {
        // Testando login com dados inválidos
        Usuario usuarioNaoEncontrado = usuarioRepository.buscarLogin("usuarioInexistente", "senhaIncorreta");

        assertNull(usuarioNaoEncontrado);
    }

    @Test
    public void testFindByEmail() {
        // Testando encontrar usuário pelo email
        Usuario usuarioEncontrado = usuarioRepository.findByEmail("user2@example.com");

        assertNotNull(usuarioEncontrado);
        assertEquals("usuario2", usuarioEncontrado.getUser());
    }

    @Test
    public void testSaveUsuario() {
        // Testando salvamento de novo usuário
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("novo@example.com");
        novoUsuario.setUser("novoUsuario");
        novoUsuario.setSenha("novaSenha");

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        assertNotNull(usuarioSalvo.getId());
        assertEquals("novo@example.com", usuarioSalvo.getEmail());
        assertEquals("novoUsuario", usuarioSalvo.getUser());
    }
}
