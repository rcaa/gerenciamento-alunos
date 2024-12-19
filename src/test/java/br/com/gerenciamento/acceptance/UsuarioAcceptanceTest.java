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
    public void testRegisterAndLoginUsuario() throws InterruptedException {
        driver.get("http://localhost:8080/cadastro");

       
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement userField = driver.findElement(By.name("user"));
        WebElement senhaField = driver.findElement(By.name("senha"));

        emailField.sendKeys("test@test.com");
        userField.sendKeys("testUser");
        senhaField.sendKeys("password");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

 
        Thread.sleep(2000);

        driver.get("http://localhost:8080/");

   
        WebElement loginUserField = driver.findElement(By.name("user"));
        WebElement loginSenhaField = driver.findElement(By.name("senha"));

        loginUserField.sendKeys("testUser");
        loginSenhaField.sendKeys("password");

        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();


        Thread.sleep(2000);

        WebElement welcomeMessage = driver.findElement(By.tagName("h1"));
        assertEquals("Welcome", welcomeMessage.getText());
    }
}