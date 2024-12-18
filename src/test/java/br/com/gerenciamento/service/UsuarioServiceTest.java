import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import br.com.es.gerenciamentoalunos.service.UsuarioService;
import br.com.es.gerenciamentoalunos.repository.UsuarioRepository;
import br.com.es.gerenciamentoalunos.model.Usuario;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioService usuarioService;
    
    private Usuario usuario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario(1L, "bianca", "bianca@email.com");
    }

    @Test
    public void testCriarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);

        assertNotNull(usuarioCriado);
        assertEquals(usuario.getNome(), usuarioCriado.getNome());
    }

    @Test
    public void testBuscarUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario usuarioBuscado = usuarioService.buscarUsuarioPorId(1L);

        assertNotNull(usuarioBuscado);
        assertEquals(usuario.getId(), usuarioBuscado.getId());
    }

    @Test
    public void testListarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testExcluirUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.excluirUsuario(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
