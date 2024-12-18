package br.com.gerenciamento.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void deveriaCarregarUmAlunoAoBuscarPeloNome() {
        Aluno aluno = new Aluno();
        aluno.setNome("Matheus");
        aluno.setMatricula("123456");
        aluno.setStatus(Status.ATIVO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setTurno(Turno.NOTURNO);
        alunoRepository.save(aluno);

        String nome = "Matheus";
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase(nome);
        Assert.assertEquals(1, alunos.size());
        Assert.assertEquals(nome, alunos.get(0).getNome());
    }
}
