package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

   @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void findByEmail(){
        Usuario usuario = new Usuario();
        usuario.setEmail("pedro@gmail.com");
        usuario.setUser("pedro");
        usuario.setSenha("123456");
        usuario.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("hiago@gmail.com");
        usuario2.setUser("hiago");  
        usuario2.setSenha("123456");
        usuario2.setId(2L);

        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);

        Usuario usuarioProcurado = usuarioRepository.findByEmail("hiago@gmail.com");
        assertEquals("hiago", usuarioProcurado.getUser(), "O aluno deve ser hiago");
    }

    @Test
    @Transactional
    public void findByEmailNaoCadastrado(){
        Usuario usuario = new Usuario();
        usuario.setEmail("marcos@gmail.com");
        usuario.setUser("marcos");
        usuario.setSenha("123456");
        usuario.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("vinicius@gmail.com");
        usuario2.setUser("vinicius");  
        usuario2.setSenha("123456");
        usuario2.setId(2L);

        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);

        Usuario usuarioProcurado = usuarioRepository.findByEmail("joao@gmail.com");
        Assert.assertThrows(NullPointerException.class, () -> {
        usuarioProcurado.getUser();});
    }

    @Test
    @Transactional
    public void buscarLogin(){
        Usuario usuario = new Usuario();
        usuario.setEmail("guilerme@gmail.com");
        usuario.setUser("guilerme");
        usuario.setSenha("123456");
        usuario.setId(3L);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("tais@gmail.com");
        usuario2.setUser("tais");  
        usuario2.setSenha("123456");
        usuario2.setId(4L);

        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);

        Usuario usuarioProcurado = usuarioRepository.buscarLogin("tais", "123456");
        assertEquals(4L, usuarioProcurado.getId(), "id do usuario deve ser 2");
    }

    @Test
    @Transactional
    public void buscarLoginNaoExistente(){
        Usuario usuario = new Usuario();
        usuario.setEmail("ana@gmail.com");
        usuario.setUser("ana");
        usuario.setSenha("123456");
        usuario.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("yuri@gmail.com");
        usuario2.setUser("yuri");  
        usuario2.setSenha("123456");
        usuario2.setId(2L);

        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);

        Usuario usuarioProcurado = usuarioRepository.buscarLogin("David", "111111");
        Assert.assertThrows(NullPointerException.class, () -> {
            usuarioProcurado.getId();});
    }
}
