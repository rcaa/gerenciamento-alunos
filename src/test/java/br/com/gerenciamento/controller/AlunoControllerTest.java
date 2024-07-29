package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
// import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
		aluno.setNome("Rafael");
		aluno.setTurno(Turno.NOTURNO);
		aluno.setCurso(Curso.INFORMATICA);
		aluno.setStatus(Status.ATIVO);
		aluno.setMatricula("1234");
	}

	@Test
    public void inserirAluno() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/InsertAlunos")
                .flashAttr("aluno", aluno)) 
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) 
                .andExpect(MockMvcResultMatchers.view().name("redirect:/alunos-adicionados"));  
        Mockito.verify(serviceAluno, Mockito.times(1)).save(Mockito.any(Aluno.class));
    }


    @Test
    public void removerAluno() throws Exception {
        Mockito.doNothing().when(serviceAluno).deleteById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/remover/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())  
                .andExpect(MockMvcResultMatchers.view().name("redirect:/alunos-adicionados")); 

        Mockito.verify(serviceAluno, Mockito.times(1)).deleteById(1L);
    }

	@Test
	public void listarAlunos() throws Exception {
        when(serviceAluno.findAll()).thenReturn(Collections.singletonList(this.aluno));

        this.mockMvc.perform(get("/alunos-adicionados"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("Aluno/listAlunos"))
                    .andExpect(model().attributeExists("alunosList"))
                    .andExpect(model().attribute("alunosList", Collections.singletonList(this.aluno)));
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