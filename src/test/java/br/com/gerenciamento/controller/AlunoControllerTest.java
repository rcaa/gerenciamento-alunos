package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	@MockBean
	private ServiceAluno serviceAluno;

	private Aluno aluno;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		aluno = new Aluno();
		aluno.setNome("Pedro");
		aluno.setTurno(Turno.NOTURNO);
		aluno.setCurso(Curso.INFORMATICA);
		aluno.setStatus(Status.ATIVO);
		aluno.setMatricula("1111");
	}

	@Test
	public void inserirAlunos() throws Exception {
		mockMvc.perform(post("/InsertAlunos")
						.param("nome", aluno.getNome())
						.param("matricula", aluno.getMatricula())
						.param("curso", aluno.getCurso().name())
						.param("status", aluno.getStatus().name())
						.param("turno", aluno.getTurno().name()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/alunos-adicionados"));

		verify(serviceAluno).save(any(Aluno.class));
	}

	@Test
	public void inserirAlunoInvalidData() throws Exception {
		aluno.setNome("P");
		aluno.setMatricula("1");

		mockMvc.perform(post("/InsertAlunos")
						.param("nome", aluno.getNome())
						.param("matricula", aluno.getMatricula())
						.param("curso", aluno.getCurso().name())
						.param("status", aluno.getStatus().name())
						.param("turno", aluno.getTurno().name()))
				.andExpect(status().isOk())
				.andExpect(view().name("Aluno/formAluno"))
				.andExpect(model().attributeHasFieldErrors("aluno"));

		verify(serviceAluno, never()).save(any(Aluno.class));
	}

	@Test
	public void listagemAlunos() throws Exception {
		when(serviceAluno.findAll()).thenReturn(List.of(aluno));

		mockMvc.perform(get("/alunos-adicionados"))
				.andExpect(status().isOk())
				.andExpect(view().name("Aluno/listAlunos"))
				.andExpect(model().attributeExists("alunosList"))
				.andExpect(model().attribute("alunosList", List.of(aluno)));
	}

	@Test
	public void editar() throws Exception {
		when(serviceAluno.getById(1L)).thenReturn(aluno);

		mockMvc.perform(get("/editar/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("Aluno/editar"))
				.andExpect(model().attributeExists("aluno"))
				.andExpect(model().attribute("aluno", aluno));
	}

}
