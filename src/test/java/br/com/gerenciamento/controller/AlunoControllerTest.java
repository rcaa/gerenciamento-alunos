package br.com.gerenciamento.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository alunoRepository;
    
    @Test
public void testAdicionarAluno() throws Exception {
    Aluno aluno = new Aluno();
    aluno.setNome("João");
    aluno.setTurno(Turno.NOTURNO);
    aluno.setCurso(Curso.ADMINISTRACAO);
    aluno.setMatricula("123456");

    mockMvc.perform(post("/alunos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(aluno)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nome").value("João"))
            .andExpect(jsonPath("$.turno").value(Turno.NOTURNO.name()))
            .andExpect(jsonPath("$.curso").value(Curso.ADMINISTRACAO.name()))
            .andExpect(jsonPath("$.matricula").value("123456"));
}

private static String asJsonString(final Object obj) {
    try {
        return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

@Test
public void testBuscarAlunoPorId() throws Exception {
    Aluno aluno = new Aluno();
    aluno.setNome("Maria");
    aluno.setTurno(Turno.MATUTINO);
    aluno.setCurso(Curso.DIREITO);
    aluno.setMatricula("654321");
    alunoRepository.save(aluno);

    mockMvc.perform(get("/alunos/{id}", aluno.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Maria"))
            .andExpect(jsonPath("$.turno").value(Turno.MATUTINO.name()))
            .andExpect(jsonPath("$.curso").value(Curso.DIREITO.name()))
            .andExpect(jsonPath("$.matricula").value("654321"));
}
@Test
public void testAtualizarAluno() throws Exception {
    Aluno aluno = new Aluno();
    aluno.setNome("Carlos");
    aluno.setTurno(Turno.MATUTINO);
    aluno.setCurso(Curso.BIOMEDICINA);
    aluno.setMatricula("789012");
    alunoRepository.save(aluno);

    aluno.setNome("Carlos Silva");
    aluno.setTurno(Turno.NOTURNO);

    mockMvc.perform(put("/alunos/{id}", aluno.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(aluno)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Carlos Silva"))
            .andExpect(jsonPath("$.turno").value(Turno.NOTURNO.name()))
            .andExpect(jsonPath("$.curso").value(Curso.ENFERMAGEM.name()))
            .andExpect(jsonPath("$.matricula").value("789012"));
}
@Test
public void testDeletarAluno() throws Exception {
    Aluno aluno = new Aluno();
    aluno.setNome("Ana");
    aluno.setTurno(Turno.NOTURNO);
    aluno.setCurso(Curso.DIREITO);
    aluno.setMatricula("345678");
    alunoRepository.save(aluno);

    mockMvc.perform(delete("/alunos/{id}", aluno.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    mockMvc.perform(get("/alunos/{id}", aluno.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
}


}
