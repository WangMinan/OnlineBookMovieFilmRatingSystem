package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.example.mapper")
public class RatingSystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatingSystemServerApplication.class, args);
    }


}
