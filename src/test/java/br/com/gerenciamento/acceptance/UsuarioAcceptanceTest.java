package br.com.gerenciamento.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

public class UsuarioAcceptanceTest {
    private static final Faker faker = new Faker(Locale.forLanguageTag("pt-br"));
    WebDriver driver;

	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}

	@Test
	public void cadastro() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("http://localhost:8080/cadastro");
       
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement usernameField = driver.findElement(By.name("user"));
        WebElement passwordField = driver.findElement(By.name("senha"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys(faker.internet().emailAddress());
        usernameField.sendKeys("novo_usuario");
        passwordField.sendKeys("senha123");
        submitButton.click();

        WebElement loginTitle = driver.findElement(By.tagName("h2"));
        assertEquals("Login", loginTitle.getText());
    
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/"));
    
        WebElement cadastroLink = driver.findElement(By.cssSelector("a[href*='cadastro']"));
        assertTrue(cadastroLink.isDisplayed());
	}

	@AfterEach
	public void teardown() {
		driver.quit();
	}
}
