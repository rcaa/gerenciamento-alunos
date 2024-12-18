package br.com.gerenciamento.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void login() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUser("john_doe");
        var senha = "123456";
        usuario.setSenha(senha);

        try {
            serviceUsuario.salvarUsuario(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("user", usuario.getUser());
        form.add("senha", senha);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        // Act
        ResponseEntity<String> response = testRestTemplate.postForEntity("/login", request, String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getHeaders().get("Set-Cookie").get(0).contains("JSESSIONID"));
    }

    @Test
    public void cadastrar() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUser("john_doe");
        usuario.setEmail("john@doe.com");
        usuario.setSenha("123456");

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("user", usuario.getUser());
        form.add("email", usuario.getEmail());
        form.add("senha", usuario.getSenha());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        // Act
        ResponseEntity<String> response = testRestTemplate.postForEntity("/salvarUsuario", request, String.class);

        // Assert
        Assert.assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    public void logout() {
    }
}
