package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void findByEmail() {
        // Verifica a busca de um usuário por email (existente)
        Usuario usuario = new Usuario();
        usuario.setEmail("mayara.keferreira@ufape.edu.br");
        usuario.setSenha("Ufape123456");
        this.usuarioRepository.save(usuario);
        Usuario usuarioEncontrado = usuarioRepository.findByEmail("mayara.keferreira@ufape.edu.br");
        assertNotNull(usuarioEncontrado); // Verifica se o usuário não é nulo
        assertEquals(usuario.getEmail(), usuarioEncontrado.getEmail()); // Verifica se o email do usuário cadastrado é igual ao do encontrado
    }

    @Test
    public void findByEmailNaoEncontrado() {
        // Verifica a busca de um usuário por email (inexistente)
        Usuario usuarioEncontrado = usuarioRepository.findByEmail("mayara.keferreira@ufape.edu.br");
        assertNull(usuarioEncontrado); // Deve ser nulo pois o usuário não existe
    }

    @Test
    public void buscarLogin() {
        // Verifica a busca por login de um usuário (existente)
        Usuario usuario = new Usuario();
        usuario.setUser("Mayara");
        usuario.setEmail("mayara.keferreira@ufape.edu.br");
        usuario.setSenha("Ufape123456");
        this.usuarioRepository.save(usuario);
        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("Mayara", "Ufape123456");
        assertNotNull(usuarioEncontrado); // Verifica se o usuário não é nulo
        // Verifica se os dados de login do usuário cadastrado são iguais ao do encontrado
        assertEquals(usuario.getUser(), usuarioEncontrado.getUser());
        assertEquals(usuario.getSenha(), usuarioEncontrado.getSenha());
    }

    @Test
    public void buscarLoginNaoEncontrado() {
        // Verifica a busca por login de um usuário (inexistente)
        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("Mayara", "Ufape123456");
        assertNull(usuarioEncontrado); // Deve ser nulo pois não existe
    }
}
