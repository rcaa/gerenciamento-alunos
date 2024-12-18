package br.com.gerenciamento.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceAluno serviceAluno;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void criarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Isabel Tenorio");
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setMatricula("040299");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);

        doNothing().when(serviceAluno).save(any(Aluno.class));

        mockMvc.perform(post("/InsertAlunos")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", aluno.getNome())
                .param("curso", aluno.getCurso().toString())
                .param("matricula", aluno.getMatricula())
                .param("status", aluno.getStatus().toString())
                .param("turno", aluno.getTurno().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void excluirAluno() throws Exception {
        doNothing().when(serviceAluno).deleteById(1L);

        mockMvc.perform(get("/remover/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void editarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Fatima Sueli");

        when(serviceAluno.getById(1L)).thenReturn(aluno);

        mockMvc.perform(get("/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/editar"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().attribute("aluno", aluno));
    }

@Test
public void listarAlunos() throws Exception {
    mockMvc.perform(get("/alunos-adicionados"))
            .andExpect(status().isOk())
            .andExpect(view().name("Aluno/listAlunos"))
            .andExpect(model().attributeExists("alunosList"));
}
}
