package br.com.gerenciamento.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;

public class UsuarioServiceTest {

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Primeiro teste implementado
    @Test
    public void testFindByEmail() {
        String email = "test@test.com";
        serviceUsuario.findByEmail(email);
        verify(usuarioRepository, times(1)).findByEmail(email);
    }


    //Segundo teste implementado
    @Test
    public void testSalvarUsuario() throws Exception {
        Usuario user = new Usuario();
        user.setEmail("test@test.com");
        user.setSenha("password");
        when(usuarioRepository.findByEmail(user.getEmail())).thenReturn(null);
        serviceUsuario.salvarUsuario(user);
        verify(usuarioRepository, times(1)).save(user);
    }

    //Terceiro teste implementado
    @Test
    public void testLoginUser() {
        String user = "testUser";
        String senha = "password";
        serviceUsuario.loginUser(user, senha);
        verify(usuarioRepository, times(1)).buscarLogin(user, senha);
    }

    //Quarto teste implementado
    @Test
    public void testSalvarUsuarioEmailExistente() {
        Usuario user = new Usuario();
        user.setEmail("test@test.com");
        when(usuarioRepository.findByEmail(user.getEmail())).thenReturn(user);
        assertThrows(EmailExistsException.class, () -> serviceUsuario.salvarUsuario(user));
    }
}
