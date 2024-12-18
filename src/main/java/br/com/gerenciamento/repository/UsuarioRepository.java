package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select e from Usuario e where e.email = :email")
    Usuario findByEmail(String email);

    Usuario findByUserAndSenha(String user, String senha);
}
