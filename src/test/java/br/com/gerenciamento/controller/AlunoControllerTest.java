package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    @Transactional
    public void testInserirAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", "João Silva")
                .param("curso", Curso.ADMINISTRACAO.toString())
                .param("matricula", "123456")
                .param("turno", Turno.NOTURNO.toString())
                .param("status", Status.ATIVO.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        List<Aluno> alunos = serviceAluno.findAll();
        Aluno aluno = alunos.stream().filter(a -> "João Silva".equals(a.getNome())).findFirst().orElse(null);

        assert aluno != null;

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alunosList"))
                .andExpect(model().attribute("alunosList", hasSize(1)))
                .andExpect(model().attribute("alunosList", hasItem(
                        hasProperty("nome", is("João Silva"))
                )));
    }

    @Test
    @Transactional
    public void testListagemAlunos() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setMatricula("123456");
        aluno1.setNome("Carlos");
        aluno1.setStatus(Status.ATIVO);
        serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setMatricula("1234567");
        aluno2.setNome("Maria");
        aluno2.setStatus(Status.INATIVO);
        serviceAluno.save(aluno2);

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alunosList"))
                .andExpect(model().attribute("alunosList", hasSize(2)))
                .andExpect(model().attribute("alunosList", hasItem(
                        hasProperty("nome", is("Carlos"))
                )))
                .andExpect(model().attribute("alunosList", hasItem(
                        hasProperty("nome", is("Maria"))
                )));
    }

    @Test
    @Transactional
    public void testEditarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("123456");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);

        List<Aluno> alunos = serviceAluno.findAll();
        Aluno savedAluno = alunos.stream().filter(a -> "João Silva".equals(a.getNome())).findFirst().orElse(null);

        assert savedAluno != null;
        savedAluno.setNome("João da Silva");
        serviceAluno.save(savedAluno);

        mockMvc.perform(post("/editar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", savedAluno.getId().toString())
                .param("nome", savedAluno.getNome())
                .param("curso", savedAluno.getCurso().toString())
                .param("matricula", savedAluno.getMatricula())
                .param("turno", savedAluno.getTurno().toString())
                .param("status", savedAluno.getStatus().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        mockMvc.perform(get("/editar/" + savedAluno.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/editar"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().attribute("aluno", hasProperty("nome", is("João da Silva"))));
    }

    @Test
    @Transactional
    public void testRemoverAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("123456");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);

        List<Aluno> alunos = serviceAluno.findAll();
        Aluno savedAluno = alunos.stream().filter(a -> "João Silva".equals(a.getNome())).findFirst().orElse(null);

        assert savedAluno != null;

        mockMvc.perform(get("/remover/" + savedAluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alunosList"))
                .andExpect(model().attribute("alunosList", hasSize(0)));
    }
}
