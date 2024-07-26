package br.com.gerenciamento.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Test");
        usuario.setEmail("test@example.com");
        usuario.setSenha("password");
        serviceUsuario.salvarUsuario(usuario);

        Assert.assertTrue(usuario.getUser().equals("Test"));
        
    }

    @Test(expected = EmailExistsException.class)
    public void naoDeveSalvarUsuarioComEmailDuplicado() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setUser("Ana");
        usuario1.setEmail("ana@example.com");
        usuario1.setSenha("password");

        serviceUsuario.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setUser("Carlos");
        usuario2.setEmail("ana@example.com");
        usuario2.setSenha("password");

        serviceUsuario.salvarUsuario(usuario2);
    }

    @Test(expected = CriptoExistsException.class)
    public void deveLancarExcecaoCripto() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Pedro");
        usuario.setEmail("pedro@example.com");
        usuario.setSenha(null); 

        serviceUsuario.salvarUsuario(usuario);
    }

    @Test
    public void loginUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste-login@email.com.br");
        usuario.setUser("teste-login");
        usuario.setSenha("12345");

        this.serviceUsuario.salvarUsuario(usuario);
        
        Usuario usuarioLogado = this.serviceUsuario.loginUser(usuario.getUser(), (usuario.getSenha()));

        Assert.assertNotNull(usuarioLogado);
        Assert.assertEquals("teste-login", usuarioLogado.getUser()); 
        Assert.assertEquals("teste-login@email.com.br", usuarioLogado.getEmail());
}

}


