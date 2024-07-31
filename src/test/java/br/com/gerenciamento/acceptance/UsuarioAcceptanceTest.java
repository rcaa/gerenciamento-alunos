package br.com.gerenciamento.acceptance;

import br.com.gerenciamento.GerenciamentoAlunosApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioAcceptanceTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private ConfigurableApplicationContext context;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        context = new SpringApplication(GerenciamentoAlunosApplication.class).run();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Transactional
    public void testarCadastroUsuario() throws InterruptedException {
        driver.get("http://localhost:8080/");

        WebElement title = driver.findElement(By.tagName("h2"));
        assert title.getText().equals("Login");

        WebElement button = driver.findElement(By.tagName("a"));
        button.click();

        title = driver.findElement(By.tagName("h2"));
        assert title.getText().equals("Cadastro de Usuário");

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
        WebElement senhaField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        WebElement cadastrarButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

        emailField.sendKeys("teste@exemplo.com");
        userField.sendKeys("usuarioTeste");
        senhaField.sendKeys("senhaSegura");

        cadastrarButton.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlToBe("http://localhost:8080/"),
                ExpectedConditions.urlToBe("http://localhost:8080/salvarUsuario")
        ));

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.equals("http://localhost:8080/") ||
                        currentUrl.equals("http://localhost:8080/salvarUsuario")
        );
    }

    @Test
    @Transactional
    public void testarLoginUsuario() {

        driver.get("http://localhost:8080");

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

        usernameField.sendKeys("usuarioTeste");
        passwordField.sendKeys("senhaSegura");

        loginButton.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/login"));

        Assert.assertEquals("http://localhost:8080/login", driver.getCurrentUrl());
    }
}
