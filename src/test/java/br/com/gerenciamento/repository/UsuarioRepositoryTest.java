package br.com.gerenciamento.repository;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
	
	@Autowired
    private UsuarioRepository repositoryUsuario;
	
	@Test
	public void findByEmail() throws NoSuchAlgorithmException {
		Usuario user = new Usuario();
		user.setId(1L);
		user.setEmail("simon@hotmail.com");
		user.setUser("simon");
		user.setSenha(Util.md5("drill"));
		this.repositoryUsuario.save(user);
		
		Usuario userRetorno = this.repositoryUsuario.findByEmail("simon@hotmail.com");
		Assert.assertTrue(user.getEmail().equals(userRetorno.getEmail()));
	}
	
	@Test
	public void buscarLogin() throws NoSuchAlgorithmException {
		Usuario user = new Usuario();
		user.setId(1L);
		user.setEmail("seiya@hotmail.com");
		user.setUser("seiya");
		user.setSenha(Util.md5("pegasus"));
		this.repositoryUsuario.save(user);
		
		Usuario userRetorno = this.repositoryUsuario.buscarLogin("seiya", Util.md5("pegasus"));
		Assert.assertTrue(user.getEmail().equals(userRetorno.getEmail()));
	}
	
	@Test
	public void delete() throws NoSuchAlgorithmException {
		Usuario user = new Usuario();
		user.setId(1L);
		user.setEmail("lelouch@hotmail.com");
		user.setUser("lelouch");
		user.setSenha(Util.md5("geass"));
		this.repositoryUsuario.save(user);
		
		this.repositoryUsuario.delete(user);
		Usuario userRetorno = this.repositoryUsuario.buscarLogin("lelouch", Util.md5("geass"));
		
		Assert.assertNull(userRetorno);
	}
	
	@Test
	public void buscarLoginSemCriptografia() throws NoSuchAlgorithmException {
		Usuario user = new Usuario();
		user.setId(1L);
		user.setEmail("link@hotmail.com");
		user.setUser("link");
		user.setSenha(Util.md5("MasterSword"));
		this.repositoryUsuario.save(user);
		
		Usuario userRetorno = this.repositoryUsuario.buscarLogin("link", "MasterSword");
		
		Assert.assertNull(userRetorno);
	}
}
