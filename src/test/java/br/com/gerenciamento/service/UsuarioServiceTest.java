package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    ServiceUsuario serviceUsuario;

    @Test
    public void salvarSucesso () throws Exception {
        // Verifica se o usuário está sendo salvo corretamente
        Usuario usuario = new Usuario();
        usuario.setUser("Mayara");
        usuario.setEmail("mayara.keferreira@ufape.edu.br");
        usuario.setSenha("Ufape123456");
        this.serviceUsuario.salvarUsuario(usuario);
        assertNotNull(usuario); // Verifica se o usuário não é nulo após salvar
    }

    @Test
    public void salvarEmailDuplicado () {
        // Verifica se a exceção é lançada corretamente quando há tentativa de cadastrar dois usuários diferentes com o mesmo email
        Usuario usuario1 = new Usuario();
        usuario1.setUser("Mayara");
        usuario1.setEmail("mayara.keferreira@ufape.edu.br");
        usuario1.setSenha("Ufape123");

        Usuario usuario2 = new Usuario();
        usuario2.setUser("Mayara");
        usuario2.setEmail("mayara.keferreira@ufape.edu.br");
        usuario2.setSenha("123Ufape");

        try {
            this.serviceUsuario.salvarUsuario(usuario1);
            this.serviceUsuario.salvarUsuario(usuario2);
            Assertions.fail("Deveria ter lançado EmailExistsException");
        } catch (EmailExistsException e) { // Verifica se a exceção é lançada e se a mensagem está correta
            assertEquals("Este email já esta cadastrado: " + usuario2.getEmail(), e.getMessage());
        } catch (Exception e) {
            Assertions.fail("Deveria ter lançado EmailExistsException");
        }
    }

    @Test
    public void salvarErroCriptografiaSenha (){
        // Verifica o tratamento de erros ao criptografar a senha do usuário
        Usuario usuario = new Usuario();
        usuario.setUser("Mayara");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("123");

        try {
            this.serviceUsuario.salvarUsuario(usuario);
        } catch (NoSuchAlgorithmException e) { // Verifica se a exceção foi lançada e se a mensagem de erro é a esperada
            assertEquals("Error na criptografia da senha", e.getMessage());
        } catch (Exception e) {
            Assertions.fail("Deveria ter lançado CriptoExistsException");
        }
    }

    @Test
    public void login() throws Exception {
        // Verifica o funcionamento do método de login após salvar um usuário
        Usuario usuario = new Usuario();
        usuario.setUser("Mayara");
        usuario.setEmail("mayara.keferreira@ufape.edu.br");
        usuario.setSenha("Ufape123");
        this.serviceUsuario.salvarUsuario(usuario);
        Usuario usuarioLogado = this.serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        assertNotNull(usuarioLogado);// Verifica se o usuário não é nulo
        assertEquals(usuarioLogado.getUser(), usuario.getUser()); // Verifica se o usuário é igual ao cadastrado
        assertEquals(usuarioLogado.getSenha(), usuario.getSenha()); // Verifica se a senha é igual a cadastrada
    }
}
