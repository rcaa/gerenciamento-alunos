package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.List;

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
        user.setUser("Alexandre");
        user.setId(12L);
        user.setSenha("12345");
        user.setEmail("alexandre.w");

        Usuario save = UserRepository.save(user);

        
        Usuario userRetorno = this.serviceAluno.findByEmail("alexandre.w");
        Assert.assertTrue(userRetorno.getNome().equals("Alexandre"));

    }

    @Test
    public void findSameEmail() {
        Usuario user = new Usuario();
        user.setUser("Alexandre");
        user.setId(12L);
        user.setSenha("12345");
        user.setEmail("alexandre.w");

        Usuario save = UserRepository.save(user);

        Usuario user1 = new Usuario();
        user1.setUser("Fabio");
        user1.setId(17L);
        user1.setSenha("12345");
        user1.setEmail("alexandre.w");

        Usuario UsuarioCopiado = UserRepository.save(user1);

        
        List<Usuario> userRetorno = this.serviceAluno.findSameEmail("alexandre.w");

    }

    @Test
    public void findUserClone() {
        Usuario user = new Usuario();
        user.setUser("Alexandre");
        user.setId(12L);
        user.setSenha("12345");
        user.setEmail("alexandre.w");

        Usuario save = UserRepository.save(user);

        Usuario user1 = new Usuario();
        user1.setUser("Alexandre");
        user1.setId(12L);
        user1.setSenha("12345");
        user1.setEmail("alexandre.w");

        Usuario UsuarioCopiado = UserRepository.save(user1);

        
        List<Usuario> userRetorno = this.serviceAluno.findUserClone("Alexandre");

    }


    @Test
    public void buscarLogin() {
        Usuario user = new Usuario();
        user.setUser("Alexandre");
        user.setId(12L);
        user.setSenha("12345");
        user.setEmail("alexandre.w");

        Usuario save = UserRepository.save(user);

        
        Usuario userRetorno = this.serviceAluno.buscarLogin(user.getUser(), user.getSenha());
        Assert.assertTrue(userRetorno.getNome().equals("Alexandre"));
    }
}
