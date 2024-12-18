package br.com.gerenciamento.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    private ServiceUsuario usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuarioComDadosValidos() {
        Usuario usuario = new Usuario();
        usuario.setNome("João Silva");
        usuario.setEmail("joao.silva@gmail.com");
        usuario.setSenha("senha123");

        Usuario salvo = usuarioService.salvarUsuario(usuario);

        Assert.assertNotNull(salvo);
        Assert.assertNotNull(salvo.getId());
        Assert.assertEquals("João Silva", salvo.getNome());
        Assert.assertEquals("joao.silva@gmail.com", salvo.getEmail());
    }

    @Test
    public void salvarUsuarioComEmailInvalido() {
        Usuario usuario = new Usuario();
        usuario.setNome("Ana Paula");
        usuario.setEmail("email-invalido"); // Email inválido
        usuario.setSenha("senhaSegura");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });
    }

    @Test
    public void salvarUsuarioComSenhaCurta() {
        Usuario usuario = new Usuario();
        usuario.setNome("Carlos Eduardo");
        usuario.setEmail("carlos@gmail.com");
        usuario.setSenha("12"); // Senha muito curta

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });
    }

    @Test
    public void autenticarUsuarioComCredenciaisValidas() {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria Clara");
        usuario.setEmail("maria@gmail.com");
        usuario.setSenha("senhaForte");
        usuarioService.salvarUsuario(usuario);

        Usuario autenticado = usuarioService.autenticarUsuario("maria@gmail.com", "senhaForte");

        Assert.assertNotNull(autenticado);
        Assert.assertEquals("Maria Clara", autenticado.getNome());
    }

    @Test
    public void autenticarUsuarioComSenhaErrada() {
        Usuario usuario = new Usuario();
        usuario.setNome("Pedro Henrique");
        usuario.setEmail("pedro@gmail.com");
        usuario.setSenha("senhaCorreta");
        usuarioService.salvarUsuario(usuario);

        Assert.assertThrows(RuntimeException.class, () -> {
            usuarioService.autenticarUsuario("pedro@gmail.com", "senhaErrada");
        });
    }

    @Test
    public void autenticarUsuarioNaoCadastrado() {
        Assert.assertThrows(RuntimeException.class, () -> {
            usuarioService.autenticarUsuario("naoexiste@gmail.com", "senha123");
        });
    }

    @Test
    public void excluirUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setNome("Luiz Fernando");
        usuario.setEmail("luiz@gmail.com");
        usuario.setSenha("senhaForte");
        Usuario salvo = usuarioService.salvarUsuario(usuario);

        usuarioService.excluirUsuario(salvo.getId());

        Assert.assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarPorId(salvo.getId());
        });
    }

    @Test
    public void excluirUsuarioInexistente() {
        Assert.assertThrows(RuntimeException.class, () -> {
            usuarioService.excluirUsuario(999L); // ID inexistente
        });
    }
}
