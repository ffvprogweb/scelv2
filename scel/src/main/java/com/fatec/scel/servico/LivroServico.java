package com.fatec.scel.servico;

import com.fatec.scel.model.Livro;

public interface LivroServico {
	public Iterable<Livro> findAll();
	public void save(Livro livro);
	
}
