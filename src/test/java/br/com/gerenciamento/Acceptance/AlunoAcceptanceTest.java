package br.com.gerenciamento.Acceptance;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlunoAcceptanceTest {

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
    public void testInsertAndVerifyAluno() throws InterruptedException {
        driver.get("http://localhost:8080/inserirAlunos");


        WebElement nomeField = driver.findElement(By.name("nome"));
        WebElement matriculaField = driver.findElement(By.name("matricula"));
        WebElement cursoField = driver.findElement(By.name("curso"));
        WebElement statusField = driver.findElement(By.name("status"));
        WebElement turnoField = driver.findElement(By.name("turno"));

        nomeField.sendKeys("Teste Aluno");
        matriculaField.sendKeys("123456");
        cursoField.sendKeys("Ciencia da Computacao");
        statusField.sendKeys("ativo");
        turnoField.sendKeys("noite");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

       
        Thread.sleep(2000);

        driver.get("http://localhost:8080/alunos-adicionados");

        
        WebElement alunoNome = driver.findElement(By.xpath("//td[contains(text(),'Teste Aluno')]"));
        assertEquals("Teste Aluno", alunoNome.getText());
    }
}
