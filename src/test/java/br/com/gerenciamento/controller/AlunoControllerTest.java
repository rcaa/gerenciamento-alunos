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
    public void testInserirAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", "Carlos Almeida")
                .param("curso", Curso.INFORMATICA.toString())
                .param("matricula", "369585")
                .param("turno", Turno.NOTURNO.toString())
                .param("status", Status.ATIVO.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        List<Aluno> alunos = serviceAluno.findAll();
        Aluno aluno = alunos.stream().filter(user -> "Carlos Almeida".equals(user.getNome())).findFirst().orElse(null);

        assert aluno != null;

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alunosList"))
                .andExpect(model().attribute("alunosList", hasSize(1)))
                .andExpect(model().attribute("alunosList", hasItem(
                        hasProperty("nome", is("Carlos Almeida"))
                )));
    }

    @Test
    public void testEditarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("123456");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);

        mockMvc.perform(post("/editar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(aluno.getId()))
                .param("nome", "João da Silva")
                .param("curso", Curso.CONTABILIDADE.toString())
                .param("matricula", "654321")
                .param("turno", Turno.NOTURNO.toString())
                .param("status", Status.INATIVO.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alunosList"))
                .andExpect(model().attribute("alunosList", hasSize(1)))
                .andExpect(model().attribute("alunosList", hasItem(
                        allOf(
                                hasProperty("id", is(aluno.getId())),
                                hasProperty("nome", is("João da Silva")),
                                hasProperty("curso", is(Curso.CONTABILIDADE)),
                                hasProperty("matricula", is("654321")),
                                hasProperty("turno", is(Turno.NOTURNO)),
                                hasProperty("status", is(Status.INATIVO))
                        )
                )));
    }

    @Test
    public void testRemoverAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria Souza");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("987654");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);

        mockMvc.perform(get("/remover/{id}", aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alunosList"))
                .andExpect(model().attribute("alunosList", hasSize(0)));
    }

    @Test
    public void testListarAlunos() throws Exception {
        // Create a new aluno
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Pedro Oliveira");
        aluno1.setCurso(Curso.BIOMEDICINA);
        aluno1.setMatricula("111111");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setStatus(Status.ATIVO);
        serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Pedro Henrique");
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setMatricula("222222");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setStatus(Status.INATIVO);
        serviceAluno.save(aluno2);

        // Perform the list operation
        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alunosList"))
                .andExpect(model().attribute("alunosList", hasSize(2)))
                .andExpect(model().attribute("alunosList", hasItems(
                        allOf(
                                hasProperty("nome", is("Pedro Oliveira")),
                                hasProperty("curso", is(Curso.BIOMEDICINA)),
                                hasProperty("matricula", is("111111")),
                                hasProperty("turno", is(Turno.MATUTINO)),
                                hasProperty("status", is(Status.ATIVO))
                        ),
                        allOf(
                                hasProperty("nome", is("Pedro Henrique")),
                                hasProperty("curso", is(Curso.ADMINISTRACAO)),
                                hasProperty("matricula", is("222222")),
                                hasProperty("turno", is(Turno.NOTURNO)),
                                hasProperty("status", is(Status.INATIVO))
                        )
                )));
    }

}
