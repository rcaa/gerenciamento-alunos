package br.com.gerenciamento.acceptance;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.gerenciamento.GerenciamentoAlunosApplication;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioAcceptanceTest {

    private WebDriver driver;
    private JavascriptExecutor js;
    private ConfigurableApplicationContext context;

    @Before
    public void setup() {
        SpringApplication app = new SpringApplication(GerenciamentoAlunosApplication.class);
        context = app.run();
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
    }

    @After
    public void cleanup() {
        driver.quit();
        context.close();
    }

    @Test
    public void cadastrarELogar() throws Exception {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        // Acessa a página de cadastro
        driver.get("http://localhost:8080/");
        wait.until(d -> d.findElement(By.linkText("Clique aqui para se cadastrar")).isDisplayed());
        driver.findElement(By.linkText("Clique aqui para se cadastrar")).click();

        // Preenche o form de cadastro
        wait.until(d -> d.findElement(By.id("email")).isDisplayed());
        driver.findElement(By.id("email")).sendKeys("exemplo@teste.com");
        driver.findElement(By.id("user")).sendKeys("usuarioTeste");
        driver.findElement(By.id("senha")).sendKeys("senhaTeste123");
        driver.findElement(By.cssSelector(".btn-primary")).click();

        // Realiza o login
        wait.until(d -> d.findElement(By.id("user")).isDisplayed());
        driver.findElement(By.id("user")).sendKeys("usuarioTeste");
        driver.findElement(By.id("senha")).sendKeys("senhaTeste123");
        driver.findElement(By.cssSelector(".btn")).click();

        // Ver a home
        Assert.assertTrue(driver.getPageSource().contains("Sistema de Gerenciamento de Alunos"));
    }
}
