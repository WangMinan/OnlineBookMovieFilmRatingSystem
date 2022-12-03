package com.example.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.Book;
import com.example.pojo.QueryInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : [wangminan]
 * @description : [分页查询工具类]
 */
public class PageGetUtil {

    /**
     * 分页获取基本信息的单一实例方法
     * @param mapper 传入的mapper
     * @param queryInfo 传入的查询信息
     * @return 返回的分页信息
     */
    public static Map<String,Object> getPage(BaseMapper mapper, QueryInfo queryInfo){
        Page<Book> page = new Page<>(queryInfo.getPagenum(), queryInfo.getPagesize());
        Page list = new Page();
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
        } else if(queryInfo.getQuery().startsWith("objectType")) {
            QueryWrapper wrapper = new QueryWrapper();
            queryInfo.setQuery(queryInfo.getQuery().substring(10));
            wrapper.like("objectType", queryInfo.getQuery());
            list = (Page) mapper.selectPage(page, wrapper);
        } else if(queryInfo.getQuery().startsWith("indistinct")){
            // 模糊搜索
            QueryWrapper wrapper = new QueryWrapper();
            queryInfo.setQuery(queryInfo.getQuery().substring(10));
            wrapper.like("objectType", queryInfo.getQuery());
            wrapper.or();
            wrapper.like("assessment", queryInfo.getQuery());
            wrapper.or();
            wrapper.like("postDate", queryInfo.getQuery());
            list = (Page) mapper.selectPage(page, wrapper);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total", list.getTotal());
        map.put("result", list.getRecords());
        return map;
    }
}
