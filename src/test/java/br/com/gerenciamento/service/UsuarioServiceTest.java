package br.com.gerenciamento.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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

    @Test
    public void loginUserInvalido() throws Exception {
        String email = "teste-login-" + System.currentTimeMillis() + "@email.com";
        
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setUser("teste-login");
        usuario.setSenha("12345");

        this.serviceUsuario.salvarUsuario(usuario);
        
        Usuario usuarioLogado = this.serviceUsuario.loginUser(usuario.getUser(), "senhaErrada");

        Assert.assertNull(usuarioLogado); 
}

    @Test
    public void naoDeveSalvarUsuarioSemNome() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@exemplo.com");
        usuario.setSenha("password");

        this.serviceUsuario.salvarUsuario(usuario); 
    }

}
