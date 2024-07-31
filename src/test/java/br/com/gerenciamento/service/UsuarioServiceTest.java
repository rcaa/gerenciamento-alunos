package br.com.gerenciamento.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Mock
    private ServiceUsuario serviceUsuario;
    
    @Test
    public void salvarUsuario() throws Exception{
        Usuario user = new Usuario();
        user.setEmail("test@gmail.com");
        user.setUser("Teste2");
        user.setSenha("123456789");

        doNothing().when(serviceUsuario).salvarUsuario(user);

        serviceUsuario.salvarUsuario(user);

        verify(serviceUsuario, times(1)).salvarUsuario(user);
    }

    @Test
    public void salvarUsuarioComEmailExistente() throws Exception{
        Usuario user = new Usuario();
        user.setEmail("test@gmail.com");
        user.setUser("Teste2");
        user.setSenha("123456789");

        doThrow(new RuntimeException("Email already exists")).when(serviceUsuario).salvarUsuario(user);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            serviceUsuario.salvarUsuario(user);
        });

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    public void loginUser() throws Exception{
        Usuario user = new Usuario();
        user.setEmail("test@gmail.com");
        user.setUser("Teste2");
        user.setSenha("123456789");

        serviceUsuario.salvarUsuario(user);

        when(serviceUsuario.loginUser("Teste2", "123456789")).thenReturn(user);

        Usuario result = serviceUsuario.loginUser("Teste2", "123456789");
        assertEquals(user, result);
    }

    @Test
    public void loginUserComSenhaErrada() throws Exception{
        Usuario user = new Usuario();
        user.setEmail("test2@gmail.com");
        user.setUser("Teste2");
        user.setSenha("123456789");

        serviceUsuario.salvarUsuario(user);
        
        when(serviceUsuario.loginUser("Teste2", "senhaErrada")).thenReturn(null);

        Usuario result = serviceUsuario.loginUser("Teste3", "senhaErrada");
        assertNull(result);
    }
}