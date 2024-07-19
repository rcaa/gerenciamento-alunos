package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ServiceAluno serviceAluno;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testInsertAlunos() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void testListagemAlunos() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Vinicius");
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Carlos");

        Mockito.when(serviceAluno.findAll()).thenReturn(Arrays.asList(aluno1, aluno2));

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void testEditar() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");

        Mockito.when(serviceAluno.getById(1L)).thenReturn(aluno);

        mockMvc.perform(get("/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/editar"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().attribute("aluno", aluno));
    }

    @Test
    public void testRemoverAluno() throws Exception {
        mockMvc.perform(get("/remover/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));

        Mockito.verify(serviceAluno, Mockito.times(1)).deleteById(1L);
    }
}
