package com.junit.prac.service;

import com.junit.prac.domain.Book;
import com.junit.prac.dto.response.BookListResDto;
import com.junit.prac.dto.response.BookRespDto;
import com.junit.prac.dto.request.BookSaveReqDto;
import com.junit.prac.repository.BookRepository;
import com.junit.prac.util.MailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void 책목록보기_테스트(){
        //given

        //stub
        List<Book> books = Arrays.asList(
                new Book(1L,"Junit5","건영"),
                new Book(2L, "Spring","java")
        );
        when(bookRepository.findAll()).thenReturn(books);
        //when
        BookListResDto bookListResDto = bookService.findAll();

        //then
        assertThat(bookListResDto.getItems().get(0).getTitle()).isEqualTo("Junit5");
        assertThat(bookListResDto.getItems().get(0).getAuthor()).isEqualTo("건영");
        assertThat(bookListResDto.getItems().get(1).getTitle()).isEqualTo("Spring");
        assertThat(bookListResDto.getItems().get(1).getAuthor()).isEqualTo("java");
    }

    @Test
    public void 책한건보기_테스트(){
        //given
        Long id = 1L;
        Book book = new Book(1L,"Junit5","건영");
        Optional<Book> bookOP = Optional.of(book);
        //stub
        when(bookRepository.findById(1L)).thenReturn(bookOP);

        //when
        BookRespDto bookRespDto = bookService.findOne(id);

        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기_테스트(){
        //given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Spring");
        dto.setAuthor("건영Update");

        //stub
        Book book = new Book(1L,"Junit5","건영");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);
        //when
        BookRespDto bookRespDto = bookService.updateBook(id,dto);
        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

}
