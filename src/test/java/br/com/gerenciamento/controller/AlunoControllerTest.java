package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceAluno serviceAluno;

    private Aluno alunoAtivo;
    private Aluno alunoInativo;

    @BeforeEach
    void setUp() {
        alunoAtivo = new Aluno();
        alunoAtivo.setId(1L);
        alunoAtivo.setNome("Vinicius");
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setMatricula("123456");
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setTurno(Turno.NOTURNO);

        alunoInativo = new Aluno();
        alunoInativo.setId(2L);
        alunoInativo.setNome("Carlos");
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("654321");
        alunoInativo.setCurso(Curso.ADMINISTRACAO);
        alunoInativo.setTurno(Turno.MATUTINO);
    }

    @Test
    public void listagemAlunos() throws Exception {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(alunoAtivo);
        alunos.add(alunoInativo);
        Mockito.when(serviceAluno.findAll()).thenReturn(alunos);

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attribute("alunosList", alunos));
    }

    @Test
    public void editarAluno() throws Exception {
        Mockito.when(serviceAluno.getById(1L)).thenReturn(alunoAtivo);

        mockMvc.perform(get("/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/editar"))
                .andExpect(model().attribute("aluno", alunoAtivo));
    }

    @Test
    public void removerAluno() throws Exception {
        mockMvc.perform(get("/remover/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void pesquisarAluno() throws Exception {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(alunoAtivo);
        Mockito.when(serviceAluno.findByNomeContainingIgnoreCase("Vinicius")).thenReturn(alunos);

        mockMvc.perform(post("/pesquisar-aluno").param("nome", "Vinicius"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/pesquisa-resultado"))
                .andExpect(model().attribute("ListaDeAlunos", alunos));
    }
}
