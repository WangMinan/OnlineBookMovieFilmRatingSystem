package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Assessment;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.*;
import com.example.mapper.AssessmentMapper;
import com.example.util.PageGetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MusicService musicService;

    @Override
    public Map<String, Object> getAllAssessments(QueryInfo queryInfo) {
        Map<String,Object> map = PageGetUtil.getPage(assessmentMapper, queryInfo);
        // 从map中获取result,根据result中的user的id查询user,将user放入result中
        // 根据result中的objectType与object的id查询object,将object放入result中
        Map<String,Object> resultMap = new HashMap<>();
        // 这玩意是个强转 但我也没法子
        List<Assessment> resultList = (List<Assessment>) map.get("result");
        for(Assessment assessmentView : resultList){
            assessmentView.setUser(userService.getUserByUsername(assessmentView.getUsername()));
            switch (assessmentView.getObjecttype()) {
                case "book":
                    assessmentView.setWork(bookService.getById(assessmentView.getObjectid()));
                    break;
                case "film":
                    assessmentView.setWork(filmService.getById(assessmentView.getObjectid()));
                    break;
                case "music":
                    assessmentView.setWork(musicService.getById(assessmentView.getObjectid()));
                    break;
                default:
                    break;
            }
        }
        resultMap.put("result", resultList);
        resultMap.put("total", map.get("total"));
        return resultMap;
    }

    @Override
    public R deleteAssessment(long id) {
        return assessmentMapper.deleteById(id) > 0 ? R.ok("删除评价成功") : R.error("删除评价失败");
    }

    @Override
    public R addAssessment(Assessment assessmentView) {
        return assessmentMapper.insert(assessmentView) > 0 ? R.ok("添加评价成功") : R.error("添加评价失败");
    }
}




