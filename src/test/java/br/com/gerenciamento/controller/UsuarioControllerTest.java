package br.com.gerenciamento.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean 
    private ServiceUsuario serviceUsuario;

    private MockMvc mockMvc;

    private Usuario usuario = new Usuario();

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        usuario.setId(1L);
        usuario.setEmail("usuario@hotmail.com");
        usuario.setUser("usuario");
        usuario.setSenha("123456");
    }

    @Test
    public void salvarUsuario() throws Exception {
        this.mockMvc.perform(post("/salvarUsuario")
                        .param("user", usuario.getUser())
                        .param("senha", usuario.getSenha()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        
        verify(serviceUsuario, times(1)).salvarUsuario(any(Usuario.class));  
    }

    @Test
    public void fazerLogin() throws Exception {
        when(serviceUsuario.loginUser(anyString(), anyString())).thenReturn(usuario);

        this.mockMvc.perform(post("/login")
                            .param("user", usuario.getUser())
                            .param("senha", usuario.getSenha()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("home/index"))
                    .andExpect(model().attributeExists("aluno"));

        verify(serviceUsuario, times(1)).loginUser(anyString(), anyString());
    }

    @Test
    public void cadastrar() throws Exception {
        doNothing().when(serviceUsuario).salvarUsuario(any(Usuario.class));

        this.mockMvc.perform(post("/salvarUsuario")
                        .param("user", "user")
                        .param("senha", "senha"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));

        verify(serviceUsuario, times(1)).salvarUsuario(any(Usuario.class));
    }

    @Test
    public void fazerLoginFail() throws Exception {
        when(serviceUsuario.loginUser(anyString(), anyString())).thenReturn(null);

        this.mockMvc.perform(post("/login")
                            .param("user", usuario.getUser())
                            .param("senha", usuario.getSenha()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("login/cadastro"));

        verify(serviceUsuario, times(1)).loginUser(anyString(), anyString());
    }
}
