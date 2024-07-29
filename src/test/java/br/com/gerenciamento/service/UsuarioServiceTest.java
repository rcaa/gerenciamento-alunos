package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.transaction.Transactional;

import java.security.NoSuchAlgorithmException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    @Transactional
    public void testSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("vinicius@email.com");
        usuario.setSenha("123456");
        usuario.setUser("ViniciusSilva");

        Assertions.assertDoesNotThrow(() -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    @Test
    @Transactional
    public void testSalvarUsuarioEmailExists() throws Exception {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        usuario1.setEmail("dora@email.com");
        usuario1.setSenha("654321");
        usuario1.setUser("DoraAdventurous");

        usuario2.setEmail("dora@email.com");
        usuario2.setSenha("754841");
        usuario2.setUser("Doroteia");

        this.serviceUsuario.salvarUsuario(usuario1);

        Assertions.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });
    }

    @Test
    @Transactional
    public void testLoginUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("anelise@email.com");
        usuario.setSenha("password");
        usuario.setUser("AneliseGenevieve");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = this.serviceUsuario.loginUser(usuario.getUser(), Util.md5("password"));

        Assertions.assertEquals(usuarioLogado.getId(), usuario.getId());
    }

    @Test
    @Transactional
    public void testLancarExcecaoCriptografada() {
        Usuario usuario = new Usuario();
        usuario.setEmail("anelise@email.com");
        usuario.setSenha("125457");
        usuario.setUser("AneliseGenevieve");

        try {
            this.serviceUsuario.salvarUsuario(usuario);
        } catch (NoSuchAlgorithmException e) {
            Assertions.assertEquals("Error na criptografia da senha", e.getMessage());
        } catch (Exception e) {
            Assertions.fail("Deveria ter lançado CriptoExistsException");
        }
    }
}
