package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        usuarioRepository.deleteAll();
        
        // Adicionar um usuário de teste para os testes de login e email
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setUser("testuser");
        usuario.setSenha("password123");

        usuarioRepository.save(usuario);
    }

    // Teste para encontrar um usuário pelo email
    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Usuario usuario = usuarioRepository.findByEmail(email);
        assertNotNull(usuario); 
        assertEquals(email, usuario.getEmail());
    }

    // Teste para encontrar um usuário pelo email quando o usuário não existe
    @Test
    public void testFindByEmailNotFound() {
        String email = "nonexistent@example.com";
        Usuario usuario = usuarioRepository.findByEmail(email);
        assertNull(usuario); 
    }

    // Teste para login bem-sucedido com o nome de usuário e senha corretos
    @Test
    public void testBuscarLogin() {
        String username = "testuser";
        String password = "password123";
        Usuario usuario = usuarioRepository.buscarLogin(username, password);
        assertNotNull(usuario); 
        assertEquals(username, usuario.getUser()); 
        assertEquals(password, usuario.getSenha());
    }

    // Teste para login malsucedido com nome de usuário ou senha incorretos
    @Test
    public void testBuscarLoginInvalidCredentials() {
        String username = "invaliduser";
        String password = "invalidpassword";
        Usuario usuario = usuarioRepository.buscarLogin(username, password);
        assertNull(usuario); 
    }
}