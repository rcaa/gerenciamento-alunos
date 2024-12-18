package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.model.Aluno;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
    
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void buscarAlunoPorMatricula() {
        Aluno aluno = new Aluno();
        aluno.setNome("Lucas Santos");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("20249876");
        aluno.setStatus(Status.ATIVO);
        alunoRepository.save(aluno);

        Optional<Aluno> encontrado = alunoRepository.findByMatricula("20249876");
        Assert.assertTrue(encontrado.isPresent());
        Assert.assertEquals("Lucas Santos", encontrado.get().getNome());
    }
    
     @Test
    public void buscarAlunosAtivos() {
        List<Aluno> ativos = alunoRepository.findByStatusAtivo();
        Assert.assertFalse(ativos.isEmpty());
    }
    
    @Test
    public void buscarAlunosInativos() {
        List<Aluno> inativos = alunoRepository.findByStatusInativo();
        Assert.assertFalse(inativos.isEmpty());
    }

    @Test
    public void salvarAlunoNoBanco() {
        Aluno aluno = new Aluno();
        aluno.setNome("Fernanda Souza");
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setMatricula("20247890");
        aluno.setStatus(Status.ATIVO);

        Aluno salvo = alunoRepository.save(aluno);
        Assert.assertNotNull(salvo.getId());
    }
}
