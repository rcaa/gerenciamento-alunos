package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.validation.ConstraintViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Before
    public void setUp() {
        usuarioRepository.deleteAll(); // Limpa o repositório antes de cada teste para evitar interferências.
    }

    @Test
    public void testFindById() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario1");
        usuarioRepository.save(usuario);

        Usuario found = usuarioService.findById(usuario.getId());
        assertNotNull(found);
        assertEquals(usuario.getNome(), found.getNome());
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario1");
        Usuario savedUsuario = usuarioService.save(usuario);
        assertNotNull(savedUsuario);
        assertEquals(usuario.getNome(), savedUsuario.getNome());
    }

    @Test
    public void testFindAll() {
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Usuario1");
        Usuario usuario2 = new Usuario();
        usuario2.setNome("Usuario2");
        
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
    }

    @Test
    public void testDelete() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario1");
        usuarioRepository.save(usuario);

        usuarioService.delete(usuario);
        Usuario found = usuarioService.findById(usuario.getId());
        assertNull(found);
    }

    @Test
    public void salvarSemNome() {
        Usuario usuario = new Usuario();
        assertThrows(ConstraintViolationException.class, () -> {
            usuarioService.save(usuario);
        });
    }
}
