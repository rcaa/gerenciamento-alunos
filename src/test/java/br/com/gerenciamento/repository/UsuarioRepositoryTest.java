package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Before;
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

    @Before
    public void setUp() {
        // Limpa o banco de dados antes de cada teste
        usuarioRepository.deleteAll();
    }

    @Test
    public void testFindByEmail() {
        // Cria e salva um usuário
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        // Busca o usuário pelo email
        Usuario usuarioRetornado = usuarioRepository.findByEmail("teste@example.com");

        // Verifica se o usuário foi encontrado corretamente
        Assert.assertNotNull(usuarioRetornado);
        Assert.assertEquals("usuarioTeste", usuarioRetornado.getUser());
    }

    @Test
    public void testFindByEmailNaoEncontrado() {
        // Tenta buscar um usuário com um email que não existe
        Usuario usuarioRetornado = usuarioRepository.findByEmail("naoexistente@example.com");

        // Verifica se o retorno é null
        Assert.assertNull(usuarioRetornado);
    }

    @Test
    public void testBuscarLoginValido() {
        // Cria e salva um usuário com credenciais corretas
        Usuario usuario = new Usuario();
        usuario.setUser("usuarioValido");
        usuario.setSenha("senhaCriptografada");
        usuarioRepository.save(usuario);

        // Busca o usuário pelo nome de usuário e senha
        Usuario usuarioRetornado = usuarioRepository.buscarLogin("usuarioValido", "senhaCriptografada");

        // Verifica se o usuário foi encontrado corretamente
        Assert.assertNotNull(usuarioRetornado);
        Assert.assertEquals("usuarioValido", usuarioRetornado.getUser());
    }

    @Test
    public void testBuscarLoginInvalido() {
        // Cria e salva um usuário com credenciais corretas
        Usuario usuario = new Usuario();
        usuario.setUser("usuarioValido");
        usuario.setSenha("senhaCriptografada");
        usuarioRepository.save(usuario);

        // Tenta buscar o usuário com credenciais incorretas
        Usuario usuarioRetornado = usuarioRepository.buscarLogin("usuarioValido", "senhaIncorreta");

        // Verifica se o retorno é null
        Assert.assertNull(usuarioRetornado);
    }
}
