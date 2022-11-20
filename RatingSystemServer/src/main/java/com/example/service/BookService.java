package com.example.service;

import com.example.domain.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author wangminan
* @description 针对表【Book】的数据库操作Service
* @createDate 2022-11-19 20:42:40
*/
@Service
public interface BookService extends IService<Book> {

    Map<String, Object> getAllBooks(QueryInfo queryInfo);

    R addBook(Book book);

    R deleteBook(long id);

    R updateBook(long id, Book book);
}
