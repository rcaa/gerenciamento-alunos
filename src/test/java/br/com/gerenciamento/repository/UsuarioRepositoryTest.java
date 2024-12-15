package br.com.gerenciamento.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

    @Test
    public void TesteBuscarUsuarioPorEmail() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur1@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        usuarioRepository.save(u);

        Usuario retorno = usuarioRepository.findByEmail("arthur1@gmail.com");
        assertEquals("ruhtra", retorno.getUser());
        assertEquals("123", retorno.getSenha());
    }

    @Test
    public void TesteBuscarUsuarioPorEmailNaoCadastrado() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur1@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        usuarioRepository.save(u);
        
        Usuario retorno = usuarioRepository.findByEmail("marlon777@gmail.com");
        assertNull(retorno);
    }
    
    @Test
    public void TesteBuscarUsuarioPorLogin() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur1@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        usuarioRepository.save(u);
        
        Usuario retorno = usuarioRepository.buscarLogin("ruhtra", "123");
        assertEquals("arthur1@gmail.com", retorno.getEmail());
    }

    @Test
    public void TesteBuscarUsuarioPorLoginComSenhaIncorreta() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setEmail("arthur1@gmail.com");
        u.setUser("ruhtra");
        u.setSenha("123");

        usuarioRepository.save(u);

        Usuario retorno = usuarioRepository.buscarLogin("ruhtra", "1234");
        assertNull(retorno);
    }
}
