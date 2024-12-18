package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testBuscarLoginComCredenciaisValidas() {
        usuarioRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setUser("leo123");
        usuario.setEmail("leo.pereira@example.com");
        usuario.setSenha("senhaSegura");
        usuarioRepository.save(usuario);

        Usuario resultado = usuarioRepository.buscarLogin("leo123", "senhaSegura");

        Assert.assertNotNull("Login falhou com credenciais válidas.", resultado);
        Assert.assertEquals("leo.pereira@example.com", resultado.getEmail());
    }

    @Test
    public void testBuscarLoginComCredenciaisInvalidas() {
        usuarioRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setUser("bruno123");
        usuario.setEmail("bruno.henrique@example.com");
        usuario.setSenha("senhaCorreta");
        usuarioRepository.save(usuario);

        Usuario resultado = usuarioRepository.buscarLogin("bruno123", "senhaErrada");

        Assert.assertNull("Login não deveria retornar resultado para credenciais inválidas.", resultado);
    }

    @Test
    public void testFindByEmail() {
        usuarioRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setUser("matheus123");
        usuario.setEmail("matheus.cunha@example.com");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        Usuario resultado = usuarioRepository.findByEmail("matheus.cunha@example.com");

        Assert.assertNotNull("Usuário não encontrado pelo email.", resultado);
        Assert.assertEquals("matheus123", resultado.getUser());
        Assert.assertEquals("senha123", resultado.getSenha());
    }

    @Test
    public void testSaveAndFindUsuariosIndividually() {
        usuarioRepository.deleteAll();

        // Criando e salvando os usuários
        Usuario usuario1 = new Usuario();
        usuario1.setUser("lucas123");
        usuario1.setEmail("lucas.paqueta@example.com");
        usuario1.setSenha("senhaLucas");

        Usuario usuario2 = new Usuario();
        usuario2.setUser("pedro456");
        usuario2.setEmail("pedro.guilherme@example.com");
        usuario2.setSenha("senhaPedro");

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        // Verificando se os usuários foram salvos corretamente
        Usuario resultado1 = usuarioRepository.findByEmail("lucas.paqueta@example.com");
        Usuario resultado2 = usuarioRepository.findByEmail("pedro.guilherme@example.com");

        Assert.assertNotNull("Usuário 1 não encontrado no banco.", resultado1);
        Assert.assertEquals("Usuário 1 com dados incorretos.", "lucas123", resultado1.getUser());
        Assert.assertEquals("Senha do usuário 1 incorreta.", "senhaLucas", resultado1.getSenha());

        Assert.assertNotNull("Usuário 2 não encontrado no banco.", resultado2);
        Assert.assertEquals("Usuário 2 com dados incorretos.", "pedro456", resultado2.getUser());
        Assert.assertEquals("Senha do usuário 2 incorreta.", "senhaPedro", resultado2.getSenha());
    }
}
