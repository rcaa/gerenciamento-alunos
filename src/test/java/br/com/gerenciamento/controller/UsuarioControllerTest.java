package br.com.gerenciamento.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest
{
	@Autowired
    private UsuarioRepository usuarioRepository;
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
    public void index() throws Exception
	{
		this.mockMvc.perform(get("/index")).andExpect(view().name("home/index"));
	}

	@Test
	@Transactional
    public void cadastrar() throws Exception
	{
		this.mockMvc.perform(post("/salvarUsuario").formField("email", "aaaaa@a.a").formField("user", "aaaaa").formField("senha", "aaaaa")).andExpect(view().name("redirect:/"));
	}

	@Test
	@Transactional
    public void loginInvalid() throws Exception
	{
		Usuario usuario = new Usuario();
		usuario.setEmail("aaaaa@a.a");
        usuario.setUser("aaaaa");
        usuario.setSenha("aaaaa");
		this.usuarioRepository.save(usuario);
		this.mockMvc.perform(post("/login").formField("email", "aaaaa@a.a").formField("user", "aaaaa").formField("senha", "aaaaa")).andExpect(view().name("login/cadastro"));
	}

	@Test
	@Transactional
    public void logout() throws Exception
	{
		this.mockMvc.perform(post("/logout")).andExpect(view().name("login/login"));
	}
}