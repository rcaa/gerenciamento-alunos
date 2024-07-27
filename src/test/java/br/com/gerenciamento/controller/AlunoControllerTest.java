package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    @Transactional
    public void testListagemAlunos() throws Exception {
        // Configura os alunos no banco de dados
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
    public void testGetInserirAluno() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().attribute("aluno", instanceOf(Aluno.class)));
    }

    @Test
    @Transactional
    public void testEditarAluno() throws Exception {
        // Configura o aluno inicial
        Aluno aluno = new Aluno();
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("123456");
        aluno.setNome("Carlos");
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);

        // Realiza a operação de edição
        mockMvc.perform(post("/editar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", aluno.getId().toString())
                .param("turno", aluno.getTurno().name())
                .param("curso", aluno.getCurso().name())
                .param("matricula", aluno.getMatricula())
                .param("nome", "Carlos Editado")
                .param("status", aluno.getStatus().name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        // Verifica se a alteração foi refletida
        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("alunosList", hasSize(1)))
                .andExpect(model().attribute("alunosList", hasItem(
                        hasProperty("nome", is("Carlos Editado"))
                )));
    }

    @Test
    @Transactional
    public void testRemoverAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("123456");
        aluno.setNome("Maria");
        aluno.setStatus(Status.ATIVO);
        serviceAluno.save(aluno);

        mockMvc.perform(get("/remover/" + aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("alunosList", hasSize(0)));
    }
}
