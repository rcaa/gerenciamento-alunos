package br.com.gerenciamento.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest
{
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
	@Transactional
    public void inserirAlunos() throws Exception
	{
		this.mockMvc.perform(post("/InsertAlunos").formField("nome", "aaaaa").formField("curso", "ADMINISTRACAO").formField("matricula", "aaaaa").formField("turno", "MATUTINO").formField("status", "ATIVO")).andExpect(view().name("redirect:/alunos-adicionados"));
	}

	@Test
	@Transactional
    public void listagemAlunos() throws Exception
	{
		Aluno aluno = new Aluno();
		aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
		this.alunoRepository.save(aluno);
		this.mockMvc.perform(get("/alunos-adicionados")).andExpect(view().name("Aluno/listAlunos")).andExpect(model().attribute("alunosList", hasItem(hasProperty("nome", containsString("aaaaa")))));
	}

	@Test
	@Transactional
    public void listaAlunosAtivos() throws Exception
	{
		Aluno aluno = new Aluno();
		aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
		this.alunoRepository.save(aluno);
		this.mockMvc.perform(get("/alunos-ativos")).andExpect(view().name("Aluno/alunos-ativos")).andExpect(model().attribute("alunosAtivos", hasItem(hasProperty("nome", containsString("aaaaa")))));
	}

	@Test
	@Transactional
    public void pesquisarAluno() throws Exception
	{
		Aluno aluno = new Aluno();
		aluno.setNome("aaaaa");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaa");
		this.alunoRepository.save(aluno);
		this.mockMvc.perform(post("/pesquisar-aluno").param("nome", "aaaaa")).andExpect(view().name("Aluno/pesquisa-resultado")).andExpect(model().attribute("ListaDeAlunos", hasItem(hasProperty("nome", containsString("aaaaa")))));
	}
}