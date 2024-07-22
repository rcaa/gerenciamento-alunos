package br.com.gerenciamento.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import br.com.gerenciamento.exception.EmailExistsException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest
{
	@Autowired
    private ServiceUsuario serviceUsuario;

	@Test
	@Transactional
    public void salvarUsuario()
	{
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setEmail("aaaaa@a.a");
		usuario.setUser("aaaaa");
		usuario.setSenha("aaaaa");
		assertDoesNotThrow(() -> {this.serviceUsuario.salvarUsuario(usuario);});
	}

	@Test
	@Transactional
    public void salvarUsuarioEmailExists() throws Exception
	{
		Usuario usuario1 = new Usuario();
		Usuario usuario2 = new Usuario();
		usuario1.setId(1L);
		usuario1.setEmail("aaaaa@a.a");
		usuario1.setUser("aaaaa");
		usuario1.setSenha("aaaaa");
		usuario2.setId(2L);
		usuario2.setEmail("aaaaa@a.a");
		usuario2.setUser("bbbbb");
		usuario2.setSenha("bbbbb");
		this.serviceUsuario.salvarUsuario(usuario1);
		Assert.assertThrows(EmailExistsException.class, () -> {this.serviceUsuario.salvarUsuario(usuario2);});
	}

	@Test
	@Transactional
    public void salvarUsuarioInvalidName()
	{
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setEmail("aaaaa@a.a");
		usuario.setUser("a");
		usuario.setSenha("aaaaa");
		Assert.assertThrows(ConstraintViolationException.class, () -> {this.serviceUsuario.salvarUsuario(usuario);});
	}

	@Test
	@Transactional
    public void loginUser() throws Exception
	{
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setEmail("aaaaa@a.a");
		usuario.setUser("aaaaa");
		usuario.setSenha("aaaaa");
		this.serviceUsuario.salvarUsuario(usuario);
		Usuario usuarioAchado = serviceUsuario.loginUser("aaaaa", Util.md5("aaaaa"));
		Assert.assertTrue(usuarioAchado.getEmail().equals("aaaaa@a.a"));
	}
}