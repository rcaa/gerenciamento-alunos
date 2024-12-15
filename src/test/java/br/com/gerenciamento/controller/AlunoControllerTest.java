package br.com.gerenciamento.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testeInserirAluno() throws Exception {
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.BIOMEDICINA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        alunoController.inserirAluno(a1, new MapBindingResult(new HashMap<Integer, Integer>(),"null"));

        List<Aluno> aluno = alunoRepository.findByNomeContainingIgnoreCase("Arthur");
        assertEquals("123", aluno.get(0).getMatricula());
    }

    @Test
    public void testeEditarAluno() throws Exception {
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.BIOMEDICINA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        alunoRepository.save(a1);

        a1.setCurso(Curso.ADMINISTRACAO);
        alunoController.editar(a1);

        List<Aluno> aluno = alunoRepository.findByNomeContainingIgnoreCase("Arthur");
        assertEquals(Curso.ADMINISTRACAO, aluno.get(0).getCurso());
    }

    @Test
    public void testeListagemAlunosInativos() {
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.BIOMEDICINA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        Aluno a2 = new Aluno();
        a2.setId(1L);
        a2.setNome("Manoel Elias");
        a2.setTurno(Turno.MATUTINO);
        a2.setCurso(Curso.DIREITO);
        a2.setStatus(Status.INATIVO);
        a2.setMatricula("125");

        alunoRepository.save(a1);
        alunoRepository.save(a2);

        ModelAndView mv = alunoController.listaAlunosInativos();
        Map<String, Object> map = mv.getModel();
        Object lista = map.get("alunosInativos");

        assertEquals(1, ((List<Aluno>) lista).size());
        assertEquals("125", ((List<Aluno>) lista).get(0).getMatricula());
    }

    @Test
    public void testePesquisarAluno() {
        Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Arthur Tenorio");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.BIOMEDICINA);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123");

        alunoRepository.save(a1);

        ModelAndView mv = alunoController.pesquisarAluno("Arthur");
        Map<String, Object> map = mv.getModel();
        Object lista = map.get("ListaDeAlunos");

        assertEquals(1, ((List<Aluno>) lista).size());
        assertEquals("123", ((List<Aluno>) lista).get(0).getMatricula());
    }
}
