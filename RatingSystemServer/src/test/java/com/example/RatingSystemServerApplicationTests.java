package com.example;

import com.example.domain.AssessmentView;
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
        List<AssessmentView> resultList = (List<AssessmentView>) map.get("result");
        for(AssessmentView assessmentView : resultList){
            assessmentView.setUser(userService.getUserByUsername(assessmentView.getUsername()));
            if(assessmentView.getObjecttype().equals("book")){
                Book book = bookService.getById(assessmentView.getObjectid());
                assessmentView.setWork(book);
            }
        }
        resultMap.put("result", resultList);
        System.out.println(resultList);
    }
}
