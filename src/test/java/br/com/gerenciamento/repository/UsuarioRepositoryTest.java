package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private ServiceUsuario serviceUsuario;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void findByEmail() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Daniel");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetorno = this.usuarioRepository.findByEmail("teste@email.com");
        Assert.assertTrue(usuarioRetorno.getEmail().equals(usuario.getEmail()));
    }

    @Test
    @Transactional
    public void emailInválido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste.com");
        usuario.setSenha("12345678");
        usuario.setUser("Daniel");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
        this.serviceUsuario.salvarUsuario(usuario);});
    }

    
    @Test
    @Transactional
    public void userInválido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Du");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);});
    }

    @Test
    @Transactional
    public void buscarLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Daniel");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetorno = this.usuarioRepository.buscarLogin("Daniel", Util.md5("12345678"));
        Assert.assertNotNull(usuarioRetorno);
        Assert.assertTrue(usuarioRetorno.getEmail().equals(usuario.getEmail()));
    }
}
