package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : [wangminan]
 * @description : 分页查询的基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryInfo {
    private String query;
    private int pagenum;
    private int pagesize;

    public QueryInfo(int pagenum, int pagesize) {
        this.pagenum = pagenum;
        this.pagesize = pagesize;
    }
}
