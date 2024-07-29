package br.com.gerenciamento.aceitacao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.gerenciamento.GerenciamentoAlunosApplication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlunoAceitacaoTest {
    private WebDriver driver;
    private ConfigurableApplicationContext appContext;

    @Before
    public void setup() {
        this.appContext = SpringApplication.run(GerenciamentoAlunosApplication.class);
        this.driver = new ChromeDriver();
    }

    @After
    public void reset() {
        driver.quit();
        appContext.close();
    }

    @Test
    public void testInserirAluno() throws InterruptedException {
        String nomeAluno = "alunoo";

        driver.get("http://localhost:8080");

        // cadastrar usuário
        driver.findElement(By.linkText("Clique aqui para se cadastrar")).click();

        driver.findElement(By.id("email")).sendKeys("usuario@hotmail.com");
        driver.findElement(By.id("user")).sendKeys("usuario");
        driver.findElement(By.id("senha")).sendKeys("usuario123");
        driver.findElement(By.className("btn-primary")).click();

        // fazer login do usuário
        driver.findElement(By.id("user")).sendKeys("usuario");
        driver.findElement(By.id("senha")).sendKeys("usuario123");
        driver.findElement(By.className("btn-primary")).click();

        String textoPagina = driver.findElement(By.className("texto-centro")).getText();
        Assert.assertTrue(textoPagina.equals("Sistema de Gerenciamento de Alunos"));

        //redirecionamento pra página de cadastro
        driver.findElement(By.linkText("Cadastrar Aluno")).click();
        
        //linkando os campos da pagina de cadastro
        WebElement nomeField = driver.findElement(By.name("nome"));
        WebElement gerarMatriculaButton = driver.findElement((By.xpath("//button[text()='Gerar']")));
        WebElement submitButton = driver.findElement((By.xpath("//button[text()='Salvar']")));

        nomeField.sendKeys(nomeAluno);
        gerarMatriculaButton.click();

        Thread.sleep(200); // garantir que seja gerada a matrícula

        WebElement matriculaField = driver.findElement(By.name("matricula"));
        assertTrue(!matriculaField.getAttribute("value").isEmpty(), "Matrícula não foi gerada");

        submitButton.click();

        Thread.sleep(200); // garantir que o submit foi feito

        driver.get("http://localhost:8080/alunos-adicionados");

        WebElement alunoTableBody = driver.findElement(By.cssSelector(".table tbody"));
        List<WebElement> rows = alunoTableBody.findElements(By.tagName("tr"));
        boolean alunoEncontrado = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > 0 && cells.get(0).getText().equals(nomeAluno)) {
                alunoEncontrado = true;
                break;
            }
        }
        assertTrue(alunoEncontrado, "Aluno não foi encontrado na lista");
    }
}
