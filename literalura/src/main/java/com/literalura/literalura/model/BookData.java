package com.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        @JsonAlias("id") Integer idGutendex,
        String title,
//        List<String> subjects,
        List<AuthorData> authors,
        List<String> languages,
        @JsonAlias("download_count") Integer downloads
) {
}
