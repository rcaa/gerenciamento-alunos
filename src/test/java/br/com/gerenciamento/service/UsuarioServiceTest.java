package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("Admin");
        usuario.setEmail("admin@example.com");
        usuario.setSenha("senha123");
        this.serviceUsuario.save(usuario);

        Usuario usuarioSalvo = this.serviceUsuario.getById(1L);
        Assert.assertTrue(usuarioSalvo.getUser().equals("Admin"));
    }

    @Test
    public void salvarSemEmail() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUser("Usuario Sem Email");
        usuario.setSenha("senha123");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.save(usuario);
        });
    }

    @Test
    public void atualizarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setUser("Usuario Atualizar");
        usuario.setEmail("usuario.atualizar@example.com");
        usuario.setSenha("senha123");
        this.serviceUsuario.save(usuario);

        usuario.setUser("Usuario Atualizado");
        this.serviceUsuario.save(usuario);

        Usuario usuarioAtualizado = this.serviceUsuario.getById(3L);
        Assert.assertTrue(usuarioAtualizado.getUser().equals("Usuario Atualizado"));
    }

    @Test
    public void deletarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(4L);
        usuario.setUser("Usuario Deletar");
        usuario.setEmail("usuario.deletar@example.com");
        usuario.setSenha("senha123");
        this.serviceUsuario.save(usuario);

        this.serviceUsuario.delete(4L);

        Usuario usuarioDeletado = this.serviceUsuario.getById(4L);
        Assert.assertNull(usuarioDeletado);
    }
}
