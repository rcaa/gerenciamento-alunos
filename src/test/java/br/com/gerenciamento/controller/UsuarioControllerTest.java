package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    UsuarioController usuarioController;

    @Test
    public void login(){
        ModelAndView modelAndView= usuarioController.login();
        Assert.assertTrue(modelAndView.getViewName().equals("login/login"));
    }

    @Test
    public void index(){
        ModelAndView modelAndView= usuarioController.index();
        Assert.assertTrue(modelAndView.getViewName().equals("home/index"));
    }

    @Test
    public void cadastrar(){
        ModelAndView modelAndView= usuarioController.cadastrar();
        Assert.assertTrue(modelAndView.getViewName().equals("login/cadastro"));
    }

    @Test
    public void cadastrarUsuario() throws Exception {
        Usuario user= new Usuario();
        user.setId(1L);
        user.setUser("carla");
        user.setEmail("carla@gmail.com");
        user.setSenha("123456789");
        ModelAndView modelAndView= usuarioController.cadastrar(user);
        Assert.assertTrue(modelAndView.getViewName().equals("redirect:/"));
    }
}
