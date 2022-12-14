package com.junit.prac.service;

import com.junit.prac.domain.Book;
import com.junit.prac.dto.response.BookListResDto;
import com.junit.prac.dto.response.BookRespDto;
import com.junit.prac.dto.request.BookSaveReqDto;
import com.junit.prac.repository.BookRepository;
import com.junit.prac.util.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    //1.책 등록
    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto insertBook(BookSaveReqDto dto){
       Book bookPs = bookRepository.save(dto.toEntity());
       if (bookPs != null){
           //메일보내기 메서드 호출 (return true or false)
           if (!mailSender.send()){
               throw new RuntimeException("메일이 전송되지 않았습니다.");
           }
       }
       return bookPs.toDto();
    }
    //2.책 목록보기
    public BookListResDto findAll(){
        List<BookRespDto> dtos =  bookRepository.findAll().stream()
//                .map(bookPs->new BookRespDto().toDto(bookPs))
                .map(Book::toDto)
                .collect(Collectors.toList());

        BookListResDto bookListResDto = BookListResDto.builder().bookList(dtos).build();

        return bookListResDto;
    }

    //3.책 한 건 보기
    public BookRespDto findOne(Long id){
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()){
            Book bookPs = bookOP.get();
            return bookPs.toDto();
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }
    //4.책 삭제
    @Transactional(rollbackOn = RuntimeException.class)
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    //5.책 수정
    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto updateBook(Long id,BookSaveReqDto dto){
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()){
            Book bookPs = bookOP.get();
            bookPs.update(dto.getTitle(),dto.getAuthor());
            return bookPs.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }

    } //메서드 종료시 더티체킹(flush)으로 update 됨.
}
