package com.literalura.literalura;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.literalura.literalura.main.Menu;
import com.literalura.literalura.model.BookData;
import com.literalura.literalura.model.Data;
import com.literalura.literalura.repository.AuthorRepository;
import com.literalura.literalura.repository.BookRepository;
import com.literalura.literalura.service.ApiFetcher;
import com.literalura.literalura.service.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private BookRepository repository;
	@Autowired
	private AuthorRepository authorRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonMappingException {
		Menu menu = new Menu(repository, authorRepository);
		menu.showMenu();
	}

}
