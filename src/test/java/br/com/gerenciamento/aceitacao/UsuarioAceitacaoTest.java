package br.com.gerenciamento.aceitacao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.gerenciamento.GerenciamentoAlunosApplication;

public class UsuarioAceitacaoTest {
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
    public void fazerLogin() {
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
    }
}
