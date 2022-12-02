package com.example.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 该类用于设置表单标题
 */
public class SetFormTitleTag extends SimpleTagSupport {

    public void doTag() throws JspException {
        try {
            JspWriter out = getJspContext().getOut();
            String outPrint = "";
            outPrint += "<div class=\"row\">";
            outPrint += "<div class=\"text-center\">";
            outPrint += "<h3>";
            String title = (String) getJspContext().getAttribute("title", PageContext.SESSION_SCOPE);
            outPrint += getJspContext().getAttribute("title", PageContext.SESSION_SCOPE);
            outPrint += "</h3>";
            outPrint += "</div>";
            outPrint += "</div>";
            out.print(outPrint);
        } catch (java.io.IOException e) {
            throw new JspTagException(e.getMessage());
        }
    }
}
