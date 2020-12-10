package com.fatec.scel.servico;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scel.model.Aluno;
import com.fatec.scel.model.AlunoRepository;
import com.fatec.scel.model.Emprestimo;
import com.fatec.scel.model.EmprestimoRepository;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@Service
public class EmprestimoServicoI implements EmprestimoServico{
	Logger logger = LogManager.getLogger(EmprestimoServicoI.class);
	@Autowired
	private EmprestimoRepository emprestimoRepository;
	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	
	public String save (Emprestimo emprestimo) {
		String mensagem="";
	    try {	
		
		if (emprestimosEmAberto (emprestimo) == false  && emprestimoValido(emprestimo )== true)	{
			logger.info("======================> 3- achou livro/aluno no db e nao existe emprestimo em aberto");
			emprestimo.setDataEmprestimo();
			emprestimoRepository.save(emprestimo);
			
			mensagem = "Emprestimo registrado";

		} else {
			logger.info("======================> 3 - não achou livro/aluno no db");
			mensagem = "Livro/Aluno não localizado ou emprestimo em aberto";

		}
	    }catch(Exception e) {
	    	logger.info("erro nao esperado no cadastro de emprestimo ===> " + e.getMessage());
	    	mensagem="Erro não esperado contacte o administrador";
	    }
		return mensagem;
	}
	public Iterable<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}
	public void deleteById(Long id) {
		emprestimoRepository.deleteById(id);
	}
	public List<Emprestimo> findByRa (String ra) {
		return emprestimoRepository.findByRa(ra);
	}
	
	public boolean emprestimosEmAberto(Emprestimo emprestimo) {
		List<Emprestimo> emprestimos = findByRa(emprestimo.getRa());
		boolean existe=false;
		for (Emprestimo umEmprestimo : emprestimos) {
			if (umEmprestimo.getDataDevolucao() == null) {
				existe = true;
			}
		}
		logger.info("=====================> 1- verifica se existem emprestimos em aberto? " + existe);
		return existe;
	}
	public boolean emprestimoValido(Emprestimo emprestimo) {
		boolean aluno_e_livro_disponivel = false;
		Aluno aluno = alunoRepository.findByRa(emprestimo.getRa());
		Livro livro = livroRepository.findByIsbn(emprestimo.getIsbn());
		if(aluno != null && livro != null && livro.getStatus().equals("disponivel") ){
			livro.setStatus(2);
			aluno_e_livro_disponivel = true;
		}
		logger.info("=====================> 2- verifica se existe aluno e livro esta disponível =" + aluno_e_livro_disponivel);
		return aluno_e_livro_disponivel;
	}
	public long count() {
		return emprestimoRepository.count();
	}
}
