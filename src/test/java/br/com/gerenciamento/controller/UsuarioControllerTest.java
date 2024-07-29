package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
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

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.*;
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
		usuario.setUser("Rafael");
		usuario.setEmail("Rafael@gmail.com");
		usuario.setSenha("1234");
	}

    @Test
    public void TestcadastroPageIsAcessible() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cadastro"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login/cadastro"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("usuario"));
    }
    

    @Test
    public void testSalvarUsuario() throws Exception {
    
        mockMvc.perform(MockMvcRequestBuilders.post("/salvarUsuario")
                .flashAttr("usuario", usuario)) 
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())  
                .andExpect(MockMvcResultMatchers.view().name("redirect:/")); 

        Mockito.verify(serviceUsuario, Mockito.times(1)).salvarUsuario(Mockito.any(Usuario.class));
    }

	@Test
    public void testLogin() throws Exception {
   
    Mockito.when(serviceUsuario.loginUser(Mockito.eq(usuario.getUser()), Mockito.anyString())).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("user", usuario.getUser())
                .param("senha", usuario.getSenha()))
                .andExpect(MockMvcResultMatchers.status().isOk())  
                .andExpect(MockMvcResultMatchers.view().name("home/index"))  
             .andExpect(MockMvcResultMatchers.model().attributeExists("aluno"));  
}


    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

}

