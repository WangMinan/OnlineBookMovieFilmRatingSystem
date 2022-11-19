package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Book;
import com.example.service.BookService;
import com.example.mapper.BookMapper;
import org.springframework.stereotype.Service;

/**
* @author wangminan
* @description 针对表【Book】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{

}




