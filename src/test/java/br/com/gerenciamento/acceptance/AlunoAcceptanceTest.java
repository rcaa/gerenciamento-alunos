package br.com.gerenciamento.acceptance;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.Test;

public class AlunoAcceptanceTest {

    @Test
    public void testLogin() {
        // criação do Playwright e inicialização do navegador
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            // acessa a aplicação
            page.navigate("http://localhost:8080");

            // aguarde um pouco a página carregar por completo
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // preencha o campo "username"
            page.fill("input[placeholder='username']", "usuarioTeste");

            // preencha o campo "senha"
            page.fill("input[placeholder='senha']", "senha123");

            // clique no botão de login
            page.click("button:has-text('Login')");

            // validação de redirecionamento ou alerta de erro
            boolean loginSucesso = page.url().contains("home") || page.locator(".alert-danger").isVisible();

            if (loginSucesso) {
                System.out.println("Teste de login executado com sucesso!");
            } else {
                System.out.println("Teste de login falhou!");
            }

            // feche o navegador
            browser.close();
        }
    }
}
