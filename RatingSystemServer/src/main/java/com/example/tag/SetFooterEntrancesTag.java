package com.example.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 用来设置footer快速入口相关属性的标签
 */
public class SetFooterEntrancesTag extends SimpleTagSupport {

    public void doTag() throws JspException {
        try {
            String[] footerEntranceList = new String[3];
            String[] hrefs = new String[3];
            footerEntranceList[0] = "书籍";
            hrefs[0] = "/user/books";
            footerEntranceList[1] = "电影";
            hrefs[1] = "/user/films";
            footerEntranceList[2] = "音乐";
            hrefs[2] = "/user/musics";
            //设置属性
            getJspContext().setAttribute("FooterEntranceList", footerEntranceList);
            getJspContext().setAttribute("Hrefs", hrefs);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
    }
}
