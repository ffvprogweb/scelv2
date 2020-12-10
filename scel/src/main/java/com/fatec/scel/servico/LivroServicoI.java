package com.fatec.scel.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;
@Service
public class LivroServicoI implements LivroServico {

	Logger logger = LogManager.getLogger(LivroServico.class);
	@Autowired
	private LivroRepository repository;

	public Iterable<Livro> findAll() {
		return repository.findAll();
	}

	public void save(Livro livro) {
		repository.save(livro);
	}
	

}
