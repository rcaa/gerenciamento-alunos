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

    @Autowired
    private ServiceAluno serviceAluno;

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

    @Test
    public void listagemAlunos() {
        // Cadastro de um aluno para teste
        Aluno novoAluno = new Aluno();
        novoAluno.setNome("Maria Silva");
        novoAluno.setMatricula("654321");
        novoAluno.setCurso(Curso.INFORMATICA);
        novoAluno.setStatus(Status.ATIVO);
        novoAluno.setTurno(Turno.MATUTINO);
        this.serviceAluno.save(novoAluno);

        // Verifica se a listagem de alunos está funcionando corretamente
        ResponseEntity<String> response = testRestTemplate.getForEntity("/alunos-adicionados", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody().contains("Maria Silva"));
    }

    // @Test
    // public void editar() {
    // }

    // @Test
    // public void removerAluno() {
    // }
}
