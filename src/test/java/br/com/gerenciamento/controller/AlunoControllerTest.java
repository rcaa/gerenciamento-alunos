package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    public void testCreateAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana Costa");
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setMatricula("11223");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);

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
    public void testGetAlunoById() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Ana Costa");

        when(serviceAluno.getById(1L)).thenReturn(aluno);

        mockMvc.perform(get("/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/editar"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().attribute("aluno", aluno));
    }

    @Test
    public void testUpdateAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Ana Costa");
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setMatricula("11223");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);


        doNothing().when(serviceAluno).save(any(Aluno.class));

       
        mockMvc.perform(post("/editar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testDeleteAluno() throws Exception {
        doNothing().when(serviceAluno).deleteById(1L);

        mockMvc.perform(get("/remover/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }
}
