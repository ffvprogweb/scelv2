package com.fatec.scel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;

import com.fatec.scel.model.Livro;

@SpringBootTest
@AutoConfigureMockMvc
class REQ01CadastrarLivroWebTests {

	@Autowired
	MockMvc mockMvc;// simula o processamento de uma requisicao web

	@Test
	public void ct01_quando_seleciona_cadastrar_livro_retorna_http_200() throws Exception {
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/livros/cadastrar"));
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void ct02_quando_seleciona_cadastrar_retorna_view() throws Exception {
		// Dado – que o sistema está disponível
		// Quando – o usuário faz uma solicitação do tipo GET
		// localhost:8080/livros/cadastrar
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/livros/cadastrar"));
		ViewResultMatchers view = MockMvcResultMatchers.view();
		// Então – retorna o nome logico da view de cadastro de livro
		resultActions.andExpect(view.name(Matchers.is("cadastrarLivro")));
	}
	@Test
	public void ct03_quando_titulo_branco_retorna_size() throws Exception {
	//Dado – que o livro nao esta cadastrado
	//Quando - o usuario deixa o titulo em branco
	mockMvc.perform(post("/livros/save") //simula uma requisicao post entrada pelo usuario
	.param("isbn", "1111")
	.param("titulo", "")
	.param("autor", "Sommerville")
	)
	//Então - retorna erro
	.andExpect(MockMvcResultMatchers.status().is(200)) //404
	.andExpect(MockMvcResultMatchers.view().name("cadastrarLivro"))
	.andExpect(MockMvcResultMatchers.model().attribute("livro", Matchers.any(Livro.class)))
	.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("livro","titulo"))
	.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("livro", "titulo", "Size"));
	}
}
