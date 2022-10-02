package com.junit.prac.controller;

import com.junit.prac.dto.response.BookListResDto;
import com.junit.prac.dto.response.BookRespDto;
import com.junit.prac.dto.request.BookSaveReqDto;
import com.junit.prac.dto.response.CMRespDto;
import com.junit.prac.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    //1.책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
        //AOP 처리하는게 좋음음!!
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage()); //getField 는 에러위치가 title인지 author인지 알려줌
            }
            System.out.println("==========================");
            System.out.println(errorMap.toString());
            System.out.println("==========================");
            throw new RuntimeException(errorMap.toString()); //parsing 하다 터지면 GlobalException이 catch 그 이후로 터지면 bindingResult.hasErrors()가 catch
        }
        BookRespDto bookRespDto = bookService.insertBook(bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 저장 성공").body(bookRespDto).build(), HttpStatus.CREATED); //데이터가 insert되면 서버쪽에서 상태코드 201 return
    }

    @GetMapping("api/v1/book")
    //2.책목록조회
    public ResponseEntity<?> getBookList() {
        BookListResDto bookList = bookService.findAll();
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 목록보기 성공").body(bookList).build(),
                HttpStatus.OK); // 200 = ok;
    }

    //3.책한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookRespDto bookRespDto = bookService.findOne(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 한건보기 성공").body(bookRespDto).build(),
                HttpStatus.OK);
    }

    //4.책삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 삭제 성공").body(null).build(),
                HttpStatus.OK);
    }

    //5.책수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
        //AOP 처리하는게 좋음음!!
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage()); //getField 는 에러위치가 title인지 author인지 알려줌
            }
            throw new RuntimeException(errorMap.toString()); //parsing 하다 터지면 GlobalException이 catch 그 이후로 터지면 bindingResult.hasErrors()가 catch
        }
        BookRespDto bookRespDto = bookService.updateBook(id,bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 수정하기 성공").body(bookRespDto).build(),
                HttpStatus.OK);
    }

}
