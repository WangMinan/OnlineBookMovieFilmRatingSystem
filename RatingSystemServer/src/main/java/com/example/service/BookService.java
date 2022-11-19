package com.example.service;

import com.example.domain.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author wangminan
* @description 针对表【Book】的数据库操作Service
* @createDate 2022-11-19 20:42:40
*/
@Service
public interface BookService extends IService<Book> {

}
