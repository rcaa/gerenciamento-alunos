package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceAluno serviceAluno;

    private Aluno alunoAtivo;
    private Aluno alunoInativo;

    @BeforeEach
    void setUp() {
        alunoAtivo = new Aluno();
        alunoAtivo.setNome("Luis Felipe");
        alunoAtivo.setMatricula("123456");
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        serviceAluno.save(alunoAtivo);

        alunoInativo = new Aluno();
        alunoInativo.setNome("Maria Fernanda");
        alunoInativo.setMatricula("435126");
        alunoInativo.setCurso(Curso.DIREITO);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setTurno(Turno.NOTURNO);
        serviceAluno.save(alunoInativo);
    }

    // #1 Inserir um aluno com sucesso
    @Test
    public void testInserirAluno() throws Exception {
        String alunoJson = """
                {
                    "nome": "Gustavo Henrique",
                    "matricula": "12345",
                    "curso": "ADMINISTRACAO",
                    "status": "ATIVO",
                    "turno": "MATUTINO"
                }
                """;

        mockMvc.perform(post("/InsertAlunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(alunoJson))
                .andExpect(status().is2xxSuccessful());
    }

    // #2 Pesquisar alunos por nome
    @Test
    public void testPesquisarAlunoPorNome() throws Exception {
        mockMvc.perform(post("/pesquisar-aluno")
                        .param("nome", "Luis Felipe"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/pesquisa-resultado"))
                .andExpect(content().string(containsString("Luis Felipe")));
    }

    // #3 Listar apenas os alunos com status inativo
    @Test
    public void testListaAlunosInativos() throws Exception {
        mockMvc.perform(get("/alunos-inativos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/alunos-inativos"))
                .andExpect(content().string(containsString(alunoInativo.getNome())))
                .andExpect(content().string(org.hamcrest.Matchers.not(containsString(alunoAtivo.getNome()))));
    }

    // #4 Remover aluno
    @Test
    public void testRemoverAluno() throws Exception {
        mockMvc.perform(get("/remover/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }
}
