package br.com.gerenciamento.controller;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;

@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceAluno serviceAluno;

    @Test
    public void testInsertAlunos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/inserirAlunos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Aluno/formAluno"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("aluno"));
    }

    @Test
    public void testListagemAlunos() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Vinicius");

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Pedro");

        when(serviceAluno.findAll()).thenReturn(Arrays.asList(aluno1, aluno2));

        mockMvc.perform(MockMvcRequestBuilders.get("/alunos-adicionados"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Aluno/listAlunos"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("alunosList"))
                .andExpect(MockMvcResultMatchers.model().attribute("alunosList", Arrays.asList(aluno1, aluno2)));
    }

    @Test
    public void testEditarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");

        when(serviceAluno.getById(1L)).thenReturn(aluno);

        mockMvc.perform(MockMvcRequestBuilders.get("/editar/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Aluno/editar"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("aluno"))
                .andExpect(MockMvcResultMatchers.model().attribute("aluno", aluno));
    }

    @Test
    public void testRemoverAluno() throws Exception {
        doNothing().when(serviceAluno).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/remover/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/alunos-adicionados"));

        verify(serviceAluno, times(1)).deleteById(1L);
    }
}
