package br.com.gerenciamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT a FROM Aluno a WHERE a.status = 'ATIVO' ")
    List<Aluno> findByStatusAtivo();

    @Query("SELECT i FROM Aluno i WHERE i.status = 'INATIVO' ")
    List<Aluno> findByStatusInativo();
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
    List<Aluno> findByCurso(Curso administracao);
    List<Aluno> findByTurno(Turno noturno);
    List<Aluno> findByMatricula(String string);

}
