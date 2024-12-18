package br.com.gerenciamento.acceptance;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioAcceptanceTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCriarUsuario() {
        driver.get("http://localhost:8080/usuarios/criar");

        // Preenche os campos do formulário
        WebElement nomeInput = driver.findElement(By.id("nome"));
        nomeInput.sendKeys("Marcos Nascimento");

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("marcos.nascimento@gmail.com");

        WebElement senhaInput = driver.findElement(By.id("senha"));
        senhaInput.sendKeys("senha123");

        // Envia o formulário
        WebElement submitButton = driver.findElement(By.id("submitButton"));
        submitButton.click();

        // Verifica o redirecionamento
        assertEquals("http://localhost:8080/usuarios/listar", driver.getCurrentUrl());
    }

    @Test
    public void testListarUsuarios() {
        driver.get("http://localhost:8080/usuarios/listar");

        // Verifica se a tabela de usuários é exibida
        WebElement usuariosTable = driver.findElement(By.id("usuariosTable"));
        assertTrue(usuariosTable.isDisplayed());
    }

    @Test
    public void testDetalhesDoUsuario() {
        driver.get("http://localhost:8080/usuarios/1");

        // Verifica se a página de detalhes do usuário é exibida
        WebElement nomeUsuario = driver.findElement(By.id("nomeUsuario"));
        assertEquals("Marcos Nascimento", nomeUsuario.getText());
    }

    @Test
    public void testDeletarUsuario() {
        driver.get("http://localhost:8080/usuarios/deletar/1");

        // Verifica o redirecionamento após a exclusão
        assertEquals("http://localhost:8080/usuarios/listar", driver.getCurrentUrl());
    }
}
