package com.example.service;

import com.example.domain.Film;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.QueryInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author wangminan
* @description 针对表【Film】的数据库操作Service
* @createDate 2022-11-19 20:42:40
*/
@Service
public interface FilmService extends IService<Film> {

    Map<String, Object> getAllFilms(QueryInfo queryInfo);
}
