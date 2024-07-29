package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import br.com.gerenciamento.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        alunoRepository.deleteAll(); // Limpa o repositório antes de cada teste para evitar interferências.
    }

    @Test
    public void testCreateAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste Aluno");
        aluno.setMatricula("123456");

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(aluno)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Teste Aluno"));
    }

    @Test
    public void testGetAlunoById() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste Aluno");
        aluno.setMatricula("123456");
        alunoService.save(aluno);

        mockMvc.perform(get("/alunos/{id}", aluno.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Aluno"));
    }

    @Test
    public void testGetAllAlunos() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Teste Aluno 1");
        aluno1.setMatricula("123456");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Teste Aluno 2");
        aluno2.setMatricula("654321");

        alunoService.save(aluno1);
        alunoService.save(aluno2);

        mockMvc.perform(get("/alunos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testDeleteAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste Aluno");
        aluno.setMatricula("123456");
        alunoService.save(aluno);

        mockMvc.perform(delete("/alunos/{id}", aluno.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<Aluno> deletedAluno = alunoRepository.findById(aluno.getId());
        assertFalse(deletedAluno.isPresent());
    }

    @Test
    public void testUpdateAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste Aluno");
        aluno.setMatricula("123456");
        alunoService.save(aluno);

        Aluno updatedAluno = new Aluno();
        updatedAluno.setNome("Teste Aluno Atualizado");
        updatedAluno.setMatricula("123456");

        mockMvc.perform(put("/alunos/{id}", aluno.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedAluno)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Aluno Atualizado"));
    }
}
