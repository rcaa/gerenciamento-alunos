package br.com.gerenciamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.model.Aluno;


public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT a FROM Aluno a WHERE a.status = 'ATIVO' ")
    List<Aluno> findByStatusAtivo();

    @Query("SELECT i FROM Aluno i WHERE i.status = 'INATIVO' ")
    List<Aluno> findByStatusInativo();

    @Query("Select m FROM Aluno m WHERE m.matricula = :matricula")
    Optional<Aluno> getByMatricula(String matricula);

    @Query("Select c FROM Aluno c WHERE c.curso = :curso")
    List<Aluno> findByCurso(Curso curso);

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

}
