package br.com.gerenciamento.service;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;

@SpringBootTest
public class UsuarioServiceTest {

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setEmail("jeff@email.com");
        usuario.setUser("jefferson");
        usuario.setSenha("jeff123");
    }

    @Test
    public void salvarUsuarioComSucesso() throws Exception {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);
        serviceUsuario.salvarUsuario(usuario);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void lancarExcecaoQuandoEmailJaExistir() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(new Usuario());
        assertThrows(EmailExistsException.class, () -> serviceUsuario.salvarUsuario(usuario));
    }

    @Test
    public void lancarExcecaoDeCriptografiaQuandoErroCripto() throws NoSuchAlgorithmException {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);
        Mockito.mockStatic(Util.class).when(() -> Util.md5(usuario.getSenha())).thenThrow(new NoSuchAlgorithmException());
        assertThrows(CriptoExistsException.class, () -> serviceUsuario.salvarUsuario(usuario));
    }

    @Test
    public void fazerLoginComUsuarioValido() {
        when(usuarioRepository.buscarLogin(usuario.getUser(), usuario.getSenha())).thenReturn(usuario);
        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        assertNotNull(usuarioLogado);
        assertEquals(usuario.getUser(), usuarioLogado.getUser());
    }
}
