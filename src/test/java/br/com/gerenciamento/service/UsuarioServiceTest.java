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

    // #1 salvar um usuario que já estava cadastrado
    @Test
    public void SalvarUsuarioExistente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Fulaninho");
        usuario.setSenha("123456");
        usuario.setEmail("fulaninho@gmail.com");

        serviceUsuario.salvarUsuario(usuario); // salvando pela primeira vez

        // tentar salvar novamente (duplicata) gera um erro
        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    // #2 salvar um usuario curto e gerar um erro
    @Test
    public void salvarUsuarioNomeCurto() {
        Usuario usuario = new Usuario();
        usuario.setUser("N"); // Nome menor que 3 caracteres
        usuario.setSenha("123456");
        usuario.setEmail("N@gmail.com");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    // #3 salvar um usuario longo e gerar um erro
    @Test
    public void salvarUsuarioNomeLongo() {
        Usuario usuario = new Usuario();
        usuario.setUser("Nome Muito Muito Muito Longo"); // Nome maior que 20 caracteres
        usuario.setSenha("123456");
        usuario.setEmail("longonome@gmail.com");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    // #4 criar um usuario e logar nele com sucesso
    @Test
    public void loginUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Cicrano");
        usuario.setSenha("123456");
        usuario.setEmail("cicrano@gmail.com");

        serviceUsuario.salvarUsuario(usuario);

        Usuario UsuarioRetorno = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());

        Assert.assertEquals(usuario.getEmail(), UsuarioRetorno.getEmail());
    }
}
