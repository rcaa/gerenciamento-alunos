package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	public void salvarUsuario() throws Exception {
		Usuario user = new Usuario();
		user.setId(1L);
		user.setEmail("simon@hotmail.com");
		user.setUser("simon");
		user.setSenha("drill");
		this.serviceUsuario.salvarUsuario(user);
		
		Usuario userRetorno = this.serviceUsuario.loginUser("simon", Util.md5("drill"));
		Assert.assertTrue(user.getEmail().equals(userRetorno.getEmail()));
	}
	
	@Test
	public void salvarEmailIgual() {
		Usuario userA = new Usuario();
		userA.setId(1L);
		userA.setEmail("lelouch@hotmail.com");
		userA.setUser("lelouch");
		userA.setSenha("123");
		try {
			this.serviceUsuario.salvarUsuario(userA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Usuario userB = new Usuario();
		userB.setId(2L);
		userB.setEmail("lelouch@hotmail.com");
		userB.setUser("zero");
		userB.setSenha("orange");
		
		Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(userB);});
	}
	
	@Test
	public void logarSemCriptografia() throws Exception {
		Usuario user = new Usuario();
		user.setId(1L);
		user.setEmail("seiya@hotmail.com");
		user.setUser("seiya");
		user.setSenha("pegasus");
		this.serviceUsuario.salvarUsuario(user);
		
		Usuario userRetorno = this.serviceUsuario.loginUser("seiya", "pegasus");
		Assert.assertNull(userRetorno);
	}
	
	@Test
	public void senhaCaseSensitivity() throws Exception {
		Usuario user = new Usuario();
		user.setId(1L);
		user.setEmail("link@hotmail.com");
		user.setUser("link");
		user.setSenha("MasterSword");
		this.serviceUsuario.salvarUsuario(user);
		
		Usuario userRetorno = this.serviceUsuario.loginUser("seiya", Util.md5("mastersword"));
		Assert.assertNull(userRetorno);
	}
}
