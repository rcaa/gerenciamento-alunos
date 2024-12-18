package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Test
    public void registrarUsuarioComSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("João");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("123456");
        
        serviceUsuario.salvarUsuario(usuario);

        Usuario salvo = serviceUsuario.getByEmail(usuario.getEmail());        
        
        Assert.assertNotNull(salvo.getId());
        Assert.assertEquals("João", salvo.getUser());
    }

    @Test
    public void registrarUsuarioSemEmail() {
        Usuario usuario = new Usuario();
        usuario.setUser("João");
        usuario.setSenha("123456");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            try {
                serviceUsuario.salvarUsuario(usuario);
            } catch (Exception ex) {
                Logger.getLogger(UsuarioServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Test
    public void naoRegistrarUsuarioComEmailDuplicado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("João Pereira");
        usuario.setEmail("joao.pereira@example.com");
        usuario.setSenha("senha123");

        serviceUsuario.salvarUsuario(usuario);

        Usuario duplicado = new Usuario();
        duplicado.setUser("João Pereira");
        duplicado.setEmail("joao.pereira@example.com");
        duplicado.setSenha("senha456");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceUsuario.salvarUsuario(duplicado);
        });
    }

    @Test
    public void naoRegistrarUsuarioSemSenha() {
        Usuario usuario = new Usuario();
        usuario.setUser("Ana Paula");
        usuario.setEmail("ana.paula@example.com");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });
    }
    
}
