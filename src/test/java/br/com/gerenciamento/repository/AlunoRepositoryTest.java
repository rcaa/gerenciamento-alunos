package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Léo Ortiz");
        aluno1.setMatricula("MAT111");
        aluno1.setCurso(Curso.CONTABILIDADE);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.MATUTINO);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Léo Pereira");
        aluno2.setMatricula("MAT222");
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setTurno(Turno.NOTURNO);

        alunoRepository.saveAll(List.of(aluno1, aluno2));

        List<Aluno> resultado = alunoRepository.findByNomeContainingIgnoreCase("LÉO");

        Assert.assertEquals(2, resultado.size());
        Assert.assertTrue(resultado.stream().anyMatch(a -> a.getNome().equals("Léo Ortiz")));
        Assert.assertTrue(resultado.stream().anyMatch(a -> a.getNome().equals("Léo Pereira")));
    }

    @Test
    public void testSaveAndFindById() {
        alunoRepository.deleteAll();

        Aluno aluno = new Aluno();
        aluno.setNome("Fabrício Bruno");
        aluno.setMatricula("MAT333");
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);

        Aluno salvo = alunoRepository.save(aluno);

        Aluno resultado = alunoRepository.findById(salvo.getId()).orElse(null);

        Assert.assertNotNull("Aluno não encontrado.", resultado);
        Assert.assertEquals("Fabrício Bruno", resultado.getNome());
        Assert.assertEquals("MAT333", resultado.getMatricula());
        Assert.assertEquals(Curso.BIOMEDICINA, resultado.getCurso());
        Assert.assertEquals(Status.ATIVO, resultado.getStatus());
        Assert.assertEquals(Turno.MATUTINO, resultado.getTurno());
    }

    @Test
    public void testFindByStatusInativo() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Ayrton Lucas");
        aluno1.setMatricula("MAT001");
        aluno1.setCurso(Curso.BIOMEDICINA);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setTurno(Turno.NOTURNO);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Wesley");
        aluno2.setMatricula("MAT002");
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setTurno(Turno.NOTURNO);

        alunoRepository.saveAll(List.of(aluno1, aluno2));

        List<Aluno> resultado = alunoRepository.findByStatusInativo();

        Assert.assertEquals(1, resultado.size());
        Assert.assertEquals("Ayrton Lucas", resultado.get(0).getNome());
    }

    @Test
    public void testFindByStatusAtivo() {
        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Luiz Araújo");
        aluno1.setMatricula("MAT123");
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.MATUTINO);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Gonzalo Plata");
        aluno2.setMatricula("MAT456");
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setTurno(Turno.NOTURNO);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Michael");
        aluno3.setMatricula("MAT789");
        aluno3.setCurso(Curso.ENFERMAGEM);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setTurno(Turno.NOTURNO);

        alunoRepository.saveAll(List.of(aluno1, aluno2, aluno3));

        List<Aluno> resultado = alunoRepository.findByStatusAtivo();

        Assert.assertEquals(2, resultado.size());
        Assert.assertTrue(resultado.stream().allMatch(a -> a.getStatus().equals(Status.ATIVO)));
    }
}