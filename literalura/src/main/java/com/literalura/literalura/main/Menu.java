package com.literalura.literalura.main;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.literalura.literalura.model.Book;
import com.literalura.literalura.model.BookData;
import com.literalura.literalura.model.Data;
import com.literalura.literalura.repository.BookRepository;
import com.literalura.literalura.service.ApiFetcher;
import com.literalura.literalura.service.DataConverter;

import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ApiFetcher apiFetcher = new ApiFetcher();
    private DataConverter converter = new DataConverter();
    private Scanner keyboard = new Scanner(System.in);
    private BookRepository repository;

    public Menu(BookRepository repository) {
        this.repository = repository;
    }

    public void showMenu() throws JsonMappingException {
        Integer option = -1;
        while (option != 0) {
            var menu = """
                    1 - Buscar libro
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 0 :
                    System.out.println("Cerrando LiterAlura :)");
                    break;
                case 1:
                    buscarLibro();
                    break;
                default:
                    System.out.println("Digite un numero valido");
            }
        }

    }

    private void buscarLibro() throws JsonMappingException {
        System.out.println("Digita el libro que deseas buscar :");
        String search = keyboard.nextLine();
        String urlSearch = URL_BASE + search.toLowerCase().trim().replace(" ","%20");
        String book = apiFetcher.fetchData(urlSearch);
        Data bookData = converter.getData(book, Data.class);
        if (bookData.booksFind().isEmpty()) {
            System.out.println("No se encontro el libro con titulo: " + search);
        } else {
            System.out.println(bookData);
            Book saveBook = new Book(bookData.booksFind().get(0));
            System.out.println(saveBook);
            Optional<Book> bookExist = repository.findByIdGutendex(saveBook.getIdGutendex());
            if (bookExist.isEmpty()) {
                repository.save(saveBook);
                System.out.println("***Serie encontrada***");
                System.out.println(saveBook.toString());
                System.out.println("**********************");
            } else {
                System.out.println("*** Serie existente ***");
                System.out.println(saveBook.toString());
                System.out.println("**********************");
            }


        }

    }
}
