package br.com.gerenciamento.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlunoAcceptanceTest {

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
    public void testCreateAluno() {
        // Acesse a página de criação de aluno
        driver.findElement(By.linkText("Novo Aluno")).click();

        // Preencha o formulário de aluno
        WebElement nomeField = driver.findElement(By.name("nome"));
        nomeField.sendKeys("Teste Aluno");

        WebElement matriculaField = driver.findElement(By.name("matricula"));
        matriculaField.sendKeys("123456");

        // Submeta o formulário
        driver.findElement(By.name("submit")).click();

        // Verifique se o aluno foi criado
        WebElement alunoNome = driver.findElement(By.id("alunoNome"));
        assertEquals("Teste Aluno", alunoNome.getText());
    }
}
