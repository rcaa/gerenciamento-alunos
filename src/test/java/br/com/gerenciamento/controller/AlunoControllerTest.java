package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.springframework.validation.BeanPropertyBindingResult;
import org.junit.Before;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Before
    public void setUp() {
        serviceAluno.deleteAll();  // Limpa todos os alunos do banco de dados antes de cada teste
    }

    @Test
    public void testInserirAluno() throws Exception {

        Aluno novoAluno = new Aluno();
        novoAluno.setNome("Arrascaeta");
        novoAluno.setMatricula("12345");
        novoAluno.setCurso(Curso.INFORMATICA);
        novoAluno.setStatus(Status.ATIVO);
        novoAluno.setTurno(Turno.NOTURNO);

        BindingResult bindingResult = new BeanPropertyBindingResult(novoAluno, "novoAluno");

        ModelAndView modelAndView = alunoController.inserirAluno(novoAluno, bindingResult);

        assertThat(modelAndView.getViewName()).isEqualTo("redirect:/alunos-adicionados");

        List<Aluno> alunos = serviceAluno.findAll();
        assertThat(alunos).hasSize(1);
        assertThat(alunos.get(0).getNome()).isEqualTo("Arrascaeta");
    }

    @Test
    public void testListagemAlunos() {
        ModelAndView modelAndView = alunoController.listagemAlunos();

        assertThat(modelAndView.getViewName()).isEqualTo("Aluno/listAlunos");
        assertThat(modelAndView.getModel()).containsKey("alunosList");
    }

    @Test
    public void testEditarAluno() throws Exception {
        Aluno novoAluno = new Aluno();
        novoAluno.setNome("Carlos");
        novoAluno.setMatricula("54321");
        novoAluno.setCurso(Curso.ADMINISTRACAO);
        novoAluno.setStatus(Status.ATIVO);
        novoAluno.setTurno(Turno.NOTURNO);

        serviceAluno.save(novoAluno);

        novoAluno.setNome("Carlos Alterado");
        ModelAndView modelAndView = alunoController.editar(novoAluno);

        assertThat(modelAndView.getViewName()).isEqualTo("redirect:/alunos-adicionados");

        Aluno alunoEditado = serviceAluno.getById(novoAluno.getId());
        assertThat(alunoEditado.getNome()).isEqualTo("Carlos Alterado");
    }

    @Test
    public void testRemoverAluno() {
        Aluno novoAluno = new Aluno();
        novoAluno.setNome("Arthur");
        novoAluno.setMatricula("67890");
        novoAluno.setCurso(Curso.ADMINISTRACAO);
        novoAluno.setStatus(Status.INATIVO);
        novoAluno.setTurno(Turno.MATUTINO);

        serviceAluno.save(novoAluno);

        alunoController.removerAluno(novoAluno.getId());

        List<Aluno> alunos = serviceAluno.findAll();
        assertThat(alunos).doesNotContain(novoAluno);
    }
}