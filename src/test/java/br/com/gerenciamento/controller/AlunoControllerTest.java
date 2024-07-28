package br.com.gerenciamento.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Test
    public void inserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setMatricula("123456");
        aluno.setNome("Marco");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);

        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");

        ModelAndView modelAndView = this.alunoController.inserirAluno(aluno, bindingResult);

        assertEquals("redirect:/alunos-adicionados", modelAndView.getViewName());
    }

    @Test
    public void inserirAlunoSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setMatricula("");
        aluno.setNome("Maria Zuleide");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);

        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");

        assertThrows(ConstraintViolationException.class, () -> {
            this.alunoController.inserirAluno(aluno, bindingResult);
        });
    }

    @Test
    public void editarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setMatricula("776655");
        aluno.setNome("Maria do Carmo");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");
        this.alunoController.inserirAluno(aluno, bindingResult);

        aluno.setMatricula("225577");
        aluno.setNome("Maria do Carmo Silva");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ENFERMAGEM);

        ModelAndView modelAndView = this.alunoController.editar(aluno);

        assertEquals("redirect:/alunos-adicionados", modelAndView.getViewName());
    }

    @Test
    public void removerAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(4L);
        aluno.setMatricula("992299");
        aluno.setNome("Mario Silva");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");
        this.alunoController.inserirAluno(aluno, bindingResult);

        String modelAndView = this.alunoController.removerAluno(aluno.getId());

        assertEquals("redirect:/alunos-adicionados", modelAndView);
    }

}
