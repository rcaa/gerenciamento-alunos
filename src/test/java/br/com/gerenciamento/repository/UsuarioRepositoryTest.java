package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Before
  public void setUp() {
    usuarioRepository.deleteAll();

    Usuario usuario1 = new Usuario();
    usuario1.setUser("usuario1");
    usuario1.setEmail("usuario1@email.com");
    usuario1.setSenha("123456");
    usuarioRepository.save(usuario1);
    
    Usuario usuario2 = new Usuario();
    usuario2.setUser("usuario2");
    usuario2.setEmail("usuario2@email.com");
    usuario2.setSenha("123456");
    usuarioRepository.save(usuario2);

    Usuario usuario3 = new Usuario();
    usuario3.setUser("usuario3");
    usuario3.setEmail("usuario3@email.com");
    usuario3.setSenha("123456");
    usuarioRepository.save(usuario3);
    
    Usuario usuario4 = new Usuario();
    usuario4.setUser("usuario4");
    usuario4.setEmail("usuario4@email.com");
    usuario4.setSenha("123456");
    usuarioRepository.save(usuario4);
    
    Usuario usuario5 = new Usuario();
    usuario5.setUser("usuario5");
    usuario5.setEmail("usuario5@email.com");
    usuario5.setSenha("123456");
    usuarioRepository.save(usuario5);
  }

  @Test
  public void testeFindByEmail() {
    Usuario usuario = usuarioRepository.findByEmail("usuario3@email.com");
    Assert.assertNotNull(usuario);
  }
  
  @Test
  public void testeNotFoundByEmail() {
    Usuario usuario = usuarioRepository.findByEmail("usuario10@email.com");
    Assert.assertNull(usuario);
  }

  @Test
  public void testeLogin() {
    Usuario usuario = usuarioRepository.buscarLogin("usuario2", "123456");
    Assert.assertNotNull(usuario);
  }
  
  @Test
  public void testeNotLogin() {
    Usuario usuario = usuarioRepository.buscarLogin("usuario2", "123456789");
    Assert.assertNull(usuario);
  }

}
