package br.com.gerenciamento.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import java.time.Duration;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.gerenciamento.GerenciamentoAlunosApplication;
import org.openqa.selenium.JavascriptExecutor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioAcceptanceTest
{
	private WebDriver driver;
	JavascriptExecutor js;
	private ConfigurableApplicationContext context;
	
	@Before
	public void setUp()
	{
		SpringApplication application = new SpringApplication(GerenciamentoAlunosApplication.class);
		context = application.run();
		driver = new ChromeDriver();
		js = (JavascriptExecutor)driver;
	}

	@After
	public void tearDown()
	{
		driver.quit();
		context.close();
	}

	@Test
	public void usuariologin() throws Exception
	{
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		driver.get("http://localhost:8080/");
		wait.until(d -> d.findElement(By.linkText("Clique aqui para se cadastrar")).isDisplayed());
		driver.findElement(By.linkText("Clique aqui para se cadastrar")).click();
		wait.until(d -> d.findElement(By.id("email")).isDisplayed());
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).sendKeys("aaaaa@a.a");
		driver.findElement(By.id("user")).click();
		driver.findElement(By.id("user")).sendKeys("aaaaa");
		driver.findElement(By.id("senha")).click();
		driver.findElement(By.id("senha")).sendKeys("aaaaa");
		driver.findElement(By.cssSelector(".btn-primary")).click();
		wait.until(d -> d.findElement(By.id("user")).isDisplayed());
		driver.findElement(By.id("user")).click();
		driver.findElement(By.id("user")).sendKeys("aaaaa");
		driver.findElement(By.id("senha")).click();
		driver.findElement(By.id("senha")).sendKeys("aaaaa");
		driver.findElement(By.cssSelector(".btn")).click();
		wait.until(d -> d.findElement(By.className("texto-centro")).isDisplayed());
		Assert.assertFalse(driver.findElements(By.className("btn-nav-sair")).isEmpty());
	}
}