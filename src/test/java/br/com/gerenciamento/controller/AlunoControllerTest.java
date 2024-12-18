package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AlunoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceAluno serviceAluno;

    @InjectMocks
    private AlunoController alunoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
    }

    @Test
    void testInserirAluno() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"));
    }

    @Test
    void testAtualizarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João");
        aluno.setMatricula("123");
        aluno.setCurso(null);
        aluno.setStatus(null);
        aluno.setTurno(null);

        mockMvc.perform(post("/editar")
                .flashAttr("aluno", aluno))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    void testListarAlunos() throws Exception {
        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"));
    }

    @Test
    void testRemoverAluno() throws Exception {
        Long alunoId = 1L;
        mockMvc.perform(get("/remover/{id}", alunoId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }
}
