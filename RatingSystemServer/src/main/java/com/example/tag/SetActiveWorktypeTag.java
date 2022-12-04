package com.example.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SetActiveWorktypeTag extends SimpleTagSupport {

    public void doTag() throws JspException {
        try {
            JspWriter out = getJspContext().getOut();
            String worktype = (String) getJspContext().getAttribute("worktype", PageContext.REQUEST_SCOPE);
            String outPrint = "";

            if(worktype.equals("films")) {
                outPrint += "<li><a href=\"/user/books/AAA\">书籍</a></li>\n" +
                        "          <li class=\"active\"><a href=\"/user/films/AAA\">电影</a></li>\n" +
                        "          <li><a href=\"/user/musics/AAA\">音乐</a></li>";
            } else if(worktype.equals("books")) {
                outPrint += "<li class=\"active\"><a href=\"/user/books/AAA\">书籍</a></li>\n" +
                        "          <li><a href=\"/user/films/AAA\">电影</a></li>\n" +
                        "          <li><a href=\"/user/musics/AAA\">音乐</a></li>";
            } else if(worktype.equals("musics")) {
                outPrint += "<li><a href=\"/user/books/AAA\">书籍</a></li>\n" +
                        "          <li><a href=\"/user/films/AAA\">电影</a></li>\n" +
                        "          <li class=\"active\"><a href=\"/user/musics/AAA\">音乐</a></li>";
            }
            out.print(outPrint);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
    }
}
