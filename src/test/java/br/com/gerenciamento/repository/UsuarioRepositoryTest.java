package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static br.com.gerenciamento.repository.UsuarioRepository.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository userRepository;

    @Test
    public void findByEmail() {
        Usuario user = new Usuario();

        Optional<Usuario> UserFound  = Optional.ofNullable(UsuarioRepository.findByEmail(user.getEmail()));
    }

    @Test
    public void buscarLogin() {
        Usuario user = new Usuario();

        Usuario save = UsuarioRepository.save(user);

        if(assertEquals(user.getEmail(), save.getEmail())){
            System.out.println("Found");
        }
    }
}
