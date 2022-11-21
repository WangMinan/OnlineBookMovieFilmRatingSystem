package com.example.mapper;

import com.example.domain.Assessment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wangminan
* @description 针对表【Assessment】的数据库操作Mapper
* @createDate 2022-11-19 20:42:40
* @Entity com.example.domain.Assessment
*/
@Mapper
public interface AssessmentMapper extends BaseMapper<Assessment> {

}




