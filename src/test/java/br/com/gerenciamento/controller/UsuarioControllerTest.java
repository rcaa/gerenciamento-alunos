package br.com.gerenciamento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.NoSuchAlgorithmException;

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

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ServiceUsuario serviceUsuario;

    private MockMvc mockMvc;

    private Usuario usuario;

    @Before
    public void setup() throws NoSuchAlgorithmException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("usuario@teste.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha(Util.md5("senhaTeste"));

        when(serviceUsuario.loginUser("usuarioTeste", Util.md5("senhaTeste"))).thenReturn(usuario);
        when(serviceUsuario.loginUser("usuarioTeste", Util.md5("senhaErrada"))).thenReturn(null);
    }

    @Test
    public void mostrarPaginaLogin() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("login/login"))
            .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void mostrarPaginaCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
            .andExpect(status().isOk())
            .andExpect(view().name("login/cadastro"))
            .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void fazerLoginComUsuarioValido() throws Exception {
        mockMvc.perform(post("/login")
                .param("user", "usuarioTeste")
                .param("senha", "senhaTeste"))
                .andExpect(status().isOk());
    }

    @Test
    public void fazerLogoutComSucesso() throws Exception {
        mockMvc.perform(post("/logout"))
            .andExpect(status().isOk());
}

}
