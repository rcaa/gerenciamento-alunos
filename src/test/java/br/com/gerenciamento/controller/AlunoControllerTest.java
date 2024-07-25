package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import br.com.gerenciamento.repository.AlunoRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import jakarta.transaction.Transactional;

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

    @Test
    public void testeInserirAluno()throws Exception{
        this.mockMvc.perform(post("/InsertAlunos")
                .formField("nome", "Gustavo Wanderley")
                .formField("curso", "INFORMATICA")
                .formField("matricula", "24122001")
                .formField("turno", "MATUTINO")
                .formField("status", "ATIVO"))
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    @Transactional
    public void testeListagemTodosAlunos() throws Exception
    {
        Aluno aluno = new Aluno();
        aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
        this.alunoRepository.save(aluno);
        this.mockMvc.perform(get("/alunos-adicionados")).andExpect(view().name("Aluno/listAlunos")).andExpect(model().attribute("alunosList", hasItem(hasProperty("nome", containsString("aaaaa")))));
    }

    @Test
    public void testEditarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setStatus(Status.ATIVO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setMatricula("123456");
        serviceAluno.save(aluno);

        this.mockMvc.perform(post("/editar")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", aluno.getId().toString())
                        .param("nome", "João Silva Editado")
                        .param("status", "ATIVO")
                        .param("curso", "INFORMATICA")
                        .param("turno", "MATUTINO")
                        .param("matricula", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testRemoverAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setStatus(Status.ATIVO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setMatricula("123456");
        serviceAluno.save(aluno);

        this.mockMvc.perform(get("/remover/" + aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

}
