package com.fatec.scel.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scel.model.Livro;
import com.fatec.scel.servico.LivroServico;

@Controller
@RequestMapping(path = "/livros")
public class LivroController {
	Logger logger = LogManager.getLogger(LivroController.class);
	@Autowired
	LivroServico servico;

	@GetMapping("/cadastrar")
	public ModelAndView retornaFormDeCadastroDe(Livro livro) {
		ModelAndView mv = new ModelAndView("cadastrarLivro");
		mv.addObject("livro", livro);
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Livro livro, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("cadastrarLivro");
		try {
			servico.save(livro);
			modelAndView.addObject("livros", servico.findAll());
			modelAndView.addObject("message", "Livro cadastrado");
		} catch (Exception e) { // captura validacoes na camada de persistencia
			if (result.hasErrors()) {
				logger.info("======================> entrada de dados invalida na pagina cadastrar livro");
				modelAndView.addObject("message", "");
			} else {
				modelAndView.addObject("message", "Livro ja cadastrado");
			}
		}
		return modelAndView;
	}
}