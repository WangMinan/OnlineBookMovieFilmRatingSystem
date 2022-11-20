package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Assessment;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.AssessmentService;
import com.example.mapper.AssessmentMapper;
import com.example.util.PageGetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author wangminan
* @description 针对表【Assessment】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class AssessmentServiceImpl extends ServiceImpl<AssessmentMapper, Assessment>
    implements AssessmentService{

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Override
    public Map<String, Object> getAllAssessments(QueryInfo queryInfo) {
        return PageGetUtil.getPage(assessmentMapper, queryInfo);
    }

    @Override
    public R deleteAssessment(long id) {
        return assessmentMapper.deleteById(id) > 0 ? R.ok("删除评价成功") : R.error("删除评价失败");
    }
}




