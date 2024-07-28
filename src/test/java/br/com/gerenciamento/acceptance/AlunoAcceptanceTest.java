package br.com.gerenciamento.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AlunoAcceptanceTest {
    WebDriver driver;

	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}

	@Test
	public void cadastro() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("http://localhost:8080/inserirAlunos");

        WebElement nomeField = driver.findElement(By.name("nome"));
        WebElement cursoSelect = driver.findElement(By.name("curso"));
        WebElement matriculaField = driver.findElement(By.id("matricula"));
        WebElement turnoSelect = driver.findElement(By.name("turno"));
        WebElement statusSelect = driver.findElement(By.name("status"));
        WebElement gerarMatriculaButton = driver.findElement(By.cssSelector("button[onclick='GerarMatricula();']"));
        WebElement submitButton = driver.findElement(By.cssSelector("button.btn.btn-outline-success"));

        nomeField.sendKeys("João da Silva");
        cursoSelect.sendKeys("Curso de Exemplo");
        gerarMatriculaButton.click();
        matriculaField.sendKeys("123456");
        turnoSelect.sendKeys("Matutino");
        statusSelect.sendKeys("Ativo");
        submitButton.click();

        WebElement homeTitle = driver.findElement(By.tagName("h3"));
        assertEquals("Últimos Alunos Adicionados", homeTitle.getText());

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/alunos-adicionados"));
	}

	@AfterEach
	public void teardown() {
		driver.quit();
	}
}
