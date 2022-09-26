package com.junit.prac.service;

import com.junit.prac.dto.BookRespDto;
import com.junit.prac.dto.BookSaveReqDto;
import com.junit.prac.repository.BookRepository;
import com.junit.prac.util.MailSenderStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;
    @Test //문제점 -> 서비스만 테스트하고 싶은데, 레포지토리 레이어가 함께 테스트 된다는 점.
    public void 책등록하기_테스트(){
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Junit5");
        dto.setAuthor("건영");

        //stub
        MailSenderStub mailSenderStub = new MailSenderStub();
        //가짜로 bookRepository 만들기 필요!

        //when
        BookService bookService = new BookService(bookRepository,mailSenderStub);
        BookRespDto bookRespDto = bookService.insertBook(dto);

        //then
        assertEquals(dto.getTitle(),bookRespDto.getTitle());
        assertEquals(dto.getAuthor(),bookRespDto.getAuthor());
    }


}
