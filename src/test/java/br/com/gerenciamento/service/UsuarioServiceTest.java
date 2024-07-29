package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Util util;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void salvarUsuarioComEmailUnico() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@exemplo.com");
        usuario.setSenha("senha123");

        when(usuarioRepository.findByEmail(eq("usuario@exemplo.com"))).thenReturn(null);

        
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        serviceUsuario.salvarUsuario(usuario);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));

    }

    @Test
    public void salvarUsuarioComEmailJaExistente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@exemplo.com");
        usuario.setSenha("senha123");

        when(usuarioRepository.findByEmail(eq("usuario@exemplo.com"))).thenReturn(new Usuario());

        EmailExistsException thrownException = assertThrows(
            EmailExistsException.class,
            () -> serviceUsuario.salvarUsuario(usuario)
        );

        assertEquals("Este email já esta cadastrado: usuario@exemplo.com", thrownException.getMessage());
    }

}