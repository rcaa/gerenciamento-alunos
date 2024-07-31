package br.com.gerenciamento.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insertAlunos() throws Exception{
        mockMvc.perform(get("/inserirAlunos")).andExpect(status().isOk()) 
               .andExpect(model().attributeExists("aluno")).andExpect(view().name("Aluno/formAluno"));
    }

    @Test
    public void inserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        mockMvc.perform(post("/InsertAlunos").flashAttr("aluno", aluno))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void filtroAlunos() throws Exception{
        mockMvc.perform(get("/filtro-alunos")).andExpect(status().isOk()) 
               .andExpect(view().name("Aluno/filtroAlunos"));
    }

    @Test
    public void listaAlunosAtivos() throws Exception{
        mockMvc.perform(get("/alunos-ativos")).andExpect(status().isOk()) 
               .andExpect(model().attributeExists("alunosAtivos")).andExpect(view().name("Aluno/alunos-ativos"));
    }
}