package com.example.service;

import com.example.domain.AssessmentView;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author wangminan
* @description 针对表【AssessmentView】的数据库操作Service
* @createDate 2022-11-19 20:42:40
*/
@Service
public interface AssessmentService extends IService<AssessmentView> {

    Map<String, Object> getAllAssessments(QueryInfo queryInfo);

    R deleteAssessment(long id);

    R addAssessment(AssessmentView assessmentView);
}
