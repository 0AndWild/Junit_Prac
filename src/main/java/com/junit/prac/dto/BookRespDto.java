package com.junit.prac.dto;

import com.junit.prac.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    public BookRespDto toDto(Book bookPs){
        this.id = bookPs.getId();
        this.title = bookPs.getTitle();
        this.author = bookPs.getAuthor();
        return this;
    }
}
