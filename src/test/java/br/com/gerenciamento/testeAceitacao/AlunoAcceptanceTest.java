package br.com.gerenciamento.testeAceitacao;

import br.com.gerenciamento.GerenciamentoAlunosApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AlunoAcceptanceTest {

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
    public void testCadastroDeAluno() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Acessa a página de cadastro de alunos
        driver.get("http://localhost:8080/inserirAlunos");

        // Preencher o Nome
        WebElement nomeField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
        nomeField.sendKeys("João Silva");

        // Selecionar o curso
        WebElement cursoSelectElement = driver.findElement(By.id("curso"));
        Select cursoSelect = new Select(cursoSelectElement);
        cursoSelect.selectByValue("INFORMATICA");

        // Preencher a matrícula
        WebElement matriculaField = driver.findElement(By.id("matricula"));
        matriculaField.sendKeys("MAT2024001");

        // Selecionar o turno
        WebElement turnoSelectElement = driver.findElement(By.id("turno"));
        Select turnoSelect = new Select(turnoSelectElement);
        turnoSelect.selectByValue("MATUTINO");

        // Selecionar o status
        WebElement statusSelectElement = driver.findElement(By.id("status"));
        Select statusSelect = new Select(statusSelectElement);
        statusSelect.selectByValue("ATIVO");

        // Aperta o botão para salvar
        WebElement salvarButton = driver.findElement(By.cssSelector("button.btn.btn-outline-success[type='submit']"));
        salvarButton.click();

        // Verifica se foi redirecionado  para "alunos-adicionados"
        wait.until(ExpectedConditions.urlContains("/alunos-adicionados"));
        assertTrue(driver.getCurrentUrl().contains("/alunos-adicionados"),
                "Redirecionamento falhou para /alunos-adicionados");

        // Verifica se os dados da tabela são os mesmos do aluno inserido
        WebElement tabela = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

        // Verificar os dados na tabela
        String pageSource = tabela.getText();
        assertTrue(pageSource.contains("João Silva"), "Nome do aluno não encontrado na tabela");
        assertTrue(pageSource.contains("INFORMATICA"), "Curso não encontrado na tabela");
        assertTrue(pageSource.contains("MAT2024001"), "Matricula não encontrada na tabela");
        assertTrue(pageSource.contains("MATUTINO"), "Turno não encontrado na tabela");
        assertTrue(pageSource.contains("ATIVO"), "Status não encontrado na tabela");


        driver.quit(); // Fecha o navegador
    }
}
