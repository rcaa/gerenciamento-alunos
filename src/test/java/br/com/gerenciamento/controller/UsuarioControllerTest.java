package br.com.gerenciamento.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UsuarioControllerTest {
    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void login() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("1234");
        usuario.setUser("Júlia");

        this.serviceUsuario.salvarUsuario(usuario);

        ResponseEntity<String> response = restTemplate.postForEntity("/login", usuario, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("home/index"));
    }

    @Test
    public void loginFalho() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("1234");
        usuario.setUser("Júlia");

        this.serviceUsuario.salvarUsuario(usuario);

        usuario.setSenha("senhaErrada");
        ResponseEntity<String> response = restTemplate.postForEntity("/login", usuario, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario não encontrado. Tente novamente"));
    }

    @Test
    public void cadastro() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("1234");
        usuario.setUser("Júlia");

        ResponseEntity<String> response = restTemplate.postForEntity("/salvarUsuario", usuario, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().getLocation().toString().contains("/"));
    }

    @Test
    public void logout() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("1234");
        usuario.setUser("Júlia");

        this.serviceUsuario.salvarUsuario(usuario);

        ResponseEntity<String> loginResponse = restTemplate.postForEntity("/login", usuario, String.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        ResponseEntity<String> logoutResponse = restTemplate.postForEntity("/logout", null, String.class);
        assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());
        assertTrue(logoutResponse.getBody().contains("login/login"));

    }
}
