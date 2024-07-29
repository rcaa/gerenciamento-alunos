package br.com.gerenciamento.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;

import org.junit.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //teste 1 - salvar usuario
    @Test
    public void testeSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioteste@exemplo.com");
        usuario.setUser("usuario"); 
        usuario.setSenha("123456789");
        assertNotNull(this.usuarioRepository.save(usuario));
    }

    // teste 2 - buscar email que nao existe
    @Test
    public void testeBuscarEmailNaoEncontrado() {
        Usuario usuarioEmail = usuarioRepository.findByEmail("usarioTeste@exemplo.com");
        assertNull(usuarioEmail);
    }

    // teste 3 - buscar login
    @Test
    public void testeBuscarLogin(){
        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioLogin@exemplo.com");
        usuario.setUser("usuarioLogin"); 
        usuario.setSenha("1122334455");
        assertNotNull(this.usuarioRepository.save(usuario));

        Usuario usuarioRepositorio = usuarioRepository.buscarLogin("usuarioLogin", "1122334455");
        assertNotNull(usuarioRepositorio);
        assertEquals("usuarioLogin", usuarioRepositorio.getUser());
        assertEquals("1122334455", usuarioRepositorio.getSenha());
    }

    // teste 4 - mudar email do usario
    @Test
    public void testeMudarEmail(){
        Usuario usuario = new Usuario();
        usuario.setEmail("emailAntigo@exemplo.com");
        usuario.setUser("usuarioTeste"); 
        usuario.setSenha("123456789");
        Usuario usuarioRep = usuarioRepository.save(usuario);

        usuarioRep.setEmail("emailNovo@exemplo.com");
        usuarioRepository.save(usuarioRep);

        Usuario usuarioAtualizado = usuarioRepository.findByEmail("emailNovo@exemplo.com");
        assertNotNull(usuarioAtualizado);
        assertEquals("emailNovo@exemplo.com", usuarioAtualizado.getEmail());
    }
}
