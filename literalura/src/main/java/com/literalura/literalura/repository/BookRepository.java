package com.literalura.literalura.repository;

import com.literalura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    //Buscar libro por id de gutendex
    Optional<Book> findByIdGutendex(Integer idGutendex);
}
