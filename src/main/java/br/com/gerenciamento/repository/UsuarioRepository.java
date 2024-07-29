package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select e from Usuario e where e.email = :email")
    static Usuario findByEmail(String email) {
        return null;
    }

    @Query("select l from Usuario l where l.user = :user and l.senha = :senha")
    Usuario buscarLogin(String user, String senha);

}
