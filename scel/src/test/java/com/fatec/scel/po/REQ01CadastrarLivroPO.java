package com.fatec.scel.po;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class REQ01CadastrarLivroPO {

	private WebDriver driver;

	@Test
	public void ct01_quando_dados_validos_retorna_livro_cadastrado() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/livros/cadastrar");
		PageCadastrarLivro pageLivro = new PageCadastrarLivro(driver);
		pageLivro.cadastrar("1111", "Sommerville", "Engenharia");
		assertEquals("Livro cadastrado", pageLivro.getMessageText());
		driver.quit();
	}

}
