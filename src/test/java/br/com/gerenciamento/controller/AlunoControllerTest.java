package br.com.gerenciamento.controller;

import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

import jakarta.transaction.Transactional;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void inserirAluno() throws Exception {
        // Verifica a inserção de um aluno
        this.mockMvc.perform(
                post("/InsertAlunos")
                        .formField("nome", "Mayara")
                        .formField("curso", "INFORMATICA")
                        .formField("matricula", "654321")
                        .formField("turno", "NOTURNO")
                        .formField("status", "ATIVO")
        ).andExpect(
                view().name("redirect:/alunos-adicionados")
        );
    }

    @Test
    @Transactional
    public void editarAluno() throws Exception {
        // Verifica a edição de um aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Mayara");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setMatricula("654321");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);
        aluno = alunoRepository.save(aluno);

        this.mockMvc.perform(
                post("/editar")
                        .param("id", aluno.getId().toString())
                        .param("nome", "Mayara")
                        .param("curso", "ADMINISTRACAO")
                        .param("matricula", "654321")
                        .param("turno", "MATUTINO")
                        .param("status", "INATIVO")
        ).andExpect(
                view().name("redirect:/alunos-adicionados")
        );

        // Verifica se as alterações foram salvas
        Aluno alunoAtualizado = alunoRepository.findById(aluno.getId()).orElse(null);
        assertNotNull(alunoAtualizado);
        assertEquals("Mayara", alunoAtualizado.getNome());
        assertEquals(Turno.MATUTINO, alunoAtualizado.getTurno());
        assertEquals(Status.INATIVO, alunoAtualizado.getStatus());
    }

    @Test
    @Transactional
    public void removerAluno() throws Exception {
        // Verifica a remoção de um aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Mayara");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setMatricula("654321");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);
        aluno = alunoRepository.save(aluno);

        this.mockMvc.perform(
                get("/remover/{id}", aluno.getId())
        ).andExpect(
                redirectedUrl("/alunos-adicionados")
        );

        Aluno alunoRemovido = alunoRepository.findById(aluno.getId()).orElse(null);
        assertNull(alunoRemovido); // Verifica se o aluno foi removido, deve ser nulo pois não existe mais
    }

    @Test
    @Transactional
    public void listarAlunosAtivos() throws Exception {
        // Cria um aluno ativo e um inativo para verificar se a lista de alunos ativos é criada corretamente
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Mayara");
        alunoAtivo.setCurso(Curso.INFORMATICA);
        alunoAtivo.setMatricula("654321");
        alunoAtivo.setTurno(Turno.NOTURNO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoRepository.save(alunoAtivo);

        Aluno alunoInativo = new Aluno();
        alunoInativo.setNome("Karol");
        alunoInativo.setCurso(Curso.BIOMEDICINA);
        alunoInativo.setMatricula("123456");
        alunoInativo.setTurno(Turno.MATUTINO);
        alunoInativo.setStatus(Status.INATIVO);
        alunoRepository.save(alunoInativo);

        // Verifica se a lista de alunos ativos é exibida corretamente
        this.mockMvc.perform(
                get("/alunos-ativos")
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("Aluno/alunos-ativos")
        ).andExpect(
                model().attribute("alunosAtivos", hasItem(
                        allOf(
                                hasProperty("nome", is(alunoAtivo.getNome())),
                                hasProperty("status", is(Status.ATIVO))
                        )
                ))
        );
    }
}