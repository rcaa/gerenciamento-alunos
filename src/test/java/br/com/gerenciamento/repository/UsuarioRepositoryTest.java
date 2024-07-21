package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repositoryUsuario;

    @Test
    public void findByEmail() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("User");
        usuario.setEmail("user@gmail.com");
        usuario.setSenha("user123");
        this.repositoryUsuario.save(usuario);

        Usuario userByEmail = this.repositoryUsuario.findByEmail(usuario.getEmail());
        Assert.assertEquals(usuario.getEmail(), userByEmail.getEmail());
    }

    @Test
    public void buscarLogin() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUser("User2");
        usuario.setEmail("user2@gmail.com");
        usuario.setSenha("user12345");
        this.repositoryUsuario.save(usuario);

        Usuario userLogin = this.repositoryUsuario.buscarLogin(usuario.getUser(), usuario.getSenha());
        Assert.assertEquals(usuario.getEmail(), userLogin.getEmail());
    }

    @Test
    public void saveUser() {
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setUser("User3");
        usuario.setEmail("user3@gmail.com");
        usuario.setSenha("user7474");
        this.repositoryUsuario.save(usuario);

        Usuario user = this.repositoryUsuario.getReferenceById(usuario.getId());
        Assert.assertNotNull(user);
    }

    @Test
    public void deleteById() {
        Usuario usuario = new Usuario();
        usuario.setId(4L);
        usuario.setUser("User4");
        usuario.setEmail("user4@gmail.com");
        usuario.setSenha("user4444");
        this.repositoryUsuario.save(usuario);
        this.repositoryUsuario.deleteById(usuario.getId());

        Assert.assertFalse(this.repositoryUsuario.existsById(usuario.getId()));
    }

}
