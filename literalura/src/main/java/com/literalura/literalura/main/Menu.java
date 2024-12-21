package com.literalura.literalura.main;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.literalura.literalura.model.Author;
import com.literalura.literalura.model.Book;
import com.literalura.literalura.model.BookData;
import com.literalura.literalura.model.Data;
import com.literalura.literalura.repository.AuthorRepository;
import com.literalura.literalura.repository.BookRepository;
import com.literalura.literalura.service.ApiFetcher;
import com.literalura.literalura.service.DataConverter;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ApiFetcher apiFetcher = new ApiFetcher();
    private DataConverter converter = new DataConverter();
    private Scanner keyboard = new Scanner(System.in);
    private BookRepository repository;
    private AuthorRepository authorRepository;

    public Menu(BookRepository repository, AuthorRepository autorRepository) {
        this.repository = repository;
        this.authorRepository = autorRepository;
    }

    public void showMenu() throws JsonMappingException {
        Integer option = -1;
        while (option != 0) {
            var menu = """
                    1 - Buscar libro
                    2 - Todos los libros
                    3 - Todos los autores
                    4 - Autores vivos en un año
                    5 - Libros por idioma
                                  
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
                case 2:
                    allBooks();
                    break;
                case 3:
                    allAuthors();
                    break;
                case 4:
                    authorAliveInYear();
                    break;
                case 5:
                    totalBooksByLanguaje();
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

    private void allBooks() {
        List<Book> bookList = repository.findAll();
        System.out.println("***** Libros registrados *****");
        bookList.stream().forEach(b -> System.out.println(b.toString()));
        System.out.println("********************");
    }

    private void allAuthors() {
        List<Author> authorList = authorRepository.findAll();
        System.out.println("***** Autores registrados *****");
        authorList.stream().forEach(a -> System.out.println(a.toString()));
        System.out.println("********************");
    }

    private void authorAliveInYear() {
        System.out.println("Digita el año ");
        Integer year = keyboard.nextInt();
        keyboard.nextLine();
        List<Author> autores = authorRepository.findAuthorsAliveInYear(year);
        System.out.println("**** Autores vivos en el año " + year + " ******");
        autores.stream().forEach(a -> System.out.println(a.toString()));
        System.out.println("**************************");
    }

    private void totalBooksByLanguaje() {
        var menu = """
                    Selecciona el lenguaje: 
                    1 - Español
                    2 - ingles   
                    0 - Salir
                    """;
        System.out.println(menu);
        Integer option = keyboard.nextInt();
        keyboard.nextLine();

        switch (option){
            case 0 :
                System.out.println("Ningun lenguaje seleccionado");
                break;
            case 1:
                groupByLanguaje("es");
                break;
            case 2:
                groupByLanguaje("en");
            default:
                System.out.println("Opcion no valida");
        }
    }

    private void groupByLanguaje(String language) {
        List<Book> books = repository.findAll();
        Long count = books.stream().filter(book -> book.getLanguages() != null && book.getLanguages().contains(language))
                .count();
        System.out.println("****** Total de libros por idioma ***********");
        System.out.println("Conteo :" + count);
        System.out.println("***************************");
    }

}
