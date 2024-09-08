package br.com.gerenciamento.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void encontrarEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("emailteste@teste.com");
        usuario.setUser("UsuarioTeste");
        usuario.setSenha("senha");
        this.usuarioRepository.save(usuario);
        Usuario usuarioRetorno = usuarioRepository.findByEmail("emailteste@teste.com");
        assertNotNull(usuarioRetorno);
        assertEquals(usuarioRetorno.getEmail(), usuario.getEmail());

    }

    @Test
    @Transactional
    public void buscarLogin() {
        Usuario usuario = new Usuario();
        usuario.setEmail("emailteste@teste.com");
        usuario.setUser("UsuarioTeste");
        usuario.setSenha("senha");
        usuarioRepository.save(usuario);
        Usuario usuarioRetorno = usuarioRepository.buscarLogin("UsuarioTeste", "senha");
        assertNotNull(usuarioRetorno);
        assertEquals("UsuarioTeste", usuarioRetorno.getUser());
        assertEquals("senha", usuarioRetorno.getSenha());
    }

    @Test
    @Transactional
    public void emailNaoEncontrado() {
        Usuario usuarioEncontrado = usuarioRepository.findByEmail("emailnaocadastrado@email.com");
        assertNull(usuarioEncontrado);
    }

    @Test
    @Transactional
    public void salvarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setUser("UsuarioTeste");
        usuario.setSenha("senha");
        usuario.setEmail("emailteste@teste.com");

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        assertNotNull(usuarioSalvo.getId());

        Usuario buscaUsuario = usuarioRepository.findById(usuarioSalvo.getId()).orElse(null);
        assertNotNull(buscaUsuario);
        assertEquals("emailteste@teste.com", buscaUsuario.getEmail());
        assertEquals("UsuarioTeste", buscaUsuario.getUser());
        assertEquals("senha", buscaUsuario.getSenha());
    }
}
