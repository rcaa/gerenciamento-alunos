package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @MockBean
    ServiceUsuario serviceUsuario;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void salvarUsuario() throws Exception {
        // Verifica se o usuário está sendo salvo corretamente
        Usuario usuario = new Usuario();
        usuario.setUser("Mayara");
        usuario.setSenha("Ufape123456");

        this.mockMvc.perform(
                        post("/salvarUsuario")
                                .param("user", usuario.getUser())
                                .param("senha", usuario.getSenha())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void loginSucesso() throws Exception {
        // Verifica se o login está sendo realizado com sucesso
        Usuario usuario = new Usuario();
        usuario.setUser("Mayara");
        usuario.setSenha("Ufape123456");

        when(serviceUsuario.loginUser(usuario.getUser(), Util.md5(usuario.getSenha())))
                .thenReturn(usuario);

        this.mockMvc.perform(
                        post("/login")
                                .param("Mayara", usuario.getUser())
                                .param("Ufape123456", usuario.getSenha())
                ).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void paginaLogin() throws Exception {
        // Verifica se a página de login está sendo exibida corretamente
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void paginaCadastro() throws Exception {
        // Verifica se a página de cadastro está sendo exibida corretamente
        this.mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }
}
