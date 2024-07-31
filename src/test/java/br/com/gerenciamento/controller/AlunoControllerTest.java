package br.com.gerenciamento.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        this.aluno.setNome("João");
        this.aluno.setStatus(Status.ATIVO);
        this.aluno.setTurno(Turno.NOTURNO);
        this.aluno.setCurso(Curso.ADMINISTRACAO);
    }

    @Test
    public void editarAluno() throws Exception {
        when(serviceAluno.getById(1L)).thenReturn(this.aluno);

        mockMvc.perform(get("/editar/1"))
            .andExpect(status().isOk());

        verify(serviceAluno, times(1)).getById(1L);
    }

    @Test
    public void ListarAlunos() throws Exception {
        when(serviceAluno.findAll()).thenReturn(Collections.singletonList(this.aluno));

        mockMvc.perform(get("/alunos-adicionados"))
            .andExpect(status().isOk());

        verify(serviceAluno, times(1)).findAll();
    }

    @Test
    public void RemoverAluno() throws Exception {
        doNothing().when(serviceAluno).deleteById(1L);

        mockMvc.perform(get("/remover/1"))
            .andExpect(status().is3xxRedirection());

        verify(serviceAluno, times(1)).deleteById(1L);
    }


    @Test
    public void deveMostrarPaginaDeErroQuandoAlunoNaoEncontrado() throws Exception {
        // Configura o comportamento do serviço para lançar uma exceção quando um aluno não for encontrado
        when(serviceAluno.getById(999L)).thenThrow(new RuntimeException("Aluno não encontrado"));
    
        mockMvc.perform(get("/alunos/999"))
            .andExpect(status().isNotFound());
    }
    
}
