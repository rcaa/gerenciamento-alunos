package br.com.gerenciamento.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;
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
    public void TesteSalvarUsuario () {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur1@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        assertDoesNotThrow(() -> {
            serviceUsuario.salvarUsuario(u);
        });
    }
    
    @Test
    public void TesteSalvarUsuarioComEmailDuplicado () throws Exception {
        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setEmail("arthur2@gmail.com");
        u1.setUser("ruhtra");
        u1.setSenha("123");

        Usuario u2 = new Usuario();
        u2.setId(1L);
        u2.setEmail("arthur2@gmail.com");
        u2.setUser("fulano");
        u2.setSenha("999");

        serviceUsuario.salvarUsuario(u1);
        assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(u2);
        });
    }
    
    @Test
    public void TesteBuscarUsuarioPorLogin () throws Exception {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur3@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");
        
        serviceUsuario.salvarUsuario(u);

        //Por razão que eu desconheço, o método loginUser, que apenas encapsula
        //o método buscarLogin, não está retornando o usuário récem criado.

        //E o método buscarLogin já foi testado e está funcionando corretamente.
        Usuario retorno = serviceUsuario.loginUser("ruhtra", "123");
        assertEquals("arthur3@gmail.com", retorno.getEmail());
    }

    @Test
    public void TesteBuscarUsuarioPorLoginComUsernameIncorreto () throws Exception {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur4@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        serviceUsuario.salvarUsuario(u);

        Usuario retorno = serviceUsuario.loginUser("ruta", "123");
        assertNull(retorno);
    }
}
