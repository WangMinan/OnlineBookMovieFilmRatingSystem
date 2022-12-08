package com.example.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

/**
 * 响应过滤器
 */
public class GZIPResponseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String type = req.getHeader("X-Requested-With")==null?"":req.getHeader("X-Requested-With");

        if (!isGzipSupported(req)) { // Invoke resource normally.
            filterChain.doFilter(req, res);
            return ;
        }

        // 将响应头信息中的内容编码设置为gzip
        res.setHeader( "Content-Encoding" , "gzip" );

        // 调用资源，使用 CompressionResponseWrapper 包装输出
        CompressionResponseWrapper responseWrapper = new CompressionResponseWrapper(res);
        filterChain.doFilter(req, responseWrapper);
        // 取得存放输出数据的 char 型数组
        char[] responseChars = responseWrapper.toCharArray();

        // 将响应数据压缩后存入一个 byte 型的数组，然后输出到
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        GZIPOutputStream zipOut = new GZIPOutputStream(byteStream);
        OutputStreamWriter tempOut = new OutputStreamWriter(zipOut);
        // 将原来的响应数据压缩后写入二字节输出流
        tempOut.write(responseChars);
        // 关闭输出流
        tempOut.close();

        // 更新响应头信息中 Content-Length 的值。
        res.setContentLength(byteStream.size());
        // 将压缩后的数据发送至客户端
        OutputStream realOut = res.getOutputStream();
        byteStream.writeTo(realOut);

        System.out.println("完成GZIP压缩");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * 检测浏览器是否支持Gzip压缩
     *
     * @param req HTTP请求对象
     * @return 如果浏览器支持Gzip压缩，则返回true，反之，则返回false
     */
    private boolean isGzipSupported(HttpServletRequest req) {
        String browserEncodings = req.getHeader( "Accept-Encoding" );
        return ((browserEncodings != null) && (browserEncodings.contains("gzip")));
    }
}
