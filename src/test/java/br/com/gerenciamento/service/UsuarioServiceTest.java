package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setSenha("password");
    }

    @Test
    public void salvarUsuario_EmailExists() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(new Usuario());
        assertThrows(EmailExistsException.class, () -> serviceUsuario.salvarUsuario(usuario));
    }

    @Test
    public void salvarUsuario_CriptografiaSenha() throws Exception {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);

        serviceUsuario.salvarUsuario(usuario);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        assertEquals(Util.md5("password"), usuario.getSenha());
    }

    @Test
    public void loginUser_ValidCredentials() throws NoSuchAlgorithmException {
        when(usuarioRepository.buscarLogin("test@example.com", Util.md5("password"))).thenReturn(usuario);

        Usuario result = serviceUsuario.loginUser("test@example.com", Util.md5("password"));

        assertNotNull(result);
        assertEquals(usuario.getEmail(), result.getEmail());
    }

    @Test
    public void loginUser_InvalidCredentials() {
        when(usuarioRepository.buscarLogin("invalid@example.com", "wrongpassword")).thenReturn(null);

        Usuario result = serviceUsuario.loginUser("invalid@example.com", "wrongpassword");

        assertNull(result);
    }
}
