package br.com.gerenciamento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ServiceAluno serviceAluno;

    private MockMvc mockMvc;

    private Aluno aluno = new Aluno();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        this.aluno.setId(1L);
        this.aluno.setMatricula("123456");
        this.aluno.setNome("alunoo");
        this.aluno.setStatus(Status.ATIVO);
        this.aluno.setTurno(Turno.MATUTINO);
        this.aluno.setCurso(Curso.CONTABILIDADE);
    }

    @Test
    public void insertAluno() throws Exception {
        this.mockMvc.perform(post("/InsertAlunos")
                            .param("nome", this.aluno.getNome())
                            .param("matricula", this.aluno.getMatricula())
                            .param("status", this.aluno.getStatus().name())
                            .param("turno", this.aluno.getTurno().name())
                            .param("curso", this.aluno.getCurso().name()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/alunos-adicionados"));

        verify(serviceAluno, times(1)).save(any(Aluno.class));
    }

    @Test
    public void editarAluno() throws Exception {
        when(serviceAluno.getById(1L)).thenReturn(this.aluno);

        this.mockMvc.perform(get("/editar/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("Aluno/editar"))
                    .andExpect(model().attributeExists("aluno"))
                    .andExpect(model().attribute("aluno", this.aluno));
    }

    @Test
    public void removerAluno() throws Exception {
        doNothing().when(serviceAluno).deleteById(1L);

        this.mockMvc.perform(get("/remover/1"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    public void listarAlunos() throws Exception {
        when(serviceAluno.findAll()).thenReturn(Collections.singletonList(this.aluno));
        
        this.mockMvc.perform(get("/alunos-adicionados"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("Aluno/listAlunos"))
                    .andExpect(model().attributeExists("alunosList"))
                    .andExpect(model().attribute("alunosList", Collections.singletonList(this.aluno)));
    }
}
