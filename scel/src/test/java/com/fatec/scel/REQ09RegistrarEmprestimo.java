package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scel.model.Aluno;
import com.fatec.scel.model.AlunoRepository;
import com.fatec.scel.model.Emprestimo;
import com.fatec.scel.model.EmprestimoRepository;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;
import com.fatec.scel.servico.EmprestimoServico;
@SpringBootTest
class REQ09RegistrarEmprestimo {
	@Autowired
	LivroRepository repositoryLivro;
	@Autowired
	AlunoRepository repositoryAluno;
	@Autowired
	EmprestimoRepository emprestimoRepository;
	@Autowired
	EmprestimoServico emprestimoServico;
	Livro livro;
	Emprestimo emprestimo;
	
	@Test
	void ct01_quando_emprestimo_valido_retorna1() {
		// Dado – que o atendente tem um livro não cadastrado
		repositoryLivro.deleteAll();
		// Quando – o atendente cadastra o livro
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repositoryLivro.save(livro);
		Aluno aluno = new Aluno("aaaa", "jose da silva","jose@gmail.com", "08545160", "Rua Carlos de Carvalho, 200");
		repositoryAluno.save(aluno);
		Emprestimo emprestimo = new Emprestimo ("3333","aaaa");
		emprestimoServico.save(emprestimo);
		assertEquals (1, emprestimoServico.count());
		repositoryLivro.deleteAll();
	}
	
	@Test
	void ct02_quando_status_do_livro_ja_emprestado_retorna0() {
		// Dado – que não existem livro, aluno e emprestimo cadastrados
		repositoryLivro.deleteAll();
		repositoryAluno.deleteAll();
		emprestimoRepository.deleteAll();
		// Quando – o atendente cadastra um emprestimo para um livro ja emprestado
		Livro livro = new Livro("1111", "Teste de Software", "Delamaro");
		livro.setStatus(2);
		repositoryLivro.save(livro);
		Aluno aluno = new Aluno("bbbb", "jose da silva","jose@gmail.com", "08545160", "Rua Carlos de Carvalho, 200");
		repositoryAluno.save(aluno);
		Emprestimo emprestimo = new Emprestimo ("1111","bbbb");
		emprestimoServico.save(emprestimo);
		//Então retorna 0
		assertEquals (0, emprestimoServico.count());
	}
}
