package br.com.gerenciamento.controller;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void inserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Lucas");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        int qtdAlunosAntes = serviceAluno.findAll().size();

        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");
        alunoController.inserirAluno(aluno, bindingResult);

        int qtdAlunosDepois = serviceAluno.findAll().size();

        assertEquals(qtdAlunosAntes + 1, qtdAlunosDepois);

        List<Aluno> alunos = serviceAluno.findByNomeContainingIgnoreCase("Lucas");
        assertEquals("123456", alunos.get(0).getMatricula());
    }

    @Test
    public void listarAlunosAtivos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Joana");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("555888");

        serviceAluno.save(aluno);

        ModelAndView mv = alunoController.listaAlunosAtivos();
        Map<String, Object> map = mv.getModel();
        List<Aluno> listaAtivos = (List<Aluno>) map.get("alunosAtivos");

        boolean alunoExiste = listaAtivos.stream().anyMatch(a -> a.getMatricula().equals("555888"));
        assertEquals(true, alunoExiste);
    }

    @Test
    public void listarAlunosInativos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Carla");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("444777");

        serviceAluno.save(aluno);

        ModelAndView mv = alunoController.listaAlunosInativos();
        Map<String, Object> map = mv.getModel();
        List<Aluno> listaInativos = (List<Aluno>) map.get("alunosInativos");

        boolean alunoExiste = listaInativos.stream().anyMatch(a -> a.getMatricula().equals("444777"));
        assertEquals(true, alunoExiste);
    }

    @Test
    public void pesquisarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Fernando");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("112233");

        serviceAluno.save(aluno);

        ModelAndView mv = alunoController.pesquisarAluno("Fernando");
        Map<String, Object> map = mv.getModel();
        List<Aluno> listaPesquisada = (List<Aluno>) map.get("ListaDeAlunos");

        boolean alunoExiste = listaPesquisada.stream().anyMatch(a -> a.getMatricula().equals("112233"));
        assertEquals(true, alunoExiste);
    }
}
