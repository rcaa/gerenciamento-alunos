package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UsuarioServiceTest {

	@Autowired
	private ServiceUsuario serviceUsuario;

	private Usuario usuario;

	@Before
	public void setup() {
		usuario = new Usuario();
		usuario.setUser("Pedro");
		usuario.setEmail("pedro@email.com");
		usuario.setSenha("1234");
	}

	@Test
	public void salvarUsuarioComEmailJaCadastrado() throws Exception {
		this.serviceUsuario.salvarUsuario(usuario);
		
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUser("Lucas");
		novoUsuario.setEmail(usuario.getEmail());
		novoUsuario.setSenha("1234");

		Assert.assertThrows(EmailExistsException.class, () -> {
			this.serviceUsuario.salvarUsuario(novoUsuario);
		});
	}

	@Test
	public void salvarUsuarioComNomeInvalido() throws Exception {
		usuario.setUser("P");

		Assert.assertThrows(ConstraintViolationException.class, () -> {
			this.serviceUsuario.salvarUsuario(usuario);
		});
	}

	@Test
	public void loginUsuario() throws Exception {
		this.serviceUsuario.salvarUsuario(usuario);

		Usuario usuarioLogado = this.serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());

		Assert.assertEquals("Pedro", usuarioLogado.getUser());
		Assert.assertEquals("pedro@email.com", usuarioLogado.getEmail());
	}

	@Test
	public void loginUsuarioNaoCadastrado() throws Exception {
		String user = "Fulano";
		String senha = "1234";

		Usuario resultado = this.serviceUsuario.loginUser(user, senha);

		Assert.assertNull(resultado);
	}
}
