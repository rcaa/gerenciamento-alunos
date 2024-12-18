package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    // Salvar um usuário com sucesso
    @Test
    public void salvarUsuarioComSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("joaosilva");
        usuario.setEmail("joao.silva@email.com");
        usuario.setSenha("123456");

        Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null); 
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        serviceUsuario.salvarUsuario(usuario);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
    }

    // Salvar um usuário com e-mail já existente
    @Test
    public void salvarUsuarioComEmailExistente() {
        Usuario usuario = new Usuario();
        usuario.setUser("mariasantos");
        usuario.setEmail("maria.santos@email.com");
        usuario.setSenha("senha123");

        Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario); 

        Assert.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });

        Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any(Usuario.class));
    }

    // Login bem-sucedido
    @Test
    public void loginUsuarioComSucesso() {
        String username = "joaosilva";
        String senha = "123456";
        Usuario usuario = new Usuario();
        usuario.setUser(username);
        usuario.setEmail("joao.silva@email.com");
        usuario.setSenha(senha);

        Mockito.when(usuarioRepository.buscarLogin(username, senha)).thenReturn(usuario); 
        Usuario usuarioLogado = serviceUsuario.loginUser(username, senha);

        Assert.assertNotNull(usuarioLogado);
        Assert.assertEquals("joaosilva", usuarioLogado.getUser());
        Mockito.verify(usuarioRepository, Mockito.times(1)).buscarLogin(username, senha);
    }

    // Login com credenciais inválidas (usuário não encontrado)
    @Test
    public void loginUsuarioComCredenciaisInvalidas() {
        String username = "usuarioinvalido";
        String senha = "senhaerrada";

        Mockito.when(usuarioRepository.buscarLogin(username, senha)).thenReturn(null); 
        Usuario usuarioLogado = serviceUsuario.loginUser(username, senha);

        Assert.assertNull(usuarioLogado);
        Mockito.verify(usuarioRepository, Mockito.times(1)).buscarLogin(username, senha);
    }
}
