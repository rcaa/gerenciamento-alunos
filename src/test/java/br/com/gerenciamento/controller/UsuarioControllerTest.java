package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@MockBean
	private ServiceUsuario serviceUsuario;

	private Usuario usuario;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		usuario = new Usuario();
		usuario.setUser("pedro");
		usuario.setEmail("pedro@email.com");
		usuario.setSenha("1234");
	}

	@Test
	public void cadastrarPage() throws Exception {
		mockMvc.perform(get("/cadastro"))
				.andExpect(status().isOk())
				.andExpect(view().name("login/cadastro"))
				.andExpect(model().attributeExists("usuario"));
	}

	@Test
	public void salvarUsuario() throws Exception {
		mockMvc.perform(post("/salvarUsuario")
						.param("user", usuario.getUser())
						.param("senha", usuario.getSenha()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));

		verify(serviceUsuario).salvarUsuario(any(Usuario.class));
	}

	@Test
    public void login() throws Exception {
        when(serviceUsuario.loginUser(eq(usuario.getUser()), anyString())).thenReturn(usuario);

        mockMvc.perform(post("/login")
        				.param("user", usuario.getUser())
        				.param("senha", usuario.getSenha()))
        		.andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }

	@Test
	public void testLogout() throws Exception {
		mockMvc.perform(post("/logout"))
				.andExpect(status().isOk())
				.andExpect(view().name("login/login"))
				.andExpect(model().attributeExists("usuario"));
	}
}
