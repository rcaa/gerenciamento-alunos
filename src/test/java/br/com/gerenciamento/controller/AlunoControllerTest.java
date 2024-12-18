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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Testes de integração utilizando ModelAndView.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    /**
     * Testa a inserção de um aluno
     */
    @Test
    public void inserirAluno() throws Exception {
        Aluno aluno00 = new Aluno();
        aluno00.setNome("Aluno 00");
        aluno00.setMatricula("000");
        aluno00.setTurno(Turno.NOTURNO);
        aluno00.setCurso(Curso.INFORMATICA);
        aluno00.setStatus(Status.ATIVO);

        BindingResult bindingResult = new BeanPropertyBindingResult(aluno00, "aluno");

        alunoController.inserirAluno(aluno00, bindingResult);

        Aluno aluno = serviceAluno.getById(aluno00.getId());
        Assert.assertEquals("Aluno 00", aluno.getNome());
    }

    /**
     * Testa a listagem de todos os alunos.
     */
    @Test
    public void listarAlunos() {
        Aluno aluno01 = new Aluno();
        aluno01.setNome("Aluno 01");
        aluno01.setMatricula("001");
        aluno01.setTurno(Turno.MATUTINO);
        aluno01.setCurso(Curso.BIOMEDICINA);
        aluno01.setStatus(Status.ATIVO);

        serviceAluno.save(aluno01);

        ModelAndView modelAndView = alunoController.listagemAlunos();

        Assert.assertEquals("Aluno/listAlunos", modelAndView.getViewName());

        @SuppressWarnings("unchecked")
        List<Aluno> alunos = (List<Aluno>) modelAndView.getModel().get("alunosList");

        Assert.assertNotNull(alunos);
        Assert.assertEquals(1, alunos.size());
        Assert.assertEquals("Aluno 01", alunos.get(0).getNome());
    }


    /**
     * Testa a edição de um aluno existente.
     */
    @Test
    public void editarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno 03");
        aluno.setMatricula("002");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);

        serviceAluno.save(aluno);

        aluno.setNome("Aluno Editado");
        alunoController.editar(aluno);

        Aluno alunoEditado = serviceAluno.getById(aluno.getId());
        Assert.assertEquals("Aluno Editado", alunoEditado.getNome());
    }

    /**
     * Testa a pesquisa de um aluno pelo nome.
     */
    @Test
    public void pesquisarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno 04");
        aluno.setMatricula("004");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);

        serviceAluno.save(aluno);

        ModelAndView modelAndView = alunoController.pesquisarAluno("Aluno 04");
        @SuppressWarnings("unchecked")
        List<Aluno> alunos = (List<Aluno>) modelAndView.getModel().get("ListaDeAlunos");

        Assert.assertNotNull(alunos);
        Assert.assertFalse(alunos.isEmpty());
        Assert.assertEquals("Aluno 04", alunos.get(0).getNome());
    }
}
