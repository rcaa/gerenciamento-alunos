package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void testInsertAlunos() {
        ModelAndView modelAndView = alunoController.insertAlunos();
        Assertions.assertEquals("Aluno/formAluno", modelAndView.getViewName());
        Assertions.assertNotNull(modelAndView.getModel().get("aluno"));
    }

    @Test
    public void testInserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setMatricula("123456");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false); 

        ModelAndView modelAndView = alunoController.inserirAluno(aluno, bindingResult);
        Assertions.assertEquals("redirect:/alunos-adicionados", modelAndView.getViewName());

        Aluno alunoSalvo = serviceAluno.findByNomeContainingIgnoreCase("Teste").get(0);
        Assertions.assertNotNull(alunoSalvo);
    }


    @Test
    public void testListagemAlunos() {
        ModelAndView modelAndView = alunoController.listagemAlunos();
        Assertions.assertEquals("Aluno/listAlunos", modelAndView.getViewName());
        Assertions.assertNotNull(modelAndView.getModel().get("alunosList"));
    }

    @Test
    public void testEditarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste Editar");
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setMatricula("654321");
        serviceAluno.save(aluno);

        Aluno alunoSalvo = serviceAluno.findByNomeContainingIgnoreCase("Teste Editar").get(0);

        ModelAndView modelAndView = alunoController.editar(alunoSalvo.getId());
        Assertions.assertEquals("Aluno/editar", modelAndView.getViewName());
        Assertions.assertNotNull(modelAndView.getModel().get("aluno"));
    }

    @Test
    public void testRemoverAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste Remover");
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.INATIVO);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setMatricula("789012");
        serviceAluno.save(aluno);

        Aluno alunoSalvo = serviceAluno.findByNomeContainingIgnoreCase("Teste Remover").get(0);
        String result = alunoController.removerAluno(alunoSalvo.getId());

        Assertions.assertEquals("redirect:/alunos-adicionados", result);
        serviceAluno.delete(alunoSalvo.getId());
        Aluno alunoDeletado = serviceAluno.getById(alunoSalvo.getId());
        Assertions.assertNull(alunoDeletado);
    }
}
