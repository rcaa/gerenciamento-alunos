package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired
    private ObjectMapper objectMapper;

    // 1. Testa o endpoint para inserir um aluno
    @Test
    public void testInserirAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos Silva");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("MAT123");

        mockMvc.perform(post("/InsertAlunos")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", aluno.getNome())
                .param("turno", aluno.getTurno().name())
                .param("curso", aluno.getCurso().name())
                .param("status", aluno.getStatus().name())
                .param("matricula", aluno.getMatricula()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    // 2. Testa o endpoint para listar alunos
    @Test
    public void testListagemAlunos() throws Exception {
        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    // 3. Testa o endpoint para editar um aluno
    @Test
    public void testEditarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana Sousa");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("MAT456");
        serviceAluno.save(aluno);

        mockMvc.perform(get("/editar/{id}", aluno.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/editar"))
                .andExpect(model().attributeExists("aluno"));
    }

    // 4. Testa o endpoint para remover um aluno
    @Test
    public void testRemoverAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Pedro Costa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("MAT789");
        serviceAluno.save(aluno);

        mockMvc.perform(get("/remover/{id}", aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }
}
