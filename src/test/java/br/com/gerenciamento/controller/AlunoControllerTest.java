package br.com.gerenciamento.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerenciamento.service.ServiceAluno;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceAluno serviceAluno;


    @Test
    @Transactional
    public void InsertAlunos() throws Exception {
        mockMvc.perform(
            //simular uma requisição get para a URL /InsertAlunos
            MockMvcRequestBuilders.get("/inserirAlunos"))
               //garantir que a resposta foi bem sucedida
               .andExpect(MockMvcResultMatchers.status().isOk())
               // Verifica se a visão retornada pelo controlador é a "Aluno/formAluno"
               .andExpect(MockMvcResultMatchers.view().name("Aluno/formAluno"))
               //Verifica se o modelo da visão contém um atributo chamado "aluno".
               .andExpect(MockMvcResultMatchers.model().attributeExists("aluno"));
    }

    @Test
    @Transactional
    public void listagemAlunos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/alunos-adicionados"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("Aluno/listAlunos"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("alunosList"));
    }

    @Test
    @Transactional
    public void removerAluno() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/remover/1"))
               .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
               .andExpect(MockMvcResultMatchers.view().name("redirect:/alunos-adicionados"));
    }

    @Test
    @Transactional
    public void filtroAlunos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/filtro-alunos"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("Aluno/filtroAlunos"));
    }

    @Test
    @Transactional
    public void listaAlunosAtivos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/alunos-ativos"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("Aluno/alunos-ativos"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("alunosAtivos"));
    }

    @Test
    @Transactional
    public void listaAlunosInativos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/alunos-inativos"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("Aluno/alunos-inativos"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("alunosInativos"));
    }
}
