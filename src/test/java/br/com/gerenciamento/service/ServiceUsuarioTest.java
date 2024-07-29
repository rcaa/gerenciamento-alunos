package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ServiceUsuarioTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuario() {
        Usuario user = new Usuario();

        Usuario save = usuarioRepository.save(user);
        assertNotNull(save);

    }

    @Test
    public void loginUser() {
        Usuario user = new Usuario();
        usuarioRepository.buscarLogin(user.getUser(), user.getSenha());
    }

    @Test
    public void RedefinirLogin(){
        Usuario user = new Usuario();

        if(Assert.assertTrue(user).IsPresent() == true){
            user.setSenha("12345");
            user.setEmail("novoEmail");
        }


    }

    @Test
    public void CriarUsuario(){
        Usuario user = new Usuario();
        user.setUser("jadson.w123");
        user.setSenha("123");
        user.setEmail("jadson.w123");
        user.setId(12L);
    }
}