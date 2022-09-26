package com.junit.prac.service;

import com.junit.prac.dto.BookRespDto;
import com.junit.prac.dto.BookSaveReqDto;
import com.junit.prac.repository.BookRepository;
import com.junit.prac.util.MailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //가짜환경을 만들어 @Mock 를 띄우고 @InjectMocks에 의존성 주입을 해줌
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기_테스트(){
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Junit5");
        dto.setAuthor("건영");

        //stub(가설)
        when(bookRepository.save(ArgumentMatchers.any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);
        //when
        BookRespDto bookRespDto = bookService.insertBook(dto);

        //then
//        assertEquals(dto.getTitle(),bookRespDto.getTitle());
//        assertEquals(dto.getAuthor(),bookRespDto.getAuthor());
        /*AssertJ*/
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }


}
