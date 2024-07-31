package br.com.gerenciamento.controller;

import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.com.gerenciamento.model.Usuario;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testpageCadastrar() throws Exception {
    mockMvc.perform(get("/cadastro"))
            .andExpect(status().isOk())
            .andExpect(view().name("login/cadastro"))
            .andExpect(model().attributeExists("novoUsuario"))
            .andExpect(model().attribute("novoUsuario", instanceOf(Usuario.class)));
}
    @Test
    @Transactional
    public void testListaUsuarios() throws Exception {
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarios/lista"))
                .andExpect(model().attributeExists("usuarios"))
                .andExpect(model().attribute("usuarios", instanceOf(List.class)));
    }
    @Test
    @Transactional
    public void testAtualizacaoUsuario() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get("/usuarios/atualizar/" + userId))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarios/atualizar"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", instanceOf(Usuario.class)));
    }

    @Test
    @Transactional
    public void testExclusaoUsuario() throws Exception {
        Long userId = 1L;
        mockMvc.perform(delete("/usuarios/excluir/" + userId))
                .andExpect(status().isNoContent());
    }

}
