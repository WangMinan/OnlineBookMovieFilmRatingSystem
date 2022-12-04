package com.example.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 设置搜索框清空筛选后的跳转页面
 */
public class SetSearchNavTypeTag extends SimpleTagSupport {
    public void doTag() throws JspException {
        try {
            JspWriter out = getJspContext().getOut();
            String outPrint = "";
            outPrint += "<a href=\"/user/";
            outPrint += getJspContext().getAttribute("worktype", PageContext.REQUEST_SCOPE);
            outPrint += "/AAA\">清空筛选</a>";
            out.print(outPrint);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
    }
}
