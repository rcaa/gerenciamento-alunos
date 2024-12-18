package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ServiceUsuario serviceUsuario;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setUser("testUser");
        usuario.setSenha("testPassword");
    }

    @Test
    public void testLoginPage() throws Exception {
        // Testa se a página de login está sendo carregada corretamente
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login/login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("usuario"));
    }
    
    @Test
    public void testCadastroPage() throws Exception {
        // Testa se a página de cadastro está sendo carregada corretamente
        mockMvc.perform(MockMvcRequestBuilders.get("/cadastro"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login/cadastro"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("usuario"));
    }
    
    @Test
    public void testCadastroUsuario() throws Exception {
        // Simula o cadastro de um novo usuário
        mockMvc.perform(MockMvcRequestBuilders.post("/salvarUsuario")
                        .param("user", "newUser")
                        .param("senha", "newPassword"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
    
    @Test
    public void testLogout() throws Exception {
        // Testa se o logout redireciona para a página de login
        mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login/login"));
    }
}
