package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @BeforeEach
    public void setUp() {
        alunoRepository.deleteAll();
        alunoRepository.save(new Aluno("João", "joao@exemplo.com", "ATIVO", "Matutino", "12345", "Biomedicina"));
        alunoRepository.save(new Aluno("Maria", "maria@exemplo.com", "INATIVO", "Vespertino", "67890", "Administracao"));
        alunoRepository.save(new Aluno("Carlos", "carlos@exemplo.com", "ATIVO", "Noturno", "11223", "Direito"));
    }

    @Test
    public void testFindByStatusAtivo() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        Assertions.assertNotNull(alunosAtivos);
        Assertions.assertEquals(2, alunosAtivos.size());
    }

    @Test
    public void testFindByStatusInativo() {
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assertions.assertNotNull(alunosInativos);
        Assertions.assertEquals(1, alunosInativos.size());
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("jo");
        Assertions.assertNotNull(alunos);
        Assertions.assertEquals(1, alunos.size());
        Assertions.assertEquals("João", alunos.get(0).getNome());
    }

    @Test
    public void testFindByNomeContainingIgnoreCaseMultipleResults() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("a");
        Assertions.assertNotNull(alunos);
        Assertions.assertEquals(2, alunos.size());
    }
}
