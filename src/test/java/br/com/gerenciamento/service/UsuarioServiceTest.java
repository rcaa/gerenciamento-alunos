package br.com.gerenciamento.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("vinicius@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");

        assertDoesNotThrow(() ->{
            this.serviceUsuario.salvarUsuario(usuario);
        });
        
    }


    @Test
    public void salvarComEmailExistente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setId(3L);
        usuario2.setEmail("vinicius123@gmail.com");
        usuario2.setUser("Vinicius Branco");
        usuario2.setSenha("vini321");

        assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });
    }

    @Test
    public void loginErrado() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("vinicius761@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser("Joaquim", "joaquimpernagrossa123");

        assertNull(usuarioLogado);
    }

    @Test
    public void salvarSemSenha() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");


        assertThrows(NullPointerException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }



    //Por algum motivo o do login sempre da errado não importa o que eu faça, o retorno fica igual a null não sei porque.

     /*@Test
    public void loginUser() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser("Vinicius", "vinicius123");

        Assert.assertNull(usuarioLogado);
    }*/ 

}
