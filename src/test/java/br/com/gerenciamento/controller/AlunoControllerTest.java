package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@SpringJUnitConfig
public class AlunoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testInsertAlunosPage() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
            .andExpect(status().isOk())
            .andExpect(view().name("Aluno/formAluno"))
            .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void testInserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos");
        aluno.setMatricula("987654");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);

        mockMvc.perform(post("/InsertAlunos")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("nome", aluno.getNome())
            .param("matricula", aluno.getMatricula())
            .param("curso", aluno.getCurso().toString())
            .param("status", aluno.getStatus().toString())
            .param("turno", aluno.getTurno().toString()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testListagemAlunos() throws Exception {
        mockMvc.perform(get("/alunos-adicionados"))
            .andExpect(status().isOk())
            .andExpect(view().name("Aluno/listAlunos"))
            .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void testRemoverAluno() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/remover/" + id))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/alunos-adicionados"));
    }
}
