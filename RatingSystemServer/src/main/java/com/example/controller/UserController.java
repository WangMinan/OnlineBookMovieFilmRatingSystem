package com.example.controller;

import com.example.domain.Assessment;
import com.example.domain.User;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : [wangminan]
 * @description : [用户Controller]
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final String MEANINGLESS_QUERY = "";
    private static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;
    private static final int MIN_PAGE_NUM = 1;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private AssessmentService assessmentService;

    // 接下来是用户状态控制
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public R handleUserRegister(@RequestBody User user){
        return userService.registerUser(user);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public R handleUserLogin(HttpServletRequest request, HttpServletResponse response,
                             @RequestBody LoginUser loginUser){
        User user = loginUser.getUser();
        String originalPassword = loginUser.getUser().getPassword();
        if(userService.login(user)){
            boolean judgeName = true;
            boolean judgePwd = true;
            boolean judgeRememberMe = true;
            Cookie[] cookies = request.getCookies();

            //如果cookies不为空
            if (cookies != null) {
                //遍历cookies，如果cookie的键为“name”，就把cookie的值设为输入的name
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        cookie.setValue(user.getUsername());
                        // 延迟过期时间
                        cookie.setMaxAge(60 * 60 * 24 * 7);
                        // 设置全局可用
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        judgeName = false;
                    }
                    if (cookie.getName().equals("password")) {
                        cookie.setValue(originalPassword);
                        // 延迟过期时间
                        cookie.setMaxAge(60 * 60 * 24 * 7);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        judgePwd = false;
                    }
                    if (cookie.getName().equals("rememberMe")) {
                        cookie.setValue(String.valueOf(loginUser.getRememberMe()));
                        // 延迟过期时间
                        cookie.setMaxAge(60 * 60 * 24 * 7);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        judgeRememberMe = false;
                    }
                }
            }

            if (judgeName) {
                Cookie cookieName = new Cookie("username", user.getUsername());
                // 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
                cookieName.setMaxAge(60*60*24*7);
                cookieName.setPath("/");
                response.addCookie(cookieName);

            }
            //如果不存在名为password的cookie
            if (judgePwd) {
                Cookie cookiePwd = new Cookie("password", originalPassword);
                // 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
                cookiePwd.setMaxAge(60*60*24*7);
                cookiePwd.setPath("/");
                response.addCookie(cookiePwd);
            }
            if(judgeRememberMe){
                Cookie cookieRememberMe = new Cookie("rememberMe", String.valueOf(loginUser.getRememberMe()));
                cookieRememberMe.setMaxAge(60*60*24*7);
                cookieRememberMe.setPath("/");
                response.addCookie(cookieRememberMe);
            }

            // 这样的话随着用户登录他的个人信息就存在session里面了，修改个人信息的时候可以直接拿出来用不需要发送请求
            user = userService.getUserByUsername(user.getUsername());
            request.getSession().setAttribute("id",user.getId());
            request.getSession().setAttribute("username",user.getUsername());
            request.getSession().setAttribute("password",originalPassword);
            request.getSession().setAttribute("mail",user.getMail());
//            System.out.println(request.getSession());
            return R.ok("登录成功");
        } else {
            return R.error("用户名或密码错误,登陆失败");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public R handleUserLogout(HttpServletRequest request){
        // 清除Session
        request.getSession().removeAttribute("id");
        request.getSession().removeAttribute("username"); // 强制过期
        request.getSession().removeAttribute("password");
        request.getSession().removeAttribute("mail");
        return R.ok("退出成功");
    }

    // 接下来是书籍、电影、音乐查看与留言
    @RequestMapping(value = "/books/{query}", method = RequestMethod.GET)
    public String booksInit(HttpServletRequest request,
                            @PathVariable String query) {
        QueryInfo queryInfo = new QueryInfo();
        if(query.equals("AAA")){
            queryInfo.setQuery(MEANINGLESS_QUERY);
        } else {
            queryInfo.setQuery(query);
        }
        queryInfo.setPagenum(MIN_PAGE_NUM);
        queryInfo.setPagesize(MAX_PAGE_SIZE);
        request.setAttribute("books", bookService.getAllBooks(queryInfo));
        return "/booksPage";
    }

    @RequestMapping(value = "/films/{query}", method = RequestMethod.GET)
    public String filmsInit(HttpServletRequest request,
                            @PathVariable String query) {
        QueryInfo queryInfo = new QueryInfo();
        if(query.equals("AAA")){
            queryInfo.setQuery(MEANINGLESS_QUERY);
        } else {
            queryInfo.setQuery(query);
        }
        queryInfo.setPagenum(MIN_PAGE_NUM);
        queryInfo.setPagesize(MAX_PAGE_SIZE);
        request.setAttribute("films", filmService.getAllFilms(queryInfo));
        return "/filmsPage";
    }

    @RequestMapping(value = "/musics/{query}", method = RequestMethod.GET)
    public String musicsInit(HttpServletRequest request,
                             @PathVariable String query) throws IOException {

        QueryInfo queryInfo = new QueryInfo();
        if(query.equals("AAA")){
            queryInfo.setQuery(MEANINGLESS_QUERY);
        } else {
            queryInfo.setQuery(query);
        }
        queryInfo.setPagenum(MIN_PAGE_NUM);
        queryInfo.setPagesize(MAX_PAGE_SIZE);
        request.setAttribute("musics", musicService.getAllMusics(queryInfo));
        return "/musicsPage";
    }

    // 资源的分类展示请由前端进行处理 页面初始化时已经返回了全部资源

    @RequestMapping(value = "/assessments", method = RequestMethod.POST)
    @ResponseBody
    public R handleAddAssessment(HttpServletRequest request,
                                 @RequestBody Assessment assessmentView) {
        assessmentView.setUsername((String) request.getSession().getAttribute("username"));
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        assessmentView.setPostdate(time);
        return assessmentService.addAssessment(assessmentView);
    }

    @RequestMapping(value = "/assessments/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public R handleDeleteAssessment(@PathVariable("id") int id) {
        return assessmentService.deleteAssessment(id);
    }

    @RequestMapping(value = "/assessments/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public R handleUpdateAssessment(HttpServletRequest request,
                                    @PathVariable("id") long id,
                                    @RequestBody Assessment assessmentView) {
        //checkSession(request, response);
        assessmentView.setUsername((String) request.getSession().getAttribute("username"));
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        assessmentView.setPostdate(time);
        return assessmentService.updateAssessment(id, assessmentView);
    }

    /**
     * 查看留言
     * @param request 请求
     * @return
     */
    @RequestMapping(value = "/getMyAssessments/{query}", method = RequestMethod.GET)
    public String handleGetMyAssessmentsByQuery(HttpServletRequest request,
                                                @PathVariable("query") String query) {
        //checkSession(request, response);
        QueryInfo queryInfo = new QueryInfo();
        if(query.equals("AAA")){
            queryInfo.setQuery(MEANINGLESS_QUERY);
        } else {
            queryInfo.setQuery(query);
        }
        queryInfo.setPagenum(MIN_PAGE_NUM);
        queryInfo.setPagesize(MAX_PAGE_SIZE);
        Map<String,Object> map = assessmentService.getAllAssessments(queryInfo);
        List<Assessment> assessments = new ArrayList<>();
        // 用户名筛选
        String username = (String) request.getSession().getAttribute("username");
        for(Assessment assessment : (List<Assessment>) map.get("result")){
            if(assessment.getUsername().equals(username)){
                assessments.add(assessment);
            }
        }
        request.setAttribute("assessments", assessments);
        return "/myAssessments";
    }

    @RequestMapping(value = "/assessments/{type}", method = RequestMethod.GET)
    @ResponseBody
    public R handleGetAssessments(@PathVariable("type") String type) {
        return assessmentService.getAssessmentsByType(type);
    }

    // 修改个人信息
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public R handleUserUpdate(HttpServletRequest request,
                              @RequestBody User user) {
        long id = (long) request.getSession().getAttribute("id");
        return userService.updateUser(id,user);
    }

    @RequestMapping(value = "/books/{query}/{pagenum}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    public R handleGetAllBooks(@PathVariable("query") String query,
                               @PathVariable("pagenum") int pagenum,
                               @PathVariable("pagesize") int pagesize) {
        if(query.equals("AAA")){
            query=MEANINGLESS_QUERY;
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = bookService.getAllBooks(queryInfo);
        return R.ok(map);
    }

    @RequestMapping(value = "/films/{query}/{pagenum}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    public R handleGetAllMovies(@PathVariable("query") String query,
                                @PathVariable("pagenum") int pagenum,
                                @PathVariable("pagesize") int pagesize) {
        if(query.equals("AAA")){
            query=MEANINGLESS_QUERY;
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = filmService.getAllFilms(queryInfo);
        return R.ok(map);
    }

    @RequestMapping(value = "/musics/{query}/{pagenum}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    public R handleGetAllMusics(@PathVariable("query") String query,
                                @PathVariable("pagenum") int pagenum,
                                @PathVariable("pagesize") int pagesize) {
        if(query.equals("AAA")){
            query=MEANINGLESS_QUERY;
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = musicService.getAllMusics(queryInfo);
        return R.ok(map);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class LoginUser{
        private User user;
        private String rememberMe;
    }
}
