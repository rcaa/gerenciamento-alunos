package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)

@SpringBootTest
class AlunoRepositoryTest {


    @Autowired
    EntityManager entityManager;

    @Autowired
    AlunoRepository alunoRepository;

  @Test
  public void FindById(){
        Long id = 23L;
        Aluno novoAluno = new Aluno(23L, "Jadson",Curso.INFORMATICA, "123", Turno.NOTURNO, Status.ATIVO );
        CreateAluno(novoAluno);

        Optional<Aluno> foundedAluno  = this.alunoRepository.findById(id);

      Assert.assertTrue(foundedAluno.isPresent());
    }

    @Test
    public void findByStatusAtivo() {
        Aluno novoAluno = new Aluno(23L, "Jadson",Curso.INFORMATICA, "123", Turno.NOTURNO, Status.ATIVO );
        CreateAluno(novoAluno);

        List<Aluno> foundedAluno  = this.alunoRepository.findByStatusAtivo();
    }

    @Test
    public void findByStatusInativo() {
        Aluno novoAluno = new Aluno(23L, "Jadson",Curso.INFORMATICA, "123", Turno.NOTURNO, Status.INATIVO );
        CreateAluno(novoAluno);

        List<Aluno> foundedAluno  = this.alunoRepository.findByStatusInativo();
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno novoAluno = new Aluno(23L, "Jadson",Curso.INFORMATICA, "123", Turno.NOTURNO, Status.ATIVO );
        CreateAluno(novoAluno);

        List<Aluno> foundedAluno  = this.alunoRepository.findByNomeContainingIgnoreCase(novoAluno.getNome());
    }

    @Transactional
    Aluno CreateAluno (Aluno aluno){
        Aluno novoAluno = new Aluno(aluno);

        this.entityManager.persist(novoAluno);

        return novoAluno;
    }
}
