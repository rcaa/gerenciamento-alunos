package br.com.gerenciamento.controller;


import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void pesquisarAluno() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("alice");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("123456");
        this.alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Alice");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123");
        this.alunoRepository.save(aluno2);
        
        mockMvc.perform(post("/pesquisar-aluno")
            .param("nome", "alice"))
            .andExpect(status().isOk()) 
            .andExpect(view().name("Aluno/pesquisa-resultado"))
            .andExpect(model().attribute("ListaDeAlunos", hasSize(2)))
            .andExpect(model().attribute("ListaDeAlunos", hasItems(
                hasProperty("nome", is("alice")),
                hasProperty("nome", is("Alice"))
            )));
    }

    @Test
    @Transactional
    public void inserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("alice");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);

        mockMvc.perform(post("/InsertAlunos")
            .flashAttr("aluno", aluno))
            .andExpect(status().is3xxRedirection()) 
            .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    @Transactional
    public void removerAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("alice");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);

        mockMvc.perform(get("/remover/" + aluno.getId()))
            .andExpect(status().is3xxRedirection())  
            .andExpect(view().name("redirect:/alunos-adicionados"));
        Assert.assertTrue(alunoRepository.findById(aluno.getId()).isEmpty());
    }

    @Test
    @Transactional
    public void listaAlunosAtivos() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("alice");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        this.alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Alice");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ENFERMAGEM);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123");
        this.alunoRepository.save(aluno2);

        mockMvc.perform(get("/alunos-ativos"))
            .andExpect(status().isOk())
            .andExpect(view().name("Aluno/alunos-ativos"))
            .andExpect(model().attribute("alunosAtivos", hasSize(2)))
            .andExpect(model().attribute("alunosAtivos", hasItems(
                hasProperty("nome", is("Alice")),
                hasProperty("nome", is("alice"))
            )));
    }
}
