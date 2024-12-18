package br.com.gerenciamento.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

@SpringBootTest
public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceUsuario serviceUsuario;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"));
    }

    @Test
    void testCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"));
    }

    @Test
    void testSalvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("email@exemplo.com");
        usuario.setUser("usuario");
        usuario.setSenha("senha");

        mockMvc.perform(post("/salvarUsuario")
                .flashAttr("usuario", usuario))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

}
