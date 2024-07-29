package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private ServiceUsuario serviceUsuario;
    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Test
    public void buscarEmailVazio(){
        Usuario usuarioRet=usuarioRepository.findByEmail("inexistente");
        Assert.assertTrue(usuarioRet==null);
    }

    @Test
    public void buscarPorEmail() throws Exception {
        Usuario user= new Usuario();
        user.setId(1L);
        user.setUser("carla");
        user.setEmail("carla@gmail.com");
        user.setSenha("123456789");
        serviceUsuario.salvarUsuario(user);
        Usuario usuarioRet=usuarioRepository.findByEmail("carla@gmail.com");
        Assert.assertTrue(usuarioRet.getId()==1L);
    }

    @Test
    public void loginUser() throws Exception {
        Usuario user= new Usuario();
        user.setId(1L);
        user.setUser("carla");
        user.setEmail("carla@gmail.com");
        user.setSenha("123456789");
        serviceUsuario.salvarUsuario(user);
        Usuario usuarioRet=usuarioRepository.buscarLogin(user.getUser(), user.getSenha());
        Assert.assertTrue(usuarioRet.getId()==1L);
    }

    @Test
    public void loginSemUser() throws Exception {
        Usuario user= new Usuario();
        user.setId(1L);
        user.setUser("carla");
        user.setEmail("carla@gmail.com");
        user.setSenha("123456789");
        serviceUsuario.salvarUsuario(user);
        Usuario usuarioRet=usuarioRepository.buscarLogin("test", user.getSenha());
        Assert.assertTrue(usuarioRet==null);
    }

}
