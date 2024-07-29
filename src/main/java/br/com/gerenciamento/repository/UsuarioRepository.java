package br.com.gerenciamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gerenciamento.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT e FROM Usuario e WHERE e.email = :email")

    Usuario findByEmail(@Param("email") String email);

    @Query("SELECT l FROM Usuario l WHERE l.user = :user and l.senha = :senha")
    Usuario buscarLogin(@Param("user") String user, @Param("senha") String senha);

}
