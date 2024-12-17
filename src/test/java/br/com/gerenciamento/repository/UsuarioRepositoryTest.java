package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @MockBean
    private UsuarioRepository mockUsuarioRepository;

    private Usuario usuario;

    @Before
    public void setUp() {
        // Criando o usuário de exemplo
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senha123");
    }

    // #1 - Testar busca de usuário por email
    @Test
    public void encontrarUsuarioPorEmail() {
        Mockito.when(mockUsuarioRepository.findByEmail("teste@exemplo.com")).thenReturn(usuario);

        Usuario result = mockUsuarioRepository.findByEmail("teste@exemplo.com");

        assertEquals("teste@exemplo.com", result.getEmail());
        assertEquals("usuarioTeste", result.getUser());
    }

    // #2 - Testar busca de usuário por email inexistente
    @Test
    public void naoEncontrarUsuarioPorEmail() {
        Mockito.when(mockUsuarioRepository.findByEmail("inexistente@exemplo.com")).thenReturn(null);

        Usuario result = mockUsuarioRepository.findByEmail("inexistente@exemplo.com");

        assertNull(result);
    }

    // #3 - Testar busca de usuário pelo login (user e senha corretos)
    @Test
    public void encontrarUsuarioPorLogin() {
        Mockito.when(mockUsuarioRepository.buscarLogin("usuarioTeste", "senha123")).thenReturn(usuario);

        Usuario result = mockUsuarioRepository.buscarLogin("usuarioTeste", "senha123");

        assertEquals("usuarioTeste", result.getUser());
        assertEquals("senha123", result.getSenha());
    }

    // #4 - Testar busca de usuário pelo login com credenciais incorretas
    @Test
    public void naoEncontrarUsuarioPorLoginIncorreto() {
        Mockito.when(mockUsuarioRepository.buscarLogin("usuarioErrado", "senhaErrada")).thenReturn(null);

        Usuario result = mockUsuarioRepository.buscarLogin("usuarioErrado", "senhaErrada");

        assertNull(result);
    }
}
