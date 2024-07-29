package br.com.gerenciamento.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioAcceptanceTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Configura o caminho do ChromeDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080"); // Altere para a URL correta do seu aplicativo
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCreateUsuario() {
        // Acesse a página de criação de usuário
        driver.findElement(By.linkText("Novo Usuario")).click();

        // Preencha o formulário de usuário
        WebElement nomeField = driver.findElement(By.name("nome"));
        nomeField.sendKeys("Teste Usuario");

        // Submeta o formulário
        driver.findElement(By.name("submit")).click();

        // Verifique se o usuário foi criado
        WebElement usuarioNome = driver.findElement(By.id("usuarioNome"));
        assertEquals("Teste Usuario", usuarioNome.getText());
    }
}
