package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@SpringBootTest
class REQ01CadastrarLivroTests {
	@Autowired
	LivroRepository repository;
	Livro livro;

	private Validator validator;
	private ValidatorFactory validatorFactory;
	
	
	@Test
	void ct01_quando_dados_validos_isbn_nao_cadastrado_retorna1() {

		// Dado – que o atendente tem um livro não cadastrado
		repository.deleteAll();
		// Quando – o atendente cadastra o livro
		//Livro livro = Livro.builder().comIsbn("1111").comTitulo("engenharia").comAutor("delamaro");
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
		// Então – o sistema verifica os dados E confirma a operação
		assertEquals(1, repository.count());
	}

	@Test
	void ct02_quando_dados_validos_violacoes_retorna_vazio() {
		// Dado – que o atendente tem um livro não cadastrado
		repository.deleteAll();
		// Quando – o atendente cadastra um livro com informações válidas
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		// Então – o sistema verifica os dados E confirma a operação.
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		assertTrue(violations.isEmpty());
	}

	@Test
	void ct03_quando_titulo_branco_retorna_msg_titulo_invalido() {
		// Dado – que o atendente tem um livro não cadastrado
		repository.deleteAll();
		// Quando – o atendente cadastra um livro com informações válidas
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Livro livro = new Livro("3333", "", "Delamaro");
		// Então – o sistema verifica os dados E confirma a operação.
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// Então – o sistema valida as informações E retorna mensagem titulo invalido.
		// assertFalse(violations.isEmpty());
		// assertEquals(violations.size(), 1);
		assertEquals("Titulo deve ter entre 1 e 50 caracteres", violations.iterator().next().getMessage());

	}

	// unique=true em @Column anotação será usada apenas em DDL generation, as
	// verificações de
	// chave primaria acontecem na camada de persistência.
	@Test
	void ct04_quando_isbn_ja_cadastrado_deve_retornar1() {
		// Dado – que o ISBN do livro ja está cadastrado
		repository.deleteAll();
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
		// Quando – o usuário registra as informações do livro e confirma a operação
		repository.save(livro);
		// Então – o sistema valida as informações E retorna a quantidade de registros
		// inseridos igual a 1.
		assertEquals(1, repository.count());
	}
}
