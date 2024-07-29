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

public class AlunoTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C://Users//mayeu//Downloads//chrome-win64//chrome-win64//chrome.exe");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("http://localhost:8080/inserirAlunos");
    }

    @Test
    public void testCadastroAluno() {
        // Preenche o nome do aluno
        WebElement nomeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Ex: João da Silva']")));
        nomeInput.sendKeys("Mayara");

        // Preenche a matrícula
        WebElement matriculaInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#matricula")));
        matriculaInput.sendKeys("123456");

        // Realiza o cadastro (Utiliza as opções de curso, turno e status padrão)
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();

        // Verifica se o redirecionamento foi feito corretamente
        wait.until(ExpectedConditions.urlContains("/logout"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/logout"));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

