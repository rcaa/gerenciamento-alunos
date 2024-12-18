package br.com.gerenciamento.controller;

import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void inserirAluno() {
        // Cria um novo aluno para teste
        Aluno novoAluno = new Aluno();
        novoAluno.setNome("João Silva");
        novoAluno.setMatricula("123456");
        novoAluno.setCurso(Curso.INFORMATICA);
        novoAluno.setStatus(Status.ATIVO);
        novoAluno.setTurno(Turno.MATUTINO);

        // Insere o aluno no banco de dados
        ResponseEntity<String> response = testRestTemplate.postForEntity("/InsertAlunos", novoAluno, String.class);

        // Verifica se o aluno foi inserido corretamente
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // @Test
    // public void listagemAlunos() {
    // }

    // @Test
    // public void editar() {
    // }

    // @Test
    // public void removerAluno() {
    // }
}
