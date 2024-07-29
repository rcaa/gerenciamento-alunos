package br.com.gerenciamento.controller;

import org.junit.Assert;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void loginVálido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Daniel");
        this.serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/login")
            .param("user", "Daniel")
            .param("senha", "12345678")) 
            .andExpect(status().isOk())
            .andExpect(view().name("home/index")) 
            .andExpect(result -> {
                HttpSession session = result.getRequest().getSession();
                Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
                Assert.assertNotNull(usuarioLogado);
                Assert.assertEquals("Daniel",  usuarioLogado.getUser());
            });
    }

    @Test
    @Transactional
    public void loginInválido() throws Exception {
        mockMvc.perform(post("/login")
            .param("user", "Daniel")
            .param("senha", "12345678")) 
            .andExpect(status().isOk())
            .andExpect(view().name("login/cadastro"));
    }

    @Test
    @Transactional
    public void logout() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Daniel");
        this.serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/logout")
        .sessionAttr("usuarioLogado", usuario)) 
        .andExpect(status().isOk())
        .andExpect(view().name("login/login")) 
        .andExpect(result -> {
            HttpSession session = result.getRequest().getSession();
            assertNull(session.getAttribute("usuarioLogado")); 
        });
    }

    @Test
    @Transactional
    public void cadastrar() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345678");
        usuario.setUser("Daniel");

        mockMvc.perform(post("/salvarUsuario")
            .flashAttr("usuario", usuario))  
            .andExpect(status().is3xxRedirection()) 
            .andExpect(view().name("redirect:/")); 

        Usuario usuarioSalvo = usuarioRepository.buscarLogin("Daniel", Util.md5("12345678"));
        Assert.assertNotNull(usuarioSalvo);
        Assert.assertEquals("teste@email.com", usuarioSalvo.getEmail());
    }

}
