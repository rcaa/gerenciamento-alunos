package br.com.gerenciamento.controller;

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

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {
	@Autowired
    private UsuarioRepository repositoryUsuario;
	@Autowired
    private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

    @Before
    public void setup()
	{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

	@Test
    public void testeSalvarUsuarios() throws Exception
	{
		this.mockMvc.perform(post("/salvarUsuarios").formField("Id", "1L").formField("Email", "okarun@gmail.com").formField("User", "Okarun").formField("Senha", "123"));
    }

	@Test
    public void testeLogin() throws Exception
	{
		Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("okarun@gmail.com");
        usuario.setUser("Okarun");
        usuario.setSenha("123");
        repositoryUsuario.save(usuario);
        this.mockMvc.perform(post("/login").param("user", "Okarun").param("senha", "123"));

    }

	@Test
    public void testeLogout() throws Exception
	{
		Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("okarun@gmail.com");
        usuario.setUser("Okarun");
        usuario.setSenha("123");
        repositoryUsuario.save(usuario);
        this.mockMvc.perform(post("/login").param("user", "Okarun").param("senha", "123"));
		this.mockMvc.perform(post("/logout"));
	}

	@Test
    public void testeIndex() throws Exception
	{

		mockMvc.perform(get("/index")).andExpect(view().name("home/index")).andExpect(model().attributeExists("aluno"));

    }

}