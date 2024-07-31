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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoAcceptanceTest {

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
    public void testarCadastroAluno() {
        driver.get("http://localhost:8080/index");

        List<WebElement> botoesCadastrar = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a.btn-index")));
        botoesCadastrar.get(0).click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/inserirAlunos"));

        Assert.assertEquals("http://localhost:8080/inserirAlunos", driver.getCurrentUrl());

        List<WebElement> inputs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("input")));

        //Input de Nome
        inputs.get(0).sendKeys("Teste da Silva");
        inputs.get(1).sendKeys("15982024");


        List<WebElement> selects = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("select")));

        selects.get(0).sendKeys("INFORMATICA");
        selects.get(1).sendKeys("NOTURNO");
        selects.get(2).sendKeys("ATIVO");

        WebElement botaoCadastrar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='save']")));
        botaoCadastrar.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/alunos-adicionados"));

        Assert.assertEquals("http://localhost:8080/alunos-adicionados", driver.getCurrentUrl());

    }
}
