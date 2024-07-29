package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
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
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Test
    public void getByID() throws Exception {
        Usuario user= new Usuario();
        user.setId(1L);
        user.setUser("carla");
        user.setEmail("carla@gmail.com");
        user.setSenha("123456789");
        serviceUsuario.salvarUsuario(user);
        Usuario userRetorno=usuarioRepository.buscarLogin(user.getUser(), user.getSenha());
        Assert.assertTrue(userRetorno.getUser().equals("carla"));
    }
    @Test
    public void salvarComEmailRepetido() throws Exception {
        Usuario user= new Usuario();
        user.setId(1L);
        user.setUser("carla");
        user.setEmail("carla@gmail.com");
        user.setSenha("123456789");
        serviceUsuario.salvarUsuario(user);
        Usuario user2= new Usuario();
        user2.setId(2L);
        user2.setUser("dani");
        user2.setEmail("carla@gmail.com");
        user2.setSenha("123456789");
        Assert.assertThrows(EmailExistsException.class,()->{
            serviceUsuario.salvarUsuario(user2);
        });
    }
    @Test
    public void senhaVazia() throws Exception {
        Usuario user= new Usuario();
        user.setId(1L);
        user.setUser("carla");
        user.setEmail("carla@gmail.com");
        Assert.assertThrows(NullPointerException.class,()->{
            serviceUsuario.salvarUsuario(user);
        });
    }
    @Test
    public void emailVazio() throws Exception {
        Usuario user= new Usuario();
        user.setId(1L);
        user.setUser("carla");
        user.setSenha("123456789");
        serviceUsuario.salvarUsuario(user);
        Usuario userRetorno=usuarioRepository.buscarLogin(user.getUser(), user.getSenha());
        Assert.assertTrue(userRetorno.getUser().equals("carla"));
    }
}
