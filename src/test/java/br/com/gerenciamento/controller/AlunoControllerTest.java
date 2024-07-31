package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc //Faz o trabalho do Before para configurar o MockMvc
public class AlunoControllerTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void removerAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/remover/" + aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void cadastrarAluno()throws Exception{

        this.mockMvc.perform(post("/InsertAlunos")
                        .formField("nome", "Teste")
                        .formField("curso", "INFORMATICA")
                        .formField("matricula", "2024")
                        .formField("turno", "NOTURNO")
                        .formField("status", "ATIVO"))
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    public void listarAlunos() throws Exception
    {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/alunos-adicionados"))
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attribute("alunosList", hasItem(hasProperty("nome", containsString("Vinicius")))));
    }

    @Test
    public void listaAlunosAtivos() throws Exception
    {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/alunos-ativos")).andExpect(view().name("Aluno/alunos-ativos"))
                .andExpect(model().attribute("alunosAtivos", hasItem(hasProperty("nome", containsString("Vinicius")))));
    }
}
