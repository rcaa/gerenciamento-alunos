package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    @SuppressWarnings("deprecation")
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void salvarUsuarioNovo() throws Exception {
        Usuario user = new Usuario();
        user.setEmail("novo@exemplo.com");
        user.setSenha("senha123");

        Mockito.when(usuarioRepository.findByEmail(user.getEmail())).thenReturn(null);
        Mockito.when(usuarioRepository.save(user)).thenReturn(user);

        serviceUsuario.salvarUsuario(user);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(user);
    }
    @Test
    public void salvarUsuarioEmailExistente() {
        Usuario user = new Usuario();
        user.setEmail("existente@exemplo.com");
        user.setSenha("senha123");

        Mockito.when(usuarioRepository.findByEmail(user.getEmail())).thenReturn(user);

        Assert.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(user);
        });
    }

    @Test
    public void loginUserCorreto() throws NoSuchAlgorithmException {
        String email = "user@exemplo.com";
        String senha = "senha123";
        Usuario user = new Usuario();
        user.setEmail(email);
        user.setSenha(Util.md5(senha));

        Mockito.when(usuarioRepository.buscarLogin(email, Util.md5(senha))).thenReturn(user);

        Usuario loginUser = serviceUsuario.loginUser(email, senha);
        Assert.assertNotNull(loginUser);
        Assert.assertEquals(email, loginUser.getEmail());
    }
    
    @Test
    public void loginUserIncorreto() throws NoSuchAlgorithmException {
        String email = "user@exemplo.com";
        String senha = "senha123";

        Mockito.when(usuarioRepository.buscarLogin(email, Util.md5(senha))).thenReturn(null);

        Usuario loginUser = serviceUsuario.loginUser(email, senha);
        Assert.assertNull(loginUser);
    }

}
