package com.fatec.scel.dd;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.fatec.scel.model.LivroRepository;
import com.fatec.scel.po.PageCadastrarLivro;
import org.springframework.boot.test.context.SpringBootTest;

class REQ01CadastrarLivro {
	static private WebDriver driver;
	static JavascriptExecutor js;
	private PageCadastrarLivro pageCadastrarLivro;
	private static Logger logger;
	@Autowired
	LivroRepository repository;
	@BeforeAll
	public static void inicializa() {
		logger = LogManager.getLogger(REQ01CadastrarLivro.class);
		driver = DriverFactory.getDriver();
		js = (JavascriptExecutor) driver;
		try {
			ManipulaExcel.setExcelFile("E:\\dd\\cadastrarUsuario.xlsx", "Planilha1");
		} catch (Exception e) {
			logger.info("Erro no path ou workbook =============>" + e.getMessage());
		}
	}

	@AfterAll
	public static void tearDown() {
		DriverFactory.finaliza();
	}

	@Test
	public void cadastrarLivro() throws Exception {
		// se o campo for lancado na planilha como numerico mas a entrada eh String deve
		// ser tratado ou na planilha indicando string ou aqui transformando para string
		int linha = 1; // linha 0 cabecalho
		while (!ManipulaExcel.getCellData(linha, 0).equals("final".trim())) {
			logger.info("==============> linha = " + linha);
			pageCadastrarLivro = new PageCadastrarLivro(driver);
			driver.get("http://localhost:8080/livros/cadastrar");
			try {
				pageCadastrarLivro.cadastrar(ManipulaExcel.getCellData(linha, 0), ManipulaExcel.getCellData(linha, 1),
						ManipulaExcel.getCellData(linha, 2));
				assertEquals(ManipulaExcel.getCellData(linha, 3), pageCadastrarLivro.getMessageText());
			} catch (Exception e) {
				fail("Erro nao esperado - " + e.getMessage());
				logger.info("Erro nao esperado=" + e.getMessage());
			}
			linha = linha + 1;
		}
	}

}
