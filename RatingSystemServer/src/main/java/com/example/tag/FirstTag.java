package com.example.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FirstTag extends SimpleTagSupport {

    public void doTag() throws JspException {
        try {
            JspWriter out = getJspContext().getOut();
            String outPrint = "";
            outPrint += "<div class=\"row\">";
            outPrint += "<div class=\"text-center\">";
            outPrint += "<h3>用户登录</h3>";
            outPrint += "</div>";
            outPrint += "</div>";
            out.print(outPrint);
        } catch (java.io.IOException e) {
            throw new JspTagException(e.getMessage());
        }
    }
}
