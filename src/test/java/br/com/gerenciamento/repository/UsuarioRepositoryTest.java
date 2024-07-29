package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        usuarioRepository.deleteAll();
        usuarioRepository.save(new Usuario("joao@exemplo.com", "João", "senha123"));
        usuarioRepository.save(new Usuario("maria@exemplo.com", "Maria", "senha456"));
        usuarioRepository.save(new Usuario("carlos@exemplo.com", "Carlos", "senha789"));
    }

    @Test
    public void testFindByEmail() {
        Usuario usuario = usuarioRepository.findByEmail("joao@exemplo.com");
        assertNotNull(usuario);
        assertEquals("João", usuario.getUser());
    }

    @Test
    public void testBuscarLogin() {
        Usuario usuario = usuarioRepository.buscarLogin("Maria", "senha456");
        assertNotNull(usuario);
        assertEquals("maria@exemplo.com", usuario.getEmail());
    }

    @Test
    public void testFindById() {
        Usuario usuario = new Usuario("ana@exemplo.com", "Ana", "senha321");
        Usuario salvo = usuarioRepository.save(usuario);
        Optional<Usuario> encontrado = usuarioRepository.findById(salvo.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Ana", encontrado.get().getUser());
    }

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario("pedro@exemplo.com", "Pedro", "senha654");
        Usuario salvo = usuarioRepository.save(usuario);
        assertNotNull(salvo);
        assertEquals("Pedro", salvo.getUser());
    }
}
