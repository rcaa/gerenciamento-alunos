package br.com.gerenciamento.acceptance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioAcceptanceTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void testCadastroUsuario() {
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
        assertEquals("http://localhost:8080/salvarUsuario", currentUrl); 
    }


    @Test
    public void testLoginUsuarioValido() {
        driver.get("http://localhost:8080/");

        WebElement userField = driver.findElement(By.name("user"));
        WebElement passwordField = driver.findElement(By.name("senha"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        userField.sendKeys("usuario");
        passwordField.sendKeys("senha");
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/login", currentUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
