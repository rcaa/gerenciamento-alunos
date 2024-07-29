package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Before
    public void setUp() {
        serviceAluno.deleteById(1L);
        serviceAluno.deleteById(2L);

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Carlos");
        aluno1.setMatricula("654321");
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.MATUTINO);
        serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria");
        aluno2.setMatricula("987654");
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setTurno(Turno.NOTURNO);
        serviceAluno.save(aluno2);
    }

    @Test
    public void testInsertAlunos() {
        ModelAndView modelAndView = alunoController.insertAlunos();
        Assert.assertEquals("Aluno/formAluno", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel().get("aluno"));
    }

    @Test
    public void testInserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Airton");
        aluno.setMatricula("112233");
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);

        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");

        ModelAndView modelAndView = alunoController.inserirAluno(aluno, bindingResult);
        Assert.assertEquals("redirect:/alunos-adicionados", modelAndView.getViewName());

        List<Aluno> alunos = serviceAluno.findAll();
        Assert.assertTrue(alunos.stream().anyMatch(a -> "Airton".equals(a.getNome())));
    }

    @Test
    public void testListagemAlunos() {
        ModelAndView modelAndView = alunoController.listagemAlunos();
        Assert.assertEquals("Aluno/listAlunos", modelAndView.getViewName());

        Object alunosObject = modelAndView.getModel().get("alunosList");
        Assert.assertTrue(alunosObject instanceof List<?>);
        @SuppressWarnings("unchecked")
        List<Aluno> alunos = (List<Aluno>) alunosObject;

        Assert.assertEquals(2, alunos.size());
    }

    @Test
    public void testRemoverAluno() {
        alunoController.removerAluno(1L);

        List<Aluno> alunos = serviceAluno.findAll();
        Assert.assertTrue(alunos.stream().noneMatch(a -> a.getId().equals(1L)));
    }
}