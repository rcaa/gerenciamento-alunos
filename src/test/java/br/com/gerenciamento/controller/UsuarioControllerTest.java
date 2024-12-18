package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void testCriarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Marcos Nascimento");
        usuario.setEmail("marcos.nascimento@gmail.com");
        usuario.setSenha("senhaSegura");

        mockMvc.perform(post("/usuarios/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("usuario", usuario))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios/listar"));
    }

    @Test
    public void testBuscarUsuarioPorId() throws Exception {
        Long usuarioId = 1L;

        mockMvc.perform(get("/usuarios/{id}", usuarioId))
                .andExpect(status().isOk())
                .andExpect(view().name("Usuario/detalhesUsuario"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testListarUsuarios() throws Exception {
        mockMvc.perform(get("/usuarios/listar"))
                .andExpect(status().isOk())
                .andExpect(view().name("Usuario/listUsuarios"))
                .andExpect(model().attributeExists("usuariosList"));
    }

    @Test
    public void testDeletarUsuario() throws Exception {
        Long usuarioId = 1L;

        mockMvc.perform(get("/usuarios/deletar/{id}", usuarioId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios/listar"));
    }
}
