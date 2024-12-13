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
        alunoAtivo.setNome("Fulano Ativo");
        alunoAtivo.setMatricula("123456");
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        serviceAluno.save(alunoAtivo);

        alunoInativo = new Aluno();
        alunoInativo.setNome("Beltrano Inativo");
        alunoInativo.setMatricula("435126");
        alunoInativo.setCurso(Curso.DIREITO);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setTurno(Turno.NOTURNO);
        serviceAluno.save(alunoInativo);
    }

    // #1 inserir um aluno com sucesso
    @Test
    public void testInserirAluno() throws Exception {
        String alunoJson = """
                {
                    "nome": "Joazinho",
                    "matricula": "12345",
                    "curso": "ADMINISTRACAO",
                    "status": "ATIVO",
                    "turno": "MATUTINO"
                }
                """;

        mockMvc.perform(post("/InsertAlunos").contentType(MediaType.APPLICATION_JSON).content(alunoJson)).andExpect(status().is2xxSuccessful());
    }

    // #2 listar todos os alunos adicionados (no setup)
    @Test
    public void testListagemAlunos() throws Exception {
        mockMvc.perform(get("/alunos-adicionados")).andExpect(status().isOk()).andExpect(view().name("Aluno/listAlunos")).andExpect(content().string(containsString(alunoAtivo.getNome()))).andExpect(content().string(containsString(alunoInativo.getNome())));
    }

    // #3 listar apenas os alunos com status ativo
    @Test
    public void testListaAlunosAtivos() throws Exception {
        mockMvc.perform(get("/alunos-ativos")).andExpect(status().isOk()).andExpect(view().name("Aluno/alunos-ativos")).andExpect(content().string(containsString(alunoAtivo.getNome()))).andExpect(content().string(org.hamcrest.Matchers.not(containsString(alunoInativo.getNome()))));
    }

    // #4 listar apenas os alunos com status inativo
    @Test
    public void testListaAlunosInativos() throws Exception {
        mockMvc.perform(get("/alunos-inativos")).andExpect(status().isOk()).andExpect(view().name("Aluno/alunos-inativos")).andExpect(content().string(containsString(alunoInativo.getNome()))).andExpect(content().string(org.hamcrest.Matchers.not(containsString(alunoAtivo.getNome()))));
    }
}
