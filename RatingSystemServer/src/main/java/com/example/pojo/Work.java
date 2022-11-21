package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : [wangminan]
 * @description : [书影音实体的父类]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    protected String name;
    protected String worktype;
    protected String type;
    protected String publishyear;
    protected String description;
    protected String picurl;

    @Override
    public String toString() {
        return "Work{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", publishyear='" + publishyear + '\'' +
                ", description='" + description + '\'' +
                ", picurl='" + picurl + '\'' +
                '}';
    }
}
