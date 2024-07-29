package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.transaction.Transactional;


// import java.util.ArrayList;
// import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
	private ServiceUsuario serviceUsuario;

	private Usuario usuario;

    @Before
	public void UsuarioParaTestes() {
		usuario = new Usuario();
		usuario.setEmail("rafao@gmail.com");
        usuario.setUser("Rafael");
		usuario.setSenha("1122");
	}


    // _________________________Novos Testes_____________________________
    @Test
    @Transactional
    public void loginUsuario() throws Exception {
		this.serviceUsuario.salvarUsuario(usuario);

		Usuario usuarioTemp = this.serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());

		Assert.assertEquals("rafao@gmail.com", usuarioTemp.getEmail());
		Assert.assertEquals("Rafael", usuarioTemp.getUser());
	}

    @Test
    @Transactional
    public void salvarUsuario() throws Exception {
        serviceUsuario.salvarUsuario(usuario);
        Assert.assertTrue(usuario.getUser().equals("Rafael"));
    }

    @Test
    @Transactional
    public void NovoUsuárioComEmailRepetido() throws Exception {

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("rafao@gmail.com");
        usuarioExistente.setUser("Katarinny"); 
        usuarioExistente.setSenha("123456789"); 


        try {
            this.serviceUsuario.salvarUsuario(usuarioExistente);
        } catch (EmailExistsException e) {
            Assert.assertEquals("Este email já está cadastrado: rafao@gmail.com", e.getMessage());
        }
    }

    @Test
    @Transactional
	public void loginSemUserCadastrado() throws Exception {
		String usuarioTempNome = "Jose temporario";
		String usuarioTempSenha = "1010";

		Usuario resultado = this.serviceUsuario.loginUser(usuarioTempNome, usuarioTempSenha);
        
		Assert.assertNull(resultado);
	}



}
