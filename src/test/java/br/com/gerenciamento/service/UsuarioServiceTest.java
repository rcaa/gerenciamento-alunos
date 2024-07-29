package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;

import java.security.NoSuchAlgorithmException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarUsuarioComSucesso() throws Exception {
        Usuario usuario = new Usuario("João", "joao@exemplo.com", "senha123");
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);
        OngoingStubbing<Usuario> usuarioOngoingStubbing = when(usuarioRepository.save(usuario)).thenReturn(usuario);

        serviceUsuario.salvarUsuario(usuario);

        assertNotNull(usuario);
        assertEquals("João",
                usuario.getUser());
        assertNotNull(usuario.getSenha());
    }

    @Test
    public void testSalvarUsuarioEmailExistente() {
        Usuario usuario = new Usuario("Maria", "maria@exemplo.com", "senha123");
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        EmailExistsException emailExistsException = assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });
    }

    @Test
    public void testSalvarUsuarioCriptoException() throws Exception {
        Usuario usuario = new Usuario("Carlos", "carlos@exemplo.com", "senha123");
        OngoingStubbing<Usuario> usuarioOngoingStubbing = when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);
        when(Util.md5(usuario.getSenha())).thenThrow(NoSuchAlgorithmException.class);

        assertThrows(CriptoExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });
    }

    @Test
    public void testLoginUser() {
        Usuario usuario = new Usuario("Ana", "ana@example.com", "senha123");
        when(usuarioRepository.buscarLogin("ana@example.com", "senha123")).thenReturn(usuario);

        Usuario encontrado = serviceUsuario.loginUser("ana@example.com", "senha123");

        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getUser());
    }
}