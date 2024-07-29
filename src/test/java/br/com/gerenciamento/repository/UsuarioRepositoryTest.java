package br.com.gerenciamento.repository;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.*;

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
    public void Salvar() {

        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("testeUser");
        usuario.setSenha("senha123");
        this.usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.findById(usuario.getId()).orElse(null);
        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(usuarioEncontrado.getUser(), usuario.getUser());
    }

    @Test
    @Transactional
    public void FindByEmail() {

        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("testeUser");
        usuario.setSenha("senha123");
        this.usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("teste@exemplo.com");
        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(usuarioEncontrado.getEmail(), usuario.getEmail());
    }

    @Test
    public void testBuscarLogin() {

        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("testeUser");
        usuario.setSenha("senha123");
        this.usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("testeUser", "senha123");
        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(usuarioEncontrado.getUser(), usuario.getUser());
    }

    @Test
    public void testFindByEmail_NotFound() {
        Usuario usuarioNaoEncontrado = usuarioRepository.findByEmail("naoexiste@exemplo.com");
        Assert.assertNull(usuarioNaoEncontrado);
    }


}
