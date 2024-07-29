package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    AlunoController alunoController;
    @Autowired
    ServiceAluno serviceAluno;
    @Test
    public void insertAluno(){
       ModelAndView modelAndView= alunoController.insertAlunos();
       Assert.assertTrue(modelAndView.getViewName().equals("Aluno/formAluno"));
    }

    @Test
    public void listagemAlunos(){
        ModelAndView modelAndView= alunoController.listagemAlunos();
        Assert.assertTrue(modelAndView.getViewName().equals("Aluno/listAlunos"));
    }

    @Test
    public void editar(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        ModelAndView modelAndView= alunoController.editar(1L);
        Assert.assertTrue(modelAndView.getViewName().equals("Aluno/editar"));
    }

    @Test
    public void editarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        ModelAndView modelAndView= alunoController.editar(aluno);
        Assert.assertTrue(modelAndView.getViewName().equals("redirect:/alunos-adicionados"));
    }
}
