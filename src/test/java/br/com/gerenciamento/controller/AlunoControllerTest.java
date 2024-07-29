package br.com.gerenciamento.controller;

import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

import jakarta.transaction.Transactional;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void inserirAluno() throws Exception {
        this.mockMvc.perform(
                post("/InsertAlunos")
                        .formField("nome", "Ariel")
                        .formField("curso", "BIOMEDICINA")
                        .formField("matricula", "2002")
                        .formField("turno", "MATUTINO")
                        .formField("status", "ATIVO")
        ).andExpect(
                view().name("redirect:/alunos-adicionados")
        );
    }

    @Test
    @Transactional
    public void listarAlunos() throws Exception {
        Aluno aluno = new Aluno();

        aluno.setNome("Jasmin");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("011");

        this.alunoRepository.save(aluno);

        this.mockMvc.perform(
                get("/alunos-adicionados")
        ).andExpect(
                view().name("Aluno/listAlunos")
        ).andExpect(
                model().attributeExists("alunosList")
        );
    }

    @Test
    @Transactional
    public void excluirAluno() throws Exception {
        Aluno aluno = new Aluno();

        aluno.setNome("Candy");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("424");

        this.alunoRepository.save(aluno);

        this.mockMvc.perform(
                get("/remover/" + aluno.getId())
        ).andExpect(
                status().is3xxRedirection()
        ).andExpect(
                redirectedUrl("/alunos-adicionados")
        );

        this.mockMvc.perform(
                get("/alunos-adicionados")
        ).andExpect(
                view().name("Aluno/listAlunos")
        ).andExpect(
                model().attributeExists("alunosList")
        );
    }

    @Test
    @Transactional
    public void pesquisarAluno() throws Exception {
        Aluno aluno = new Aluno();

        aluno.setNome("Merida");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("901");

        this.alunoRepository.save(aluno);

        this.mockMvc.perform(
                post("/pesquisar-aluno")
                        .param("nome", "Merida")
        ).andExpect(
                view().name("Aluno/pesquisa-resultado")
        ).andExpect(
                model().attribute("ListaDeAlunos",
                        hasItem(hasProperty("nome", containsString("Merida"))
                        )
                )
        );
    }
}