package com.fatec.scel.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageCadastrarLivro {
	private WebDriver driver;
	private By isbnBy = By.id("isbn");
	private By autorBy = By.id("autor");
	private By tituloBy = By.id("titulo");
	private By btnCadastrarBy = By.cssSelector(".btn-primary:nth-child(1)");//By.id("btnCadastrar");
	private By mensagemBy = By.cssSelector(".text-danger"); //By.id("mensagem");
	public PageCadastrarLivro(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Cadastrar livro
	 * 
	 * @param isbn
	 * @param autor
	 * @param titulo
	 * @return – um objeto do tipo pagina é retornado
	 */
	public PageCadastrarLivro cadastrar(String isbn, String autor, String titulo) {
		driver.findElement(isbnBy).sendKeys(isbn);
		driver.findElement(autorBy).sendKeys(autor);
		driver.findElement(tituloBy).sendKeys(titulo);
		driver.findElement(btnCadastrarBy).click();
		return new PageCadastrarLivro(driver);
	}

	/*
	 * Obtem a mensagem apresentada na tela ao usuario.
	 */
	public String getMessageText() {
		return driver.findElement(mensagemBy).getText();
	}
}