package br.com.gerenciamento.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
import br.com.gerenciamento.repository.AlunoRepository;
import br.com.gerenciamento.service.ServiceAluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    AlunoController alunoController;

    @Autowired
    ServiceAluno serviceAluno;

    @Autowired
    AlunoRepository alunoRepository;

    @Test
    public void insertAluno() throws Exception {

        alunoRepository.deleteAll();

        ModelAndView mvGetMethod = alunoController.insertAlunos();
        assertEquals("Aluno/formAluno", mvGetMethod.getViewName()); // Valida o método GET

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Guilherme");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("176");

        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");

        ModelAndView mvPostMethod = alunoController.inserirAluno(aluno, bindingResult);
        assertEquals("redirect:/alunos-adicionados", mvPostMethod.getViewName()); // Valida o método POST

        int qtdeAlunosFinal = serviceAluno.findAll().size();

        assertEquals(1, qtdeAlunosFinal); // Valida a inserção do novo usuário
    }

    @Test
    public void editAluno() throws Exception {

        long id = 1L;
        String nomeInicial = "Guilherme";
        String nomeFinal = "Guillaume";

        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setNome(nomeInicial);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("176");

        serviceAluno.save(aluno);

        ModelAndView mvGetMethod = alunoController.editar(id);
        assertEquals("Aluno/editar", mvGetMethod.getViewName()); // Valida o método GET

        aluno.setNome(nomeFinal);

        ModelAndView mvPostMethod = alunoController.editar(aluno);
        assertEquals("redirect:/alunos-adicionados", mvPostMethod.getViewName()); // Valida o método POST

        String nomeReal = serviceAluno.findByNomeContainingIgnoreCase(nomeFinal).get(0).getNome();

        assertEquals(nomeFinal, nomeReal);

    }

    @Test
    public void pesquisarAluno() throws Exception {

        alunoRepository.deleteAll();

        String nome = "Guilherme";
        String matricula = "176";

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome(nome);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula(matricula);

        serviceAluno.save(aluno);

        ModelAndView mv = alunoController.pesquisarAluno(nome);

        @SuppressWarnings("unchecked")
        List<Aluno> busca = (List<Aluno>) mv.getModel().get("ListaDeAlunos");

        assertEquals(matricula, busca.get(0).getMatricula());

    }

    @Test
    public void listarAlunosAtivos() throws Exception {

        alunoRepository.deleteAll();

        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();

        aluno1.setId(1L);
        aluno1.setNome("Guilherme");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("176");

        aluno2.setId(2L);
        aluno2.setNome("Guillaume");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("1763");

        serviceAluno.save(aluno1);
        serviceAluno.save(aluno2);

        ModelAndView mv = alunoController.listaAlunosAtivos();

        @SuppressWarnings("unchecked")
        List<Aluno> alunosAtivos = (List<Aluno>) mv.getModel().get("alunosAtivos");

        int qtdeAtivos = alunosAtivos.size();

        assertEquals(1, qtdeAtivos);

    }
}
