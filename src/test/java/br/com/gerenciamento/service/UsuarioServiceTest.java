package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    public Usuario criarUsuarioGenerico() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@gmail.com");
        usuario.setUser("usuario");
        usuario.setSenha("1234");

        return usuario;
    }

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = criarUsuarioGenerico();

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);

        serviceUsuario.salvarUsuario(usuario);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void salvarUsuarioEmailDuplicado() {
        Usuario usuario = criarUsuarioGenerico();

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        assertThrows(EmailExistsException.class, () -> serviceUsuario.salvarUsuario(usuario));

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    public void salvarUsuarioFalhaSenha() throws Exception {
        Usuario usuario = criarUsuarioGenerico();

        mockStatic(Util.class);
        when(Util.md5(usuario.getSenha())).thenThrow(NoSuchAlgorithmException.class);

        assertThrows(CriptoExistsException.class, () -> serviceUsuario.salvarUsuario(usuario));

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    public void loginBemSucedido(){
        Usuario usuario = criarUsuarioGenerico();

        when(usuarioRepository.buscarLogin("usuario", "1234")).thenReturn(usuario);

        Usuario resultado = serviceUsuario.loginUser("usuario", "1234");

        assertNotNull(resultado);
        assertEquals("usuario", resultado.getUser());
        assertEquals("1234", resultado.getSenha());
    }
}