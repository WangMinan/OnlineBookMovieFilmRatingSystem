package com.example;

import com.example.domain.Book;
import com.example.pojo.QueryInfo;
import com.example.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RatingSystemServerApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    public void testGetListFromPage(){
        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setQuery("");
        queryInfo.setPagesize(99999);
        queryInfo.setPagenum(1);
        List<Book> bookList = (List<Book>) bookService.getAllBooks(queryInfo).get("result");
        for(Book book : bookList){
            System.out.println(book);
        }
    }
}
