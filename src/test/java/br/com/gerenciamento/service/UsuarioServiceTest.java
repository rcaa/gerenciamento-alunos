package br.com.gerenciamento.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    
    @Test
    public void loginUserUsuarioNaoExistente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste-nao-existe@email.com.br");
        usuario.setUser("usuario-nao-existe");
        usuario.setSenha("12345");

        try {
            this.serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
            Assert.fail("Esperava-se a exceção UsuarioNaoEncontradoException");
        } catch (UsuarioNaoEncontradoException e) {
            Assert.assertEquals("Usuário não encontrado", e.getMessage());
        }
    }

    @Test
    public void loginUserSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("teste-senha-incorreta@email.com.br");
        usuario.setUser("teste-senha-incorreta");
        usuario.setSenha("12345");
        Usuario novoUsuario = this.serviceUsuario.salvarUsuario(usuario);

        try {
            this.serviceUsuario.loginUser(novoUsuario.getUser(), "senhaErrada");
            Assert.fail("Esperava-se a exceção SenhaIncorretaException");
        } catch (SenhaIncorretaException e) {
            Assert.assertEquals("Senha incorreta", e.getMessage());
        }
    }

    @Test
    public void loginUserEmailEmBranco() {
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setEmail("");
        usuario.setUser("teste-email-branco");
        usuario.setSenha("12345");
        Usuario novoUsuario = this.serviceUsuario.salvarUsuario(usuario);

        try {
            this.serviceUsuario.loginUser(novoUsuario.getUser(), novoUsuario.getSenha());
            Assert.fail("Esperava-se a exceção IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("O usuário não pode ser vazio", e.getMessage());
        }
    }

    @Test
    public void loginUserEmailInvalido() {
        Usuario usuario = new Usuario();
        usuario.setId(4L);
        usuario.setEmail("email-invalido");
        usuario.setUser("teste-email-invalido");
        usuario.setSenha("12345");
        Usuario novoUsuario = this.serviceUsuario.salvarUsuario(usuario);

        try {
            this.serviceUsuario.loginUser(novoUsuario.getUser(), novoUsuario.getSenha());
            Assert.fail("Esperava-se a exceção EmailInvalidoException");
        } catch (EmailInvalidoException e) {
            Assert.assertEquals("Email inválido", e.getMessage());
        }
    }


}
