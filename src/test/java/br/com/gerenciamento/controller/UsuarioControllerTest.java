package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll(); // Limpa o repositório antes de cada teste para evitar interferências.
    }

    @Test
    public void testCreateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Usuario");

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Teste Usuario"));
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Usuario");
        usuarioService.save(usuario);

        mockMvc.perform(get("/usuarios/{id}", usuario.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Usuario"));
    }

    @Test
    public void testGetAllUsuarios() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Teste Usuario 1");

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Teste Usuario 2");

        usuarioService.save(usuario1);
        usuarioService.save(usuario2);

        mockMvc.perform(get("/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Usuario");
        usuarioService.save(usuario);

        mockMvc.perform(delete("/usuarios/{id}", usuario.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<Usuario> deletedUsuario = usuarioRepository.findById(usuario.getId());
        assertFalse(deletedUsuario.isPresent());
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Usuario");
        usuarioService.save(usuario);

        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setNome("Teste Usuario Atualizado");

        mockMvc.perform(put("/usuarios/{id}", usuario.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Usuario Atualizado"));
    }
}
