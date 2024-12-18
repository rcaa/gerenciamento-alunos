package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private void criarUsuarios() {
        usuarioRepository.deleteAll();

        Usuario usuario00 = new Usuario();
        usuario00.setUser("Usuario00");
        usuario00.setEmail("test@email.com");
        usuario00.setSenha("password");

        this.usuarioRepository.save(usuario00);

        Usuario usuario01 = new Usuario();
        usuario01.setUser("Usuario01");
        usuario01.setEmail("test01@email.com");
        usuario01.setSenha("password01");

        this.usuarioRepository.save(usuario01);
    }

    /**
     * Teste para verificar se findByEmail retorna o usuário correto.
     */
    @Test
    public void buscarPorEmail() {
        criarUsuarios();
        Usuario usuario = this.usuarioRepository.findByEmail("test@email.com");
        assertThat(usuario).isNotNull();
        Assert.assertEquals("Usuario00", usuario.getUser());
    }

    /**
     * Teste para verificar se findByEmail retorna null quando o email não existe.
     */
    @Test
    public void buscarPorEmailInexistente() {
        criarUsuarios();
        Usuario email = this.usuarioRepository.findByEmail("inexistente@email.com");
        assertThat(email).isNull();
    }

    /**
     * Teste para verificar se buscarLogin retorna o usuário correto.
     */
    @Test
    public void buscarPorLogin() {
        criarUsuarios();
        Usuario usuario = this.usuarioRepository.buscarLogin("Usuario00", "password");
        assertThat(usuario).isNotNull();
    }

    /**
     * Teste para verificar se buscarLogin retorna null quando o login não existe.
     */
    @Test
    public void buscarPorLoginIncorreto() {
        criarUsuarios();
        Usuario usuario = this.usuarioRepository.buscarLogin("Usuario00", "senhaIncorreta");
        assertThat(usuario).isNull();
    }
}
