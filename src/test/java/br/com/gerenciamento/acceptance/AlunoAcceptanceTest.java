package br.com.gerenciamento.acceptance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlunoAcceptanceTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        registerAndLogin();
    }

    private void registerAndLogin() {
        // Cadastro de usuário
        driver.get("http://localhost:8080/cadastro");

        WebElement emailField = driver.findElement(By.name("email"));
        WebElement userField = driver.findElement(By.name("user"));
        WebElement passwordField = driver.findElement(By.name("senha"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("usuario@example.com");
        userField.sendKeys("usuario");
        passwordField.sendKeys("senha");
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/salvarUsuario", currentUrl, "Usuário não foi redirecionado para a página inicial após cadastro.");

        driver.get("http://localhost:8080/");

        WebElement loginUserField = driver.findElement(By.name("user"));
        WebElement loginPasswordField = driver.findElement(By.name("senha"));
        WebElement loginSubmitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        loginUserField.sendKeys("usuario");
        loginPasswordField.sendKeys("senha");
        loginSubmitButton.click();

        currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/login", currentUrl, "Usuário não foi redirecionado para a página inicial após login.");
    }

    @Test
    public void testCreateAluno() {
        driver.get("http://localhost:8080/inserirAlunos");

        WebElement nomeField = driver.findElement(By.name("nome")); 
        WebElement cursoField = driver.findElement(By.name("curso"));
        WebElement matriculaField = driver.findElement(By.name("matricula"));
        WebElement statusField = driver.findElement(By.name("status"));
        WebElement turnoField = driver.findElement(By.name("turno"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nomeField.sendKeys("João da Silva");
        cursoField.sendKeys("Engenharia");
        matriculaField.sendKeys("12345");
        statusField.sendKeys("Ativo");
        turnoField.sendKeys("Diurno");
        
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/logout", currentUrl);  
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
