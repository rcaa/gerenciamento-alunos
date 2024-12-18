package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT a FROM Aluno a WHERE a.status = 'ATIVO' ")
    List<Aluno> findByStatusAtivo();

    @Query("SELECT i FROM Aluno i WHERE i.status = 'INATIVO' ")
    List<Aluno> findByStatusInativo();

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    List<Aluno> findByTurno(Turno matutino);

}
