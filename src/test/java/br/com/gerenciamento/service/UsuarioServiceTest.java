package br.com.gerenciamento.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

  @Autowired
  private ServiceUsuario serviceUsuario;

  @Test
  public void salvarUsuario() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setEmail("teste@email.com");
    usuario.setSenha("123456");
    usuario.setUser("teste");
    
    try {
          this.serviceUsuario.salvarUsuario(usuario);
        } catch (Exception e) {
            fail("A função lançou uma exceção: " + e.getMessage());
        }
  }

  @Test
  public void salvarUsuarioDuplicado() {
    Usuario usuario = new Usuario();
    usuario.setId(2L);
    usuario.setEmail("testededuplicidade@email.com");
    usuario.setSenha("123456");
    usuario.setUser("testededuplicidade");
    
    try {
      this.serviceUsuario.salvarUsuario(usuario);
    } catch (Exception e) {
      fail("A função lançou uma exceção: " + e.getMessage());
    }

    Usuario usuario2 = new Usuario();
    usuario2.setId(2L);
    usuario2.setEmail("testededuplicidade@email.com");
    usuario2.setSenha("123456");
    usuario2.setUser("testededuplicidade");
    
    Assert.assertThrows(EmailExistsException.class, () -> {
      this.serviceUsuario.salvarUsuario(usuario2);
    });
  }

  @Test
  public void loginUser() {
    Usuario usuario = new Usuario();
    usuario.setId(3L);
    usuario.setEmail("testeLogin@email.com");
    usuario.setSenha("654321");
    usuario.setUser("testeLogin");
    
    try {
      this.serviceUsuario.salvarUsuario(usuario);
    } catch (Exception e) {
      fail("A função lançou uma exceção: " + e.getMessage());
    }
    
    try {
      Usuario usuarioSalvo = this.serviceUsuario.loginUser("testeLogin", Util.md5("654321"));
      Assert.assertEquals("testeLogin@email.com", usuarioSalvo.getEmail());
    } catch (Exception e) {
      fail("A função lançou uma exceção: " + e.getMessage());
    }
  }
  
  @Test
  public void loginSemUserSalvo() {
    Usuario usuario = new Usuario();
    usuario.setId(4L);
    usuario.setEmail("testeLoginSemUserSalvo@email.com");
    usuario.setSenha("6543210");
    usuario.setUser("testeLoginSemUserSalvo");
    
    try {
      Usuario usuarioSalvo = this.serviceUsuario.loginUser("testeLoginSemUserSalvo", Util.md5("6543210"));
      Assert.assertNull(usuarioSalvo);
    } catch (Exception e) {
      fail("A função lançou uma exceção: " + e.getMessage());
    }
  }
}
