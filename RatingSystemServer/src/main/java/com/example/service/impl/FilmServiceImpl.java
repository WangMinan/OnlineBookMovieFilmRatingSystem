package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Book;
import com.example.domain.Film;
import com.example.pojo.QueryInfo;
import com.example.service.FilmService;
import com.example.mapper.FilmMapper;
import com.example.util.PageGetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @author wangminan
* @description 针对表【Film】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film>
    implements FilmService{

    @Autowired
    private FilmMapper filmMapper;

    @Override
    public Map<String, Object> getAllFilms(QueryInfo queryInfo) {
        return PageGetUtil.getPage(filmMapper, queryInfo);
    }
}




