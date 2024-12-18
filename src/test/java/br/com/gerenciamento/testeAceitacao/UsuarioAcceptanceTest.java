package br.com.gerenciamento.testeAceitacao;

import br.com.gerenciamento.GerenciamentoAlunosApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UsuarioAcceptanceTest {

    private static WebDriver driver;

    // Estou colocando no beforeAll num cenario onde outros testes serão implementados, facilitando tudo
    @BeforeAll
    public static void setup() {
        // Inicia a aplicação Spring Boot para acessar via navegador localmente
        new Thread(() -> SpringApplication.run(GerenciamentoAlunosApplication.class)).start();

        // configura o ChromeDriver
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }


    @Test
    public void testCadastroELoginUsuario() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Acessar a página de cadastro
        driver.get("http://localhost:8080/cadastro");

        // Preencher o formulário de cadastro
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        WebElement userField = driver.findElement(By.id("user"));
        WebElement senhaField = driver.findElement(By.id("senha"));
        WebElement cadastrarButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("teste@exemplo.com");
        userField.sendKeys("testeUsuario");
        senhaField.sendKeys("senha123");

        cadastrarButton.click();

        // Redirecionar para página de login
        driver.get("http://localhost:8080/");

        // Preencher o formulário de login
        WebElement loginUserField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user")));
        WebElement loginSenhaField = driver.findElement(By.id("senha"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        loginUserField.sendKeys("testeUsuario");
        loginSenhaField.sendKeys("senha123");

        loginButton.click();

        // Verificar se foi redirecionado após o login
        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"), "Redirecionamento falhou para /login (pagina de logado)");

        System.out.println("Teste de cadastro e login executado com sucesso!");

        driver.quit();
    }
}
