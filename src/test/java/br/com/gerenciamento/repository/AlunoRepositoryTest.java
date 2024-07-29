package br.com.gerenciamento.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @BeforeEach
    void setUp() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("João");
        aluno1.setStatus(Status.ATIVO);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria");
        aluno2.setStatus(Status.INATIVO);
        alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("José");
        aluno3.setStatus(Status.INATIVO);
        alunoRepository.save(aluno3);
    }

    @Test
    void testFindByStatusAtivo() {
        List<Aluno> ativos = alunoRepository.findByStatusAtivo();

        assertEquals(2, ativos.size());
        assertEquals("João", ativos.get(0).getNome());
        assertEquals("José", ativos.get(1).getNome());
    }

    @Test
    void testFindByStatusInativo() {
        List<Aluno> inativos = alunoRepository.findByStatusInativo();

        assertEquals(1, inativos.size());
        assertEquals("Maria", inativos.get(0).getNome());
    }

    @Test
    void testFindByNomeContainingIgnoreCase() {
        List<Aluno> resultado = alunoRepository.findByNomeContainingIgnoreCase("jo");

        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
        assertEquals("José", resultado.get(1).getNome());
    }

    @Test
    void testSalvarEBuscarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos");
        aluno.setStatus(Status.ATIVO);

        Aluno salvo = alunoRepository.save(aluno);
        Aluno encontrado = alunoRepository.findById(salvo.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Carlos", encontrado.getNome());
        assertEquals(Status.ATIVO, encontrado.getStatus());
    }

}
