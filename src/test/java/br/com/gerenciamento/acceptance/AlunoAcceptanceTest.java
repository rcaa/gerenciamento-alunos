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
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testInserirAluno() {
        driver.get("http://localhost:8080/inserirAlunos");

        // Preenche os campos do formulário
        WebElement nomeInput = driver.findElement(By.id("nome"));
        nomeInput.sendKeys("Marcos Nascimento");

        WebElement matriculaInput = driver.findElement(By.id("matricula"));
        matriculaInput.sendKeys("123456");

        WebElement cursoInput = driver.findElement(By.id("curso"));
        cursoInput.sendKeys("Administração");

        WebElement turnoInput = driver.findElement(By.id("turno"));
        turnoInput.sendKeys("Noturno");

        WebElement statusInput = driver.findElement(By.id("status"));
        statusInput.sendKeys("Ativo");

        // Envia o formulário
        WebElement submitButton = driver.findElement(By.id("submitButton"));
        submitButton.click();

        // Verifica o redirecionamento
        assertEquals("http://localhost:8080/alunos-adicionados", driver.getCurrentUrl());
    }

    @Test
    public void testListarAlunos() {
        driver.get("http://localhost:8080/alunos-adicionados");

        // Verifica se a tabela de alunos é exibida
        WebElement alunosTable = driver.findElement(By.id("alunosTable"));
        assertTrue(alunosTable.isDisplayed());
    }

    @Test
    public void testDetalhesDoAluno() {
        driver.get("http://localhost:8080/alunos/1");

        // Verifica se a página de detalhes do aluno é exibida
        WebElement nomeAluno = driver.findElement(By.id("nomeAluno"));
        assertEquals("Marcos Nascimento", nomeAluno.getText());
    }

    @Test
    public void testRemoverAluno() {
        driver.get("http://localhost:8080/remover/1");

        // Verifica o redirecionamento após a remoção
        assertEquals("http://localhost:8080/alunos-adicionados", driver.getCurrentUrl());
    }
}
