package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ServiceUsuario serviceUsuario;

  @Test
  @Transactional
  public void testLoginPage() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("login/login"))
        .andExpect(model().attributeExists("usuario"))
        .andExpect(model().attribute("usuario", instanceOf(Usuario.class)));
  }

  @Test
  @Transactional
  public void testCadastroPage() throws Exception {
    mockMvc.perform(get("/cadastro"))
        .andExpect(status().isOk())
        .andExpect(view().name("login/cadastro"))
        .andExpect(model().attributeExists("usuario"))
        .andExpect(model().attribute("usuario", instanceOf(Usuario.class)));
  }

  @Test
  @Transactional
  public void testLoginUsuario() throws Exception {
    Usuario usuario = new Usuario();
    usuario.setUser("existinguser");
    usuario.setSenha("existingpassword");
    serviceUsuario.salvarUsuario(usuario);

    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("user", usuario.getUser())
        .param("senha", "existingpassword"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/index"))
        .andExpect(status().isOk())
        .andExpect(view().name("home/index"));
  }

  @Test
  @Transactional
  public void testLogout() throws Exception {

    Usuario usuario = new Usuario();
    usuario.setUser("testuser");
    usuario.setSenha("testpassword");
    serviceUsuario.salvarUsuario(usuario);

    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("user", usuario.getUser())
        .param("senha", "testpassword"))
        .andExpect(status().isOk());

    mockMvc.perform(post("/logout"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("login/login"));
  }
}
