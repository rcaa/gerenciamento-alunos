package br.com.gerenciamento.service;

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

    // #1 - Salvar um usuário com um nome muito curto (erro de validação)
    @Test
    public void salvarUsuarioNomeCurto() {
        Usuario usuario = new Usuario();
        usuario.setUser("A"); // Nome menor que 3 caracteres
        usuario.setSenha("senha123");
        usuario.setEmail("nomecurto@gmail.com");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });
    }

    // #2 - Realizar login com sucesso
    @Test
    public void loginUsuarioSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Beltrano");
        usuario.setSenha("senha123");
        usuario.setEmail("beltrano@gmail.com");

        serviceUsuario.salvarUsuario(usuario); // Salva o usuário

        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());

        Assert.assertNotNull("Usuário não deve ser nulo", usuarioLogado);
        Assert.assertEquals(usuario.getEmail(), usuarioLogado.getEmail());
    }

    // #3 - Tentar login com senha incorreta
    @Test
    public void loginUsuarioSenhaIncorreta() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("BeltranoSenhaErrada");
        usuario.setSenha("senha123");
        usuario.setEmail("beltranoerrado@gmail.com");

        serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), "senhaIncorreta");

        Assert.assertNull("Usuário deve ser nulo devido à senha incorreta", usuarioLogado);
    }

    // #4 - Tentar login com usuário inexistente
    @Test
    public void loginUsuarioInexistente() {
        Usuario usuarioLogado = serviceUsuario.loginUser("UsuarioInexistente", "senha123");

        Assert.assertNull("Usuário deve ser nulo pois não existe no sistema", usuarioLogado);
    }
}
