package com.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.Assessment;
import com.example.domain.Book;
import com.example.domain.User;
import com.example.mapper.AssessmentMapper;
import com.example.pojo.QueryInfo;
import com.example.service.BookService;
import com.example.service.UserService;
import com.example.util.PageGetUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class RatingSystemServerApplicationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Autowired
    private UserService userService;

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

    @Test
    public void testGetAssessments(){
        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setQuery("");
        queryInfo.setPagesize(99999);
        queryInfo.setPagenum(1);
        Map<String,Object> map = PageGetUtil.getPage(assessmentMapper, queryInfo);
        // 从map中获取result,根据result中的user的id查询user,将user放入result中
        // 根据result中的objectType与object的id查询object,将object放入result中
        Map<String,Object> resultMap = new HashMap<>();
        List<Assessment> resultList = (List<Assessment>) map.get("result");
        for(Assessment assessmentView : resultList){
            assessmentView.setUser(userService.getUserByUsername(assessmentView.getUsername()));
            if(assessmentView.getObjecttype().equals("book")){
                Book book = bookService.getById(assessmentView.getObjectid());
                assessmentView.setWork(book);
            }
        }
        resultMap.put("result", resultList);
        System.out.println(resultList);
    }

    @Test
    public void testSubString(){
        System.out.println("0123456789".substring(5));
        System.out.println("usernamew".substring(8));
        QueryInfo queryInfo = new QueryInfo("usernamew",1,99999);
        QueryWrapper wrapper = new QueryWrapper();
        queryInfo.setQuery(queryInfo.getQuery().substring(8));
        wrapper.like("username", queryInfo.getQuery());
        Page page = new Page<>(queryInfo.getPagenum(), queryInfo.getPagesize());
        Page list = new Page();
        list = assessmentMapper.selectPage(page, wrapper);
        // 打印list
        System.out.println(list.getRecords());
    }

    @Test
    public void testBcryptPasswordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for(int i=1;i<20;i++){
            String password1 = encoder.encode("123456");
            System.out.println(password1);
        }
        String password1 = encoder.encode("123456");
        System.out.println(password1);
        String password2 = "123456";
        System.out.println(encoder.matches(password2, password1));
        System.out.println(encoder.matches(password2,"$2a$10$rkMZnTJz5I2xt9x3WzQJ1OyqBRg1TNGjeFEU0uof4PtIPoqyxx8ca"));
    }
}
