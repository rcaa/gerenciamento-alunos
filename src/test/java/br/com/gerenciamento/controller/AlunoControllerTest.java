package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AlunoControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
    }

    @Test
    public void testInsertAlunoView() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"));
    }

    @Test
    public void InserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João");
        aluno.setStatus(Status.ATIVO);

        mockMvc.perform(post("/InsertAlunos")
                        .flashAttr("aluno", aluno))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void ListarAlunos() throws Exception {
        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void RemoverAluno() throws Exception {
        Long alunoId = 1L;
        mockMvc.perform(get("/remover/{id}", alunoId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }
}
