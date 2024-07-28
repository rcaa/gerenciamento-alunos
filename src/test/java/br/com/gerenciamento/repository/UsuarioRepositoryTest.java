package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private Usuario usuario;

	@Before
	public void setup() {
		usuario = new Usuario();
		usuario.setUser("Pedro");
		usuario.setEmail("pedro@email.com");
		usuario.setSenha("1234");

		usuarioRepository.save(usuario);
	}

	@After
	public void teardown() {
		usuarioRepository.deleteAll();
	}

	@Test
	public void findByEmail() {
		String user = this.usuario.getUser();
		String email = this.usuario.getEmail();

		Usuario usuario = usuarioRepository.findByEmail(email);

		Assert.assertEquals(user, usuario.getUser());
		Assert.assertEquals(email, usuario.getEmail());
	}

	@Test
	public void buscarLogin() {
		String user = this.usuario.getUser();
		String senha = this.usuario.getSenha();

		Usuario usuario = usuarioRepository.buscarLogin(user, senha);

		Assert.assertEquals(user, usuario.getUser());
		Assert.assertEquals(senha, usuario.getSenha());
	}

	@Test
	public void buscarLoginIncorrectCredentials() {
		String user = "Lucas";
		String senha = "abcd";

		Usuario usuario = usuarioRepository.buscarLogin(user, senha);

		Assert.assertNull(usuario);
	}

	@Test
	public void findByEmailNotFound() {
		String email = "unknown@email.com";

		Usuario usuario = usuarioRepository.findByEmail(email);

		Assert.assertNull(usuario);
	}
}
