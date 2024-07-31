package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class ServiceUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void save(Usuario user) throws Exception {
        try {
            if (usuarioRepository.findByEmail(user.getEmail()) != null) {
                throw new EmailExistsException("Este email já está cadastrado: " + user.getEmail());
            }
            user.setSenha(Util.md5(user.getSenha()));
        } catch (NoSuchAlgorithmException e) {
            throw new CriptoExistsException("Erro na criptografia da senha");
        }
        usuarioRepository.save(user);
    }

    public Usuario getById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario loginUser(String user, String senha) {
        return usuarioRepository.buscarLogin(user, senha);
    }
}
