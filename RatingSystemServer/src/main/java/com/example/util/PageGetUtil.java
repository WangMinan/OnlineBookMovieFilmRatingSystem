package com.example.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Book;
import com.example.pojo.QueryInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : [wangminan]
 * @description : [一句话描述该类的功能]
 */
public class PageGetUtil {

    public static Map<String,Object> getPage(BaseMapper mapper, QueryInfo queryInfo){
        Page<Book> page = new Page<>(queryInfo.getPagenum(), queryInfo.getPagesize());
        Page list;
        if(Objects.equals(queryInfo.getQuery(), "") || queryInfo.getQuery() == null){
            list = (Page) mapper.selectPage(page, null);
        } else if(queryInfo.getQuery().startsWith("name")){
            QueryWrapper wrapper = new QueryWrapper();
            queryInfo.setQuery(queryInfo.getQuery().substring(4));
            wrapper.like("name", queryInfo.getQuery());
            list = (Page) mapper.selectPage(page, wrapper);
        } else if(queryInfo.getQuery().startsWith("type")){
            QueryWrapper wrapper = new QueryWrapper();
            queryInfo.setQuery(queryInfo.getQuery().substring(4));
            wrapper.like("type", queryInfo.getQuery());
            list = (Page) mapper.selectPage(page, wrapper);
        } else if(queryInfo.getQuery().startsWith("year")){
            QueryWrapper wrapper = new QueryWrapper();
            queryInfo.setQuery(queryInfo.getQuery().substring(4));
            wrapper.like("publishYear", queryInfo.getQuery());
            list = (Page) mapper.selectPage(page, wrapper);
        } else {
            list = (Page) mapper.selectPage(page, null);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total", list.getTotal());
        map.put("result", list.getRecords());
        return map;
    }
}
