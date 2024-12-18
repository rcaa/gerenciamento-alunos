package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceAluno serviceAluno;

    @InjectMocks
    private AlunoController alunoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    public void ListagemAlunos() throws Exception {
        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"));
    }

    @Test
    public void pesquisarAluno() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("biruleibe");
        aluno1.setMatricula("001");
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.NOTURNO);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Tanajura");
        aluno2.setMatricula("002");
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setTurno(Turno.MATUTINO);

        when(serviceAluno.findByNomeContainingIgnoreCase("Tanajura")).thenReturn(List.of(aluno2));

        mockMvc.perform(post("/pesquisar-aluno")
                .param("nome", "Tanajura"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ListaDeAlunos"))
                .andExpect(model().attribute("ListaDeAlunos", hasItem(hasProperty("nome", is("Tanajura")))));
    }

    @Test
    void testRemoverAluno() throws Exception {
        Long alunoId = 1L;
        mockMvc.perform(get("/remover/{id}", alunoId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    void SalvarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Aristeu");
        aluno.setMatricula("001");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);

        Mockito.doNothing().when(serviceAluno).save(Mockito.any(Aluno.class));

        mockMvc.perform(post("/InsertAlunos")
                .flashAttr("aluno", aluno))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }
}