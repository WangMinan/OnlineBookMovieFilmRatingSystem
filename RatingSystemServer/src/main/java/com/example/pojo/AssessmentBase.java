package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : [wangminan]
 * @description : [Assessment类的父类]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentBase {

    @TableField(exist = false)
    protected User user;
    @TableField(exist = false)
    protected Work work;
    protected String assessment;
}
