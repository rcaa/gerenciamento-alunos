package br.com.gerenciamento.repository;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.gerenciamento.model.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest
{
	@Autowired
    private UsuarioRepository UsuarioRepository;

	@Test
	@Transactional
    public void save()
	{
		Usuario usuario = new Usuario();
		usuario.setEmail("aaaaa@a.a");
        usuario.setUser("aaaaa");
        usuario.setSenha("aaaaa");
		this.UsuarioRepository.save(usuario);
		List<Usuario> usuarios = this.UsuarioRepository.findAll();
		Assert.assertTrue(usuarios.get(0).getUser().equals("aaaaa"));
	}

	@Test
	@Transactional
    public void saveInvalidEmail()
	{
		Usuario usuario = new Usuario();
		usuario.setEmail("aaaaa");
        usuario.setUser("aaaaa");
        usuario.setSenha("aaaaa");
		Assert.assertThrows(ConstraintViolationException.class, () -> {this.UsuarioRepository.save(usuario);});
	}

	@Test
	@Transactional
    public void saveInvalidUser()
	{
		Usuario usuario = new Usuario();
		usuario.setEmail("aaaaa@.a");
        usuario.setUser("a");
        usuario.setSenha("aaaaa");
		Assert.assertThrows(ConstraintViolationException.class, () -> {this.UsuarioRepository.save(usuario);});
	}

	@Test
	@Transactional
    public void buscarLogin()
	{
		Usuario usuario = new Usuario();
		usuario.setEmail("aaaaa@a.a");
        usuario.setUser("aaaaa");
        usuario.setSenha("aaaaa");
		this.UsuarioRepository.save(usuario);
		Usuario usuarioAchado = this.UsuarioRepository.buscarLogin("aaaaa", "aaaaa");
		Assert.assertTrue(usuarioAchado.getEmail().equals("aaaaa@a.a"));
	}
}