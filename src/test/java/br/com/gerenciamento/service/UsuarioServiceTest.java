package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
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
    public void criarUsuario() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("testando");
        usuario.setSenha("aaa123");

        Usuario usuarioSalvo = serviceUsuario.salvarUsuario(usuario);

        Assert.assertTrue(usuarioSalvo.getId().equals(1L));
    }

    @Test
    public void salvarSemEmail() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUser("testando");
        usuario.setSenha("aaa123");

        Usuario usuarioSalvo = serviceUsuario.salvarUsuario(usuario);

        Assert.assertTrue(usuarioSalvo.getId().equals(2L));
    }

    @Test
    public void salvarSemUser() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setEmail("teste@exemplo2.com");
        usuario.setSenha("aaa123");

        Usuario usuarioSalvo = serviceUsuario.salvarUsuario(usuario);

        Assert.assertTrue(usuarioSalvo.getId().equals(3L));
    }

    @Test
    public void salvarSemId() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo3.com");
        usuario.setUser("testando2");
        usuario.setSenha("aaa123");

        Usuario usuarioSalvo = serviceUsuario.salvarUsuario(usuario);

        Assert.assertTrue(usuarioSalvo.getUser().equals("testando2"));
    }

}
