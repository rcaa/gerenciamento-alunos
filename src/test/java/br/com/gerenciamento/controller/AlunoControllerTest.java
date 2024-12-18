package br.com.gerenciamento.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {
	@Autowired
    private AlunoRepository alunoRepository;
	@Autowired
    private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

    @Before
    public void setup()
	{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

	@Test
    public void testeInserirAlunos() throws Exception
	{
		this.mockMvc.perform(post("/InsertAlunos").formField("nome", "Okarun").formField("curso", "ADMINISTRACAO").formField("matricula", "123").formField("turno", "MATUTINO").formField("status", "ATIVO")).andExpect(view().name("redirect:/alunos-adicionados"));
	}

	@Test
    public void testeListagemAlunos() throws Exception
	{
		Aluno aluno = new Aluno();
        aluno.setId(1L);
		aluno.setNome("Okarun");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123");
		this.alunoRepository.save(aluno);
		this.mockMvc.perform(get("/alunos-adicionados")).andExpect(view().name("Aluno/listAlunos")).andExpect(model().attribute("alunosList", hasItem(hasProperty("nome", containsString("Okarun")))));
	}

	@Test
    public void testeListaAlunosAtivos() throws Exception
	{
		Aluno aluno = new Aluno();
        aluno.setId(1L);
		aluno.setNome("Okarun");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123");
		this.alunoRepository.save(aluno);
		this.mockMvc.perform(get("/alunos-ativos")).andExpect(view().name("Aluno/alunos-ativos")).andExpect(model().attribute("alunosAtivos", hasItem(hasProperty("nome", containsString("Okarun")))));
	}

	@Test
    public void testePesquisarAluno() throws Exception
	{
		Aluno aluno = new Aluno();
        aluno.setId(1L);
		aluno.setNome("Okarun");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123");
		this.alunoRepository.save(aluno);
		this.mockMvc.perform(post("/pesquisar-aluno").param("nome", "Okarun")).andExpect(view().name("Aluno/pesquisa-resultado")).andExpect(model().attribute("ListaDeAlunos", hasItem(hasProperty("nome", containsString("Okarun")))));
	}
}