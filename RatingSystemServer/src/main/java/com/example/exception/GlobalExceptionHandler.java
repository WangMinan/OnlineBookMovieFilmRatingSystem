package com.example.exception;

import com.example.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : [wangminan]
 * @description : [全局异常处理]
 */
@Slf4j // 日志
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TokenException.class)
    public R handler(TokenException e){
        log.error("token异常:" + e.getMessage());
        return R.error(e.getMessage()); // 向前端返回500错误并传输异常message
    }

    // 只能收到Controller中抛出的异常 其他层中的异常需要自定义
    @ExceptionHandler(value = RuntimeException.class)
    public R handler(RuntimeException e){
        log.error("运行时异常:" + e.getMessage());
        return R.error(e.getMessage()); // 向前端返回500错误并传输异常message
    }
}
