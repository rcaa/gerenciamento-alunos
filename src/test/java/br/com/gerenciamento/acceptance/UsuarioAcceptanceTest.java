package br.com.gerenciamento.acceptance;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
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
import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioAcceptanceTest {

    private WebDriver driver;
    private ConfigurableApplicationContext context;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        SpringApplication application = new SpringApplication(GerenciamentoAlunosApplication.class);
        context = application.run();
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.quit();
        context.close();
    }

    @Test
    public void testUsuarioCriar() throws Exception {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get("http://localhost:8080/");
        wait.until(d -> d.findElement(By.linkText("Clique aqui para se cadastrar")).isDisplayed());
        driver.findElement(By.linkText("Clique aqui para se cadastrar")).click();
        wait.until(d -> d.findElement(By.id("email")).isDisplayed());
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("user")).click();
        driver.findElement(By.id("user")).sendKeys("testuser");
        driver.findElement(By.id("senha")).click();
        driver.findElement(By.id("senha")).sendKeys("password123");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        
        wait.until(d -> d.findElement(By.id("user")).isDisplayed());
        driver.findElement(By.id("user")).click();
        driver.findElement(By.id("user")).sendKeys("testuser");
        driver.findElement(By.id("senha")).click();
        driver.findElement(By.id("senha")).sendKeys("password123");
        driver.findElement(By.cssSelector(".btn")).click();
        
        wait.until(d -> d.findElement(By.linkText("CADASTRAR ALUNO")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.linkText("CADASTRAR ALUNO")).isDisplayed());
    }
}
