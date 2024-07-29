package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void index() throws Exception {
        mockMvc.perform(
                get("/index")
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("home/index")
        );
    }

    @Test
    @Transactional
    public void logout() throws Exception {
        this.mockMvc.perform(
                post("/logout")
        ).andExpect(
                status().is3xxRedirection()
        ).andExpect(
                redirectedUrl("/login")
        );
    }

    @Test
    @Transactional
    public void inserirUsuario() throws Exception {
        this.mockMvc.perform(
                post("/salvarUsuario")
                        .param("email", "ariel@email.com")
                        .param("user", "Ariel")
                        .param("senha", "274350")
        ).andExpect(
                status().is3xxRedirection()
        ).andExpect(
                redirectedUrl("/")
        );
    }

    @Test
    @Transactional
    public void loginInvalid() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("tiana@email.com");
        usuario.setSenha("842620");
        usuario.setUser("Tiana");

        this.usuarioRepository.save(usuario);

        this.mockMvc.perform(
                post("/login")
                        .param("email", "tiana@email.com")  // Ajuste para email salvo
                        .param("user", "Tiana")
                        .param("senha", "123456")  // Senha incorreta
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("login/cadastro")
        ).andExpect(
                model().attributeExists("error")  // Verifica se há um atributo de erro no modelo
        );
    }
}
