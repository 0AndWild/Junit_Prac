package com.junit.prac.domain;

import com.junit.prac.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

    //1. 책 등록
    @Test
    public void 책등록_Test(){
        //given (데이터 준비)
        String title = "Junit5";
        String author = "건영";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        //when(테스트 실행)
        //PS = persistence 즉, DB에 저장되어 영속화된
        Book bookPS = bookRepository.save(book);

        //then(검증)
        assertEquals(title,bookPS.getTitle());
        assertEquals(author,bookPS.getAuthor());

    }
    //2. 책 목록보기

    //3. 책 한건 보기

    //4. 책 수정

    //5. 책 삭제

}
