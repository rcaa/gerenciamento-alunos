package br.com.gerenciamento.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    @Transactional
    public void emailJaExistente(){
        Usuario usuario = new Usuario();
        usuario.setEmail("pedro@gmail.com");
        usuario.setUser("pedro");
        usuario.setSenha("123456");
        usuario.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("pedro@gmail.com");
        usuario2.setUser("hiago");  
        usuario2.setSenha("123456");
        usuario2.setId(2L);

        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
            this.serviceUsuario.salvarUsuario(usuario2);});
       
    }
    @Test
    @Transactional
    public void emailNaoCadastrado(){

        Assert.assertNull("Null retornado",serviceUsuario.loginUser("pedro", "123456"));
    }

    @Test
    @Transactional
    public void nomeUserCurto(){
        Usuario usuario = new Usuario();
        usuario.setEmail("pedro@gmail.com");
        usuario.setUser("Ph");
        usuario.setSenha("123456");
        usuario.setId(1L);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
          this.serviceUsuario.salvarUsuario(usuario);});
    }

    @Test
    @Transactional
    public void nomeUserLongo() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setEmail("pedro@gmail.com");
        usuario.setUser("Maximiliano Rodriguez Alvarez Fernandez Santos");
        usuario.setSenha("123456");
        usuario.setId(1L);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
          this.serviceUsuario.salvarUsuario(usuario);});
    }
}
