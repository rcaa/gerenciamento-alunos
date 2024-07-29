package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senha123");

        try {
            // Salva o usuário
            this.serviceUsuario.salvarUsuario(usuario);

            // Verifica se o usuário foi salvo
            Usuario usuarioRetornado = this.usuarioRepository.findByEmail("teste@example.com");
            Assert.assertNotNull(usuarioRetornado);
            Assert.assertEquals("usuarioTeste", usuarioRetornado.getUser());
            Assert.assertNotEquals("senha123", usuarioRetornado.getSenha()); // Verifica que a senha foi criptografada
        } catch (Exception e) {
            Assert.fail("Não era esperado que uma exceção fosse lançada: " + e.getMessage());
        }
    }

    @Test
    public void salvarUsuarioComEmailExistente() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("existente@example.com");
        usuarioExistente.setUser("usuarioExistente");
        usuarioExistente.setSenha("senhaExistente");

        try {
            // Salva o usuário existente
            this.serviceUsuario.salvarUsuario(usuarioExistente);

            Usuario novoUsuario = new Usuario();
            novoUsuario.setEmail("existente@example.com");
            novoUsuario.setUser("novoUsuario");
            novoUsuario.setSenha("novaSenha");

            // Tenta salvar um novo usuário com o mesmo email
            this.serviceUsuario.salvarUsuario(novoUsuario);

            Assert.fail("Deveria ter lançado uma exceção EmailExistsException");
        } catch (EmailExistsException e) {
            Assert.assertTrue(e.getMessage().contains("Este email já esta cadastrado"));
        } catch (Exception e) {
            Assert.fail(
                    "Era esperado que a exceção fosse EmailExistsException, mas foi: " + e.getClass().getSimpleName());
        }
    }

    @Test
    public void loginUsuarioSenhaIncorreta() {
        // Cria um usuário válido
        Usuario usuario = new Usuario();
        usuario.setUser("usuarioValido");
        usuario.setSenha("senhaCorreta");

        try {
            // Salva o usuário
            this.serviceUsuario.salvarUsuario(usuario);

            // Tenta o login com a senha incorreta
            Usuario usuarioRetornado = this.serviceUsuario.loginUser("usuarioValido", "senhaIncorreta");

            // Verifica se o retorno é null, indicando que a senha estava incorreta
            Assert.assertNull(usuarioRetornado);
        } catch (Exception e) {
            Assert.fail("Não era esperado que uma exceção fosse lançada: " + e.getMessage());
        }
    }

    @Test
    public void loginUsuarioInvalido() {
        try {
            // Tentativa de login com credenciais incorretas
            Usuario usuarioRetornado = this.serviceUsuario.loginUser("usuarioInvalido", "senhaIncorreta");
            Assert.assertNull(usuarioRetornado);
        } catch (Exception e) {
            Assert.fail("Não era esperado que uma exceção fosse lançada: " + e.getMessage());
        }
    }
}
