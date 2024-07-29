package br.com.gerenciamento.repository;

import java.util.List;

// import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
	private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    @Before
	public void UsuarioParaTestes() {
		usuario = new Usuario();
        usuario.setId(7L);
		usuario.setEmail("rafao@gmail.com");
        usuario.setUser("Rafael");
		usuario.setSenha("1122");
	}



    @Test
    @Transactional
	public void findByEmail() {
        this.usuarioRepository.save(usuario);
		String userTemp = this.usuario.getUser();
		String emailTemp = this.usuario.getEmail();

		Usuario usuario = usuarioRepository.findByEmail(emailTemp);

		Assert.assertEquals(userTemp, usuario.getUser());
		Assert.assertEquals(emailTemp, usuario.getEmail());
	}

   
	@Test
    @Transactional
	public void findByEmailNotFound() {
        this.usuarioRepository.save(usuario);
		String emailTemp = "Josezinho@gmail.com";

		Usuario usuario = usuarioRepository.findByEmail(emailTemp);

		Assert.assertNull(usuario);
	}

    @Test
    @Transactional
    public void LoginIncorrectCredentials() {
        this.usuarioRepository.save(usuario);
		String user = "Errado";
		String senha = "Senhaerrada";

		Usuario usuario = usuarioRepository.buscarLogin(user, senha);

		Assert.assertNull(usuario);
	}

    @Test
    @Transactional
    public void FindAll() {
        this.usuarioRepository.save(usuario);
    
        List<Usuario> TodosUsuarios = this.usuarioRepository.findAll();

        Assert.assertEquals(1, TodosUsuarios.size());
        Assert.assertEquals("Rafael", TodosUsuarios.get(0).getUser());
    }
}
