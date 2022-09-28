package com.junit.prac.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

//책목록보기 조회시 List 형태가 아닌 Object 형태로 맞추어 return 하기위해 생성
@Getter
public class BookListResDto {
    List<BookRespDto> items;

    @Builder
    public BookListResDto(List<BookRespDto> bookList){
        this.items = bookList;
    }
}
