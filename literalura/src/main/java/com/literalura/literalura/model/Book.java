package com.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    private List<String> languages;
    private Integer downloads;
    private Integer idGutendex;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors;

    public Book() {}

    public Book(BookData bookData) {
        this.title = bookData.title();
        this.languages = bookData.languages();
        this.downloads = bookData.downloads();
        this.idGutendex = bookData.idGutendex();
        List<Author> authorList = new ArrayList<>();
        for (AuthorData authorData : bookData.authors()) {
            Author author = new Author(authorData);
            author.setBook(this);
            authorList.add(author);
        }
        this.authors = authorList;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", languages=" + languages +
                ", downloads=" + downloads +
                ", idGutendex=" + idGutendex +
                ", authors=" + authors;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Integer getIdGutendex() {
        return idGutendex;
    }

    public void setIdGutendex(Integer idGutendex) {
        this.idGutendex = idGutendex;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
