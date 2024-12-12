package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;
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

    @Test
    public void salvarUsuarioValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senha123");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetorno = serviceUsuario.loginUser("usuarioTeste", "senha123");
        Assert.assertEquals("teste@exemplo.com", usuarioRetorno.getEmail());
    }

    @Test
    public void cadastrarSemSenha() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("usuarioTeste");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    @Test
    public void cadastrarEmailExistente() {
        Usuario usuario1 = new Usuario();
        usuario1.setEmail("teste@exemplo.com");
        usuario1.setUser("usuarioTeste1");
        usuario1.setSenha("senha123");

        try {
            serviceUsuario.salvarUsuario(usuario1);
        } catch (Exception e) {
            Assert.fail("Erro ao salvar o primeiro usuário: " + e.getMessage());
        }

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("teste@exemplo.com");
        usuario2.setUser("usuarioTeste2");
        usuario2.setSenha("senha456");

        Assert.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuario2);
        });
    }

    @Test
    public void cadastrarUsuarioInvalido() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("us");
        usuario.setSenha("senha123");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });
    }

}
