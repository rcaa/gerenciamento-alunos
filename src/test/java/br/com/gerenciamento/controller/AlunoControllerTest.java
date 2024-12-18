package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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
    public void testBuscarAlunoPorNome() throws Exception {
        String nomeAluno = "Marcos Nascimento";
        mockMvc.perform(get("/buscarAluno").param("nome", nomeAluno))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void testAtualizarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Marcos Nascimento Atualizado");
        aluno.setStatus(Status.ATIVO);

        mockMvc.perform(post("/atualizarAluno")
                        .flashAttr("aluno", aluno))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testExibirDetalhesDoAluno() throws Exception {
        Long alunoId = 1L;
        mockMvc.perform(get("/detalhes/{id}", alunoId))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/detalhesAluno"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void testExibirPaginaDeErroAoBuscarAlunoInexistente() throws Exception {
        Long alunoIdInexistente = 999L;

        mockMvc.perform(get("/detalhes/{id}", alunoIdInexistente))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }
}
