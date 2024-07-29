package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import br.com.gerenciamento.service.ServiceAluno;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Autowired
    private ServiceAluno serviceAluno;

    //teste 1 - inserir um aluno
    @Test
    public void testeInserirAluno()throws Exception{
        this.mockMvc.perform(post("/InsertAlunos")
                .formField("nome", "Usuario Teste")
                .formField("curso", "INFORMATICA")
                .formField("matricula", "12345677")
                .formField("turno", "NOTURNO")
                .formField("status", "ATIVO"))
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    //teste 2 - editar um aluno
    @Test
    public void testeEditarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Gustavo");
        aluno.setStatus(Status.INATIVO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setMatricula("123456");
        serviceAluno.save(aluno);

        this.mockMvc.perform(post("/editar")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", aluno.getId().toString())
                        .param("nome", "Gustavo Henrique")
                        .param("status", "ATIVO")
                        .param("curso", "INFORMATICA")
                        .param("turno", "MATUTINO")
                        .param("matricula", "15680"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    //teste 3 - teste remover um aluno
    @Test
    public void testeRemoverAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Gustavo Henrique");
        aluno.setStatus(Status.ATIVO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setMatricula("15680");
        serviceAluno.save(aluno);

        this.mockMvc.perform(get("/remover/" + aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    //teste 4 - listagem de alunos
    @Test
    @Transactional
    public void testeListagemTodosAlunos() throws Exception
    {
        Aluno aluno = new Aluno();
        aluno.setNome("Gustavo Henrique");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("15680");
        this.alunoRepository.save(aluno);
        this.mockMvc.perform(get("/alunos-adicionados")).andExpect(view().name("Aluno/listAlunos")).andExpect(model().attribute("alunosList", hasItem(hasProperty("nome", containsString("Gustavo Henrique")))));
    }
}
