package com.literalura.literalura.repository;

import com.literalura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    //Buscar libro por id de gutendex
    Optional<Book> findByIdGutendex(Integer idGutendex);
}
