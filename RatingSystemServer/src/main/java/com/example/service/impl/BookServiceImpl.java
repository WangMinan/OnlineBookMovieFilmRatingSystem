package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Book;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.BookService;
import com.example.mapper.BookMapper;
import com.example.util.PageGetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @author wangminan
* @description 针对表【Book】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Map<String, Object> getAllBooks(QueryInfo queryInfo) {
        return PageGetUtil.getPage(bookMapper, queryInfo);
    }

    @Override
    public R addBook(Book book) {
        return bookMapper.insert(book) > 0 ? R.ok("添加图书成功") : R.error("添加图书失败");
    }

    @Override
    public R deleteBook(long id) {
        return bookMapper.deleteById(id) > 0 ? R.ok("删除图书成功") : R.error("删除图书失败");
    }

    @Override
    public R updateBook(long id, Book book) {
        book.setId(id);
        return bookMapper.updateById(book) > 0 ? R.ok("更新图书成功") : R.error("更新图书失败");
    }
}




