import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.es.gerenciamentoalunos.model.Usuario;
import br.com.es.gerenciamentoalunos.repository.UsuarioRepository;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario(1L, "bianca", "bianca@email.com");
    }

    @Test
    public void testSalvarUsuario() {
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        assertNotNull(usuarioSalvo);
        assertEquals(usuario.getNome(), usuarioSalvo.getNome());
    }

    @Test
    public void testBuscarUsuarioPorId() {
        usuarioRepository.save(usuario);
        Usuario usuarioBuscado = usuarioRepository.findById(1L).orElse(null);

        assertNotNull(usuarioBuscado);
        assertEquals(usuario.getId(), usuarioBuscado.getId());
    }

    @Test
    public void testListarTodosOsUsuarios() {
        usuarioRepository.save(usuario);
        List<Usuario> usuarios = usuarioRepository.findAll();

        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
    }
}
