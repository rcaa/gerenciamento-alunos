package br.com.gerenciamento.repository;

import static org.junit.Assert.assertNull;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void procurarEmailInexistente(){
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");

        this.usuarioRepository.save(usuario);

        assertNull(usuarioRepository.buscarLogin("Fejj", "123"));
    }
    @Test
    public void procurarLoginValido() {
        Usuario usuario = new Usuario();
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");
        this.usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.buscarLogin("Vinicius", "vinicius123");
        assertNotNull(encontrado);
        assertEquals("vinicius123@gmail.com", encontrado.getEmail());
    }

    @Test
        public void procurarLoginComSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("vinicius123");
        this.usuarioRepository.save(usuario);
        Usuario encontrado = usuarioRepository.buscarLogin("Vinicius", "123");
        assertNull(encontrado);
    }

    @Test
    public void procurarLoginComUsuarioInvalido() {
        Usuario usuario = new Usuario();
        usuario.setEmail("vinicius123@gmail.com");
        usuario.setUser("Vinicius");
        usuario.setSenha("senha123");
        this.usuarioRepository.save(usuario);
    
        Usuario resultado = usuarioRepository.buscarLogin("iniius", "senha123");
    
        assertNull("O método deveria retornar null para um nome de usuário incorreto.", resultado);
    }
    
    

}