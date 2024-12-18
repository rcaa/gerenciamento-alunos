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
    public void buscarPorEmail() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("okarun@gmail.com");
        usuario.setUser("Okarun");
        usuario.setSenha("123");
        repositoryUsuario.save(usuario);

        Usuario userEmail = this.repositoryUsuario.findByEmail("okarun@gmail.com");
        Assert.assertEquals("okarun@gmail.com", userEmail.getEmail());

    }

    @Test
    public void buscarLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("okarun@gmail.com");
        usuario.setUser("Okarun");
        usuario.setSenha("123");
        repositoryUsuario.save(usuario);

        Usuario userLogin = this.repositoryUsuario.buscarLogin("Okarun", "123");
        Assert.assertEquals("Okarun", userLogin.getUser());
        Assert.assertEquals("123", userLogin.getSenha());

    }

    @Test
    public void buscarPorUserNaoCadastrado() {
        Usuario userLogin = this.repositoryUsuario.buscarLogin("Momo", "momo");
        Assert.assertNull("O Usuario deve retornar NULL", userLogin);

    }

    @Test
    public void buscarPorEmailNaoCadastrado() {
        Usuario userEmail = this.repositoryUsuario.findByEmail("momo@gmail.com");
        Assert.assertNull("O Email deve retornar NULL", userEmail);

    }


}
