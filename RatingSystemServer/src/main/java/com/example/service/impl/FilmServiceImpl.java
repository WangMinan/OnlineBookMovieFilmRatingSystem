package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Film;
import com.example.service.FilmService;
import com.example.mapper.FilmMapper;
import org.springframework.stereotype.Service;

/**
* @author wangminan
* @description 针对表【Film】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film>
    implements FilmService{

}




