package com.example.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class CompressionResponseWrapper extends HttpServletResponseWrapper {

    private CharArrayWriter charWriter;
    public CompressionResponseWrapper(HttpServletResponse response) {
        super(response);
        charWriter = new CharArrayWriter();
    }

    public PrintWriter getWriter() {
        return new PrintWriter(charWriter);
    }

    public   char [] toCharArray() {
        return   charWriter.toCharArray();
    }

}
