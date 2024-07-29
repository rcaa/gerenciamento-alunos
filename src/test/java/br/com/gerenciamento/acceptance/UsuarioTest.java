package br.com.gerenciamento.acceptance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class UsuarioTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C://Users//mayeu//Downloads//chrome-win64//chrome-win64//chrome.exe");
        driver = new ChromeDriver(options);
        // Tempo de espera para os elementos ficarem visiveis na tela
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void testCadastroELogin() {
        // Navega para a página de cadastro
        driver.get("http://localhost:8080/cadastro");

        // Espera o formulário de cadastro estar visível
        WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("form")));

        // Encontra os elementos de entrada
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='digite seu email']")));
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='username']")));
        WebElement senhaField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='senha']")));
        WebElement cadastrarButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-primary")));

        // Realiza o cadastro
        String email = "mayara.keferreira@ufape.edu.br";
        String username = "Mayara";
        String senha = "Ufape123";
        emailField.sendKeys(email);
        usernameField.sendKeys(username);
        senhaField.sendKeys(senha);
        cadastrarButton.click();

        // Vai para a página de login
        driver.get("http://localhost:8080");

        // Espera o formulário de login estar visível
        WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("form")));

        // Encontra os elementos de entrada de login
        WebElement loginUsernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='username']")));
        WebElement loginSenhaField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='senha']")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-primary")));

        // Realiza o login
        loginUsernameField.sendKeys(username);
        loginSenhaField.sendKeys(senha);
        loginButton.click();

        // Espera redirecionamento
        wait.until(ExpectedConditions.urlContains("/login"));

        // Verifica se o redirecionamento foi realizado corretamente
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/login"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8080/login");

    }

    @AfterClass
    public void tearDown() {
        // Fecha o navegador
        if (driver != null) {
            driver.quit();
        }
    }
}

