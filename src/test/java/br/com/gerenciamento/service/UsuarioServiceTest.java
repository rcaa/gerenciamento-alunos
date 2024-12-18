package br.com.gerenciamento.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {
    @InjectMocks
    private ServiceUsuario serviceUsuario; // Classe a ser testada

    @Mock
    private UsuarioRepository usuarioRepository; // Mock do repositório

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configuração inicial para os testes
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("João");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("senha123");
    }

    @Test
    public void salvarUsuarioValido() throws Exception {
        // Simula o comportamento do repositório sem email duplicado
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);

        // Salva o usuário
        serviceUsuario.salvarUsuario(usuario);

        // Verifica se o método save foi chamado
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void salvarUsuarioEmailDuplicado() {
        // Simula email duplicado no repositório
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        // Verifica se a exceção EmailExistsException é lançada
        EmailExistsException exception = assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });

        assertEquals("Este email já esta cadastrado: joao@example.com", exception.getMessage());
    }

    @Test
    public void loginUsuarioValido() {
        // Simula credenciais válidas retornando o usuário
        when(usuarioRepository.buscarLogin("João", "senha123")).thenReturn(usuario);

        // Realiza o login
        Usuario usuarioLogado = serviceUsuario.loginUser("João", "senha123");

        // Verifica o retorno correto
        assertNotNull(usuarioLogado);
        assertEquals("João", usuarioLogado.getUser());
        assertEquals("joao@example.com", usuarioLogado.getEmail());
    }

    @Test
    public void loginUsuarioInvalido() {
        // Simula credenciais inválidas retornando null
        when(usuarioRepository.buscarLogin("João", "senhaErrada")).thenReturn(null);

        // Realiza o login com credenciais inválidas
        Usuario usuarioLogado = serviceUsuario.loginUser("João", "senhaErrada");

        // Verifica que o login falha
        assertNull(usuarioLogado);
    }
}
