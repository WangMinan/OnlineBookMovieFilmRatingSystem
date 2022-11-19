package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Assessment;
import com.example.service.AssessmentService;
import com.example.mapper.AssessmentMapper;
import org.springframework.stereotype.Service;

/**
* @author wangminan
* @description 针对表【Assessment】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class AssessmentServiceImpl extends ServiceImpl<AssessmentMapper, Assessment>
    implements AssessmentService{

}




