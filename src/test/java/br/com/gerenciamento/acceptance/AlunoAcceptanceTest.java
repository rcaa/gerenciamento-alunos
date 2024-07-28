package br.com.gerenciamento.acceptance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.GerenciamentoAlunosApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoAcceptanceTest {

	private WebDriver driver;
	private ConfigurableApplicationContext context;

	@Before
	public void setup() {
		context = SpringApplication.run(GerenciamentoAlunosApplication.class);

		driver = new ChromeDriver();
		driver.get("http://localhost:8080");

		this.cadastrarELogarUsuario();
	}

	@After
	public void teardown() {
		driver.quit();
		context.close();
	}

	public void cadastrarELogarUsuario() {
		String email = "pedro@gmail.com";
		String user = "pedro";
		String senha = "1234";

		driver.findElement(By.linkText("Clique aqui para se cadastrar")).click();

		// cadastro
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("user")).sendKeys(user);
		driver.findElement(By.id("senha")).sendKeys(senha);
		driver.findElement(By.className("btn-primary")).click();

		// login
		driver.findElement(By.id("user")).sendKeys(user);
		driver.findElement(By.id("senha")).sendKeys(senha);
		driver.findElement(By.className("btn-primary")).click();

		String expectedText = "Sistema de Gerenciamento de Alunos";
		String actualText = driver.findElement(By.className("texto-centro")).getText();

		Assert.assertEquals(expectedText, actualText);
	}

	@Test
	public void cadastrarAluno() {
		String nomeAluno = "Maria";
		String cursoAluno = "INFORMATICA";

		driver.findElement(By.linkText("CADASTRAR ALUNO")).click();

		driver.findElement(By.id("nome")).sendKeys(nomeAluno);
		driver.findElement(By.xpath("//button[text()='Gerar']")).click();

		WebElement selectElement = driver.findElement(By.id("curso"));
		Select select = new Select(selectElement);
		select.selectByVisibleText(cursoAluno);

		driver.findElement(By.xpath("//button[text()='Salvar']")).click();

		WebElement table = driver.findElement(By.className("table"));
		WebElement firstRow = table.findElement(By.cssSelector("tbody tr:first-child"));

		WebElement firstCell = firstRow.findElement(By.cssSelector("td:nth-child(1)"));
		WebElement secondCell = firstRow.findElement(By.cssSelector("td:nth-child(2)"));

		Assert.assertEquals(nomeAluno, firstCell.getText());
		Assert.assertEquals(cursoAluno, secondCell.getText());
	}
}
