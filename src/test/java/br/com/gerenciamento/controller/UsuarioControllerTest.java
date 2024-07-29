package br.com.gerenciamento.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceUsuario serviceUsuario;

    @Test
    public void testeCadastro() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cadastro"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login/cadastro"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("usuario"));
    }

    @Test
    public void testeLogout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login/login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("usuario"));
    }

    @Test
    public void testeSalvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("testuser");
        usuario.setSenha("senha123");

        doNothing().when(serviceUsuario).salvarUsuario(any(Usuario.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/salvarUsuario")
                .flashAttr("usuario", usuario))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        verify(serviceUsuario, times(1)).salvarUsuario(any(Usuario.class));
    }

    @Test
    public void testeLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("testuser");
        usuario.setSenha(Util.md5("senha123"));

        when(serviceUsuario.loginUser(anyString(), anyString())).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("user", "testuser")
                .param("senha", "senha123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("aluno"));

        verify(serviceUsuario, times(1)).loginUser(anyString(), anyString());
    }
}
