package com.junit.prac.domain;

import com.junit.prac.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;
    //@BeforeAll //테스트 시작전에 한번만 실행
    @BeforeEach //각 테스트 시작전에 한번씩 실행
    public void 데이터준비(){
//        System.out.println("=====================================================");
        String title = "Junit5";
        String author = "건영";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }//트랜잭션이 종료 됐다면 말이안됨
    //가정1 : [데이터준비() + 1 책등록] (T) , [데이터준비() + 2 책목록보기] (T) -> 책 목록보기 findAll() 사이즈 1 (검증완료)
    //가정2 : [데이터준비() + 1 책등록 + 데이터준비() + 2 책목록보기] (T) -> 책 목록보기 findAll() 사이즈 2 (검증 실패)


    //1. 책 등록
    @Test
    @Order(1)
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
    }//트랜잭션 종료 (저장된 데이터를 초기화함)

    @Test
    @Order(2)
    //2. 책 목록보기
    public void 책목록보기_test(){
        // given
        String title = "Junit5";
        String author = "건영";

        //when
        List<Book> booksPS = bookRepository.findAll();
        //@BeforeEach 트랜잭션 검증
        System.out.println("========================"+ "사이즈 = " + booksPS.size()+ "==============================");

        //then
        assertEquals(title,booksPS.get(0).getTitle());
        assertEquals(author,booksPS.get(0).getAuthor());
    }//트랜잭션 종료 (저장된 데이터를 초기화함)

    //3. 책 한건 보기
    @Sql("classpath:db/tableInit.sql") //Id 값을 조회하는 곳에는 table을 drop해주는 설정을 걸어두는게 좋음
    @Test
    @Order(3)
    public void 책한건보기_test(){
        //given
        String title = "Junit5";
        String author = "건영";

        //when
        Book bookPS = bookRepository.findById(1L).get();

        //then
        assertEquals(title,bookPS.getTitle());
        assertEquals(author,bookPS.getAuthor());
    }
    //4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    @Order(4)
    public void 책삭제_test(){
        //given
        Long id = 1L;
        //then
        bookRepository.deleteById(id);
        //when
        assertFalse(bookRepository.findById(id).isPresent()); //False이면 성공(=데이터를 삭제했으니 존재하지않음 , isPresent() ==false)
    }
    //5. 책 수정

}
