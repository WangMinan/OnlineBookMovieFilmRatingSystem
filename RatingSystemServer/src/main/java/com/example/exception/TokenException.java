package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author : [wangminan]
 * @description : [token异常]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenException extends RuntimeException{

    private Integer code;//异常编号

    public TokenException(String message) {
        super(message);
        this.code= HttpStatus.FORBIDDEN.value();
    }

    public TokenException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public TokenException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
