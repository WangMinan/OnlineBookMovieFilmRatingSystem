package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.exception.TokenException;
import com.example.pojo.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : [wangminan]
 * @description : [未登录或token失效时访问接口时自定义的返回结果]
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        R result = R.error(HttpStatus.FORBIDDEN.value(), "无token或token未认证,请登录");
        String responseJson = JSON.toJSONString(result);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(responseJson.getBytes());
        outputStream.flush();
        outputStream.close();
        // 纳入全局异常统一处理
        throw new TokenException("无token或token未认证,请登录");
    }
}
