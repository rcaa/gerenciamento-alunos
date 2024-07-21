package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void loginUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("User");
        usuario.setEmail("user@gmail.com");
        usuario.setSenha("user123");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario userLogin = this.serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        Assert.assertEquals(usuario.getId(), userLogin.getId());
    }

    @Test
    public void salvarUsuarioComEmailJaUtilizado() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setId(2L);
        usuario1.setUser("User123");
        usuario1.setEmail("user123@gmail.com");
        usuario1.setSenha("user123");
        this.serviceUsuario.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setId(3L);
        usuario2.setUser("User321");
        usuario2.setEmail("user123@gmail.com");
        usuario2.setSenha("user321");

        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });
    }

    @Test
    public void salvarUsuarioComPoucosCaracteres() throws Exception {
        Usuario user = new Usuario();
        user.setId(4L);
        user.setUser("zé");
        user.setEmail("jose@gmail.com");
        user.setSenha("jose123");
        
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(user);
        });
    }

    @Test
    public void salvarUsuarioComFormatoInvalidoDeEmail() throws Exception{
        Usuario user = new Usuario();
        user.setId(5L);
        user.setUser("zé");
        user.setEmail("jose545.com");
        user.setSenha("jose123");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(user);
        });
    }

}
