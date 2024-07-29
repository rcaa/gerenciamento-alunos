package br.com.gerenciamento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarComEmailExistente() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setEmail("teste@teste.com");
        usuario1.setSenha("1234");
        usuario1.setUser("Júlia");

        this.serviceUsuario.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setId(1L);
        usuario2.setEmail("teste@teste.com");
        usuario2.setSenha("1234");
        usuario2.setUser("Júlia");
        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });

    }

    @Test
    public void login() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setEmail("teste@teste.com");
        usuario1.setSenha("1234");
        usuario1.setUser("Júlia");

        this.serviceUsuario.salvarUsuario(usuario1);

        Usuario resultado = this.serviceUsuario.loginUser(usuario1.getUser(), usuario1.getSenha());

        Assert.assertEquals(usuario1, resultado);
    }

    @Test
    public void salvarComErroDeCriptografia() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("1234");
        usuario.setUser("Júlia");

        this.serviceUsuario.salvarUsuario(usuario);

        Util utilMock = mock(Util.class);
        when(utilMock.md5(usuario.getSenha())).thenThrow(new NoSuchAlgorithmException());

        Assert.assertThrows(CriptoExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });

    }

    @Test
    public void loginSemSenha() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("Julia");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("1234");

        this.serviceUsuario.salvarUsuario(usuario);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.loginUser(usuario.getUser(), null);
        });

    }

}
