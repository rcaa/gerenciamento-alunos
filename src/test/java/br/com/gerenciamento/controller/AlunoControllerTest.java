package br.com.gerenciamento.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void inserirAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        this.alunoController.insertAlunos();

        ResponseEntity<String> response = restTemplate.postForEntity("/InsertAlunos", aluno, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void listarAlunos() {
        ServiceAluno serviceAluno = new ServiceAluno();

        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Vinicius");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(1L);
        aluno2.setNome("João da Silva");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("294830");
        serviceAluno.save(aluno2);

        ResponseEntity<String> response = restTemplate.getForEntity("/alunos-adicionados", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Vinicius"));
        assertTrue(response.getBody().contains("João da Silva"));
    }

    @Test
    public void editarAluno() {
        ServiceAluno serviceAluno = new ServiceAluno();

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        serviceAluno.save(aluno);

        aluno.setNome("Aluno Editado");
        restTemplate.postForEntity("/editar", aluno, String.class);

        Aluno alunoAtualizado = serviceAluno.getById(aluno.getId());
        assertEquals("Aluno Editado", alunoAtualizado.getNome());
    }

    @Test
    public void excluirAluno() {
        ServiceAluno serviceAluno = new ServiceAluno();

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        serviceAluno.save(aluno);
        restTemplate.getForEntity("/remover/" + aluno.getId(), String.class);
        assertFalse(serviceAluno.findAll().contains(aluno));
    }
}
