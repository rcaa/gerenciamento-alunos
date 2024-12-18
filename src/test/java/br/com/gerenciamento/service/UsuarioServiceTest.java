package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.exception.EmailExistsException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuarioDadosCorretos() {

        usuarioRepository.deleteAll();

        Usuario user = new Usuario();

        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setUser("username");
        user.setSenha("password");

        try {

            this.serviceUsuario.salvarUsuario(user);

        } catch (Exception e) {

            Assert.fail("Nenhuma exceção deveria ocorrer aqui, porém: " + e.getMessage());

        }

    }

    @Test
    public void salvarUsuarioEmailDuplicado() {

        usuarioRepository.deleteAll();

        Usuario user1 = new Usuario();
        Usuario user2 = new Usuario();

        user1.setId(1L);
        user1.setEmail("user1@gmail.com");
        user1.setUser("username1");
        user1.setSenha("password1");

        user2.setId(2L);
        user2.setEmail("user1@gmail.com");
        user2.setUser("username2");
        user2.setSenha("password2");

        try {
            this.serviceUsuario.salvarUsuario(user1);
        } catch (Exception e) {
            Assert.fail("Nenhuma exceção deveria ocorrer aqui");
        }

        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(user2);
        });

    }

    @Test
    public void salvarUsuarioUsernameCurto() {

        usuarioRepository.deleteAll();

        Usuario user = new Usuario();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setUser("us"); // Nome de usuário muito curto
        user.setSenha("password");

        try {
            this.serviceUsuario.salvarUsuario(user);
            Assert.fail("Deveria ter sido lançada uma exceção sobre o nome do usuário");
        } catch (Exception e) {
            Assert.assertTrue("Erro esperado relacionado ao nome do usuário", true);
        }
    }

    @Test
    public void salvarUsuarioUsernameLongo() {

        usuarioRepository.deleteAll();

        Usuario user = new Usuario();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setUser("ExtremelyLongUsername"); // Nome de usuário muito longo
        user.setSenha("password");

        try {
            this.serviceUsuario.salvarUsuario(user);
            Assert.fail("Deveria ter sido lançada uma exceção sobre o nome do usuário");
        } catch (Exception e) {
            Assert.assertTrue("Erro esperado relacionado ao nome do usuário", true);
        }
    }

}
