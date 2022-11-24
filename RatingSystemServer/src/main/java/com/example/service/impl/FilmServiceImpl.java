package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Book;
import com.example.domain.Film;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
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

    @Override
    public R addFilm(Film film) {
        return filmMapper.insert(film) > 0 ? R.ok("添加电影成功") : R.error("添加电影失败");
    }

    @Override
    public R deleteFilm(long id) {
        return filmMapper.deleteById(id) > 0 ? R.ok("删除电影成功") : R.error("删除电影失败");
    }

    @Override
    public R updateFilm(long id, Film film) {
        film.setId(id);
        return filmMapper.updateById(film) > 0 ? R.ok("修改电影成功") : R.error("修改电影失败");
    }

    @Override
    public R getFilmById(long id) {
        Film film = filmMapper.selectById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("film", film);
        return R.ok(map);
    }
}




