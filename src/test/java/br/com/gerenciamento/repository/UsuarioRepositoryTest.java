package br.com.gerenciamento.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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

    private Usuario user;
    
    @BeforeEach
    public void setUp(){
        user = new Usuario();
        user.setEmail("test@test.com");
        user.setUser("Teste");
        user.setSenha("123456789");
        user.setId(6L);

        usuarioRepository.save(user);
    }

    @Test
    public void findByEmail(){
        assertEquals(user, usuarioRepository.findByEmail("test@test.com"));
    }

    @Test
    public void buscarLogin(){
          assertEquals(user, usuarioRepository.buscarLogin("Teste", "123456789"));
    }

    @Test
    public void findByEmailNaoExistente(){
        assertNull(usuarioRepository.findByEmail("testnaoexistente@test.com"));
    }

    @Test
    public void buscarLoginInvalido(){
        assertNull(usuarioRepository.buscarLogin("Testef2", "123456789"));
    }
}