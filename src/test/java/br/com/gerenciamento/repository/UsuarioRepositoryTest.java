package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Usuario usuario1;
    private Usuario usuario2;

    @Before
    public void setUp() {
        // Primeiro usuário
        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setUser("usuario1");
        usuario1.setSenha("senha1");
        usuario1.setEmail("usuario1@email.com");

        // Segundo usuário
        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setUser("usuario2");
        usuario2.setSenha("senha2");
        usuario2.setEmail("usuario2@email.com");
    }

    // #1 encontrar usuário por email
    @Test
    public void encontrarPorEmail() {
        Mockito.when(mockUsuarioRepository.findByEmail("usuario1@email.com")).thenReturn(usuario1);

        Usuario result = mockUsuarioRepository.findByEmail("usuario1@email.com");

        assertEquals("usuario1", result.getUser());
    }

    // #2 login com usuário e senha corretos
    @Test
    public void buscarLoginValido() {
        Mockito.when(mockUsuarioRepository.buscarLogin("usuario2", "senha2")).thenReturn(usuario2);

        Usuario result = mockUsuarioRepository.buscarLogin("usuario2", "senha2");

        assertEquals("usuario2@email.com", result.getEmail());
    }

    // #3 login com usuário e senha inválidos
    @Test
    public void buscarLoginInvalido() {
        Mockito.when(mockUsuarioRepository.buscarLogin("usuarioInvalido", "senhaInvalida")).thenReturn(null);

        Usuario result = mockUsuarioRepository.buscarLogin("usuarioInvalido", "senhaInvalida");

        assertNull(result);
    }

    // #4 não encontrar usuário por email inexistente
    @Test
    public void naoEncontrarPorEmail() {
        Mockito.when(mockUsuarioRepository.findByEmail("emailInexistente@email.com")).thenReturn(null);

        Usuario result = mockUsuarioRepository.findByEmail("emailInexistente@email.com");

        assertNull(result);
    }
}
