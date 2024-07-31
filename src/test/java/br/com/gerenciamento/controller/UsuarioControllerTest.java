package br.com.gerenciamento.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ServiceUsuario serviceUsuario;

    @Test
    public void login() throws Exception{
        mockMvc.perform(get("/")).andExpect(status().isOk()) 
        .andExpect(model().attributeExists("usuario")).andExpect(view().name("login/login"));
    }
    
    @Test
    public void index() throws Exception{
        mockMvc.perform(get("/index")).andExpect(status().isOk()) 
               .andExpect(model().attributeExists("aluno")).andExpect(view().name("home/index"));
    }

    @Test
    public void cadastrar() throws Exception{
        mockMvc.perform(get("/cadastro")).andExpect(status().isOk()) 
        .andExpect(model().attributeExists("usuario")).andExpect(view().name("login/cadastro"));
    }

    @Test
    public void CadastrarUsuario() throws Exception {
        MockitoAnnotations.openMocks(this);
        
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setUser("test");
        usuario.setSenha("12345678");

        doNothing().when(serviceUsuario).salvarUsuario(usuario);

        mockMvc.perform(post("/salvarUsuario")
                .flashAttr("usuario", usuario))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}