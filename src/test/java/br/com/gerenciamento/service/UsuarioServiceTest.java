package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Test
    public void testSalvarUsuarioComSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senha123");

        Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        serviceUsuario.salvarUsuario(usuario);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testSalvarUsuarioEmailExistente() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senha123");

        Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        Exception exception = Assertions.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });

        Assertions.assertEquals("Este email já esta cadastrado: teste@email.com", exception.getMessage());
    }

    @Test
    public void testLoginUsuarioComSucesso() throws NoSuchAlgorithmException {
        String username = "usuarioTeste";
        String senha = "senha123";

        Usuario usuario = new Usuario();
        usuario.setUser(username);
        usuario.setSenha(Util.md5(senha));

        Mockito.when(usuarioRepository.buscarLogin(username, Util.md5(senha))).thenReturn(usuario);

        Usuario usuarioRetornado = serviceUsuario.loginUser(username, Util.md5(senha));

        Assertions.assertNotNull(usuarioRetornado);
        Assertions.assertEquals(username, usuarioRetornado.getUser());
    }

    @Test
    public void testLoginUsuarioInvalido() throws Exception {
        // Criação do usuário com dados inválidos
        Usuario usuario = new Usuario();
        usuario.setUser("usuarioInvalido");
        usuario.setSenha("senhaErrada");

        Mockito.when(usuarioRepository.buscarLogin(usuario.getUser(), Util.md5(usuario.getSenha())))
                .thenReturn(null);

        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), Util.md5(usuario.getSenha()));

        Assert.assertNull(usuarioLogado);
    }

}
