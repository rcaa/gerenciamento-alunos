package br.com.gerenciamento.repository;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
     @Autowired
        private UsuarioRepository usuarioRepository;

        @Test
        public void findByEmail() throws NoSuchAlgorithmException {
            Usuario usuario = new Usuario();
            usuario.setUser("usuario");
            usuario.setEmail("usuario@email.com");
            usuario.setSenha(Util.md5("senha"));
            usuarioRepository.save(usuario);

            Usuario userFound = usuarioRepository.findByEmail("usuario@email.com");
            Assert.assertNotNull(userFound);
            Assert.assertEquals("usuario", userFound.getUser());
        }
        @Test
        public void buscarLogin() throws NoSuchAlgorithmException {
            Usuario usuario = new Usuario();
            usuario.setUser("usuario");
            usuario.setSenha(Util.md5("senha"));
            usuarioRepository.save(usuario);

            Usuario userFound = usuarioRepository.buscarLogin("usuario", Util.md5("senha"));
            Assert.assertNotNull(userFound);
            Assert.assertEquals("usuario", userFound.getUser());
        }

        @Test
        public void buscarLoginComSenhaErrada() throws NoSuchAlgorithmException {
            Usuario usuario = new Usuario();
            usuario.setUser("usuario");
            usuario.setSenha(Util.md5("senha"));
            usuarioRepository.save(usuario);

            Usuario userFound = usuarioRepository.buscarLogin("usuario", Util.md5("wrongpassword"));
            Assert.assertNull(userFound);
        }

        @Test
        public void findByEmailNaoExistente() throws NoSuchAlgorithmException {
            Usuario usuario = new Usuario();
            usuario.setUser("usuario");
            usuario.setEmail("usuario@email.com");
            usuario.setSenha(Util.md5("senha"));
            usuarioRepository.save(usuario);

            Usuario userFound = usuarioRepository.findByEmail("nonexisting@email.com");
            Assert.assertNull(userFound);
        }


    }
