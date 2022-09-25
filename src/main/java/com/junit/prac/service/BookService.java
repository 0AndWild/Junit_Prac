package com.junit.prac.service;

import com.junit.prac.domain.Book;
import com.junit.prac.dto.BookRespDto;
import com.junit.prac.dto.BookSaveReqDto;
import com.junit.prac.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    //책 등록
    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto dto){
       Book bookPs = bookRepository.save(dto.toEntity());
       return new BookRespDto().toDto(bookPs);
    }
    //책 목록보기

    //책 한 건 보기

    //책 삭제

    //책 수정
}
