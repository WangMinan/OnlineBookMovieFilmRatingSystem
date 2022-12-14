package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Assessment;
import com.example.domain.User;
import com.example.pojo.MailContent;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.*;
import com.example.mapper.AssessmentMapper;
import com.example.util.PageGetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private SendMailService sendMailService;

    @Override
    public Map<String, Object> getAllAssessments(QueryInfo queryInfo) {
        boolean flag = false;
        String query = "";
        if(queryInfo.getQuery().equals("")){
            flag = true;
        } else if(queryInfo.getQuery().startsWith("indistinct")) {
            // 取出第10个之后的部分
            query = queryInfo.getQuery().substring(10);
        } else if(queryInfo.getQuery().startsWith("username")){
            query = queryInfo.getQuery().substring(8);
        }
        queryInfo.setQuery("");
        Map<String,Object> map = PageGetUtil.getPage(assessmentMapper, queryInfo);
        // 从map中获取result,根据result中的user的id查询user,将user放入result中
        // 根据result中的objectType与object的id查询object,将object放入result中
        Map<String,Object> resultMap = new HashMap<>();
        // 这玩意是个强转 但我也没法子
        List<Assessment> resultList = (List<Assessment>) map.get("result");
        for(Assessment assessmentView : resultList){
            getUserAndWorkOfAssessment(assessmentView);
        }
        if (!flag){
            // 模糊查询
            // 深拷贝resultList到另一个list
            List<Assessment> resultListCopy = new ArrayList<>(resultList);
            Iterator<Assessment> iterator = resultListCopy.iterator();
            // 对于List中元素的删除一定要使用迭代器
            // 直接使用list.remove()会出现空指针异常
            while (iterator.hasNext()){
                Assessment assessmentView = iterator.next();
                if(!assessmentView.getWork().getName().contains(query)
                        && !assessmentView.getUser().getUsername().contains(query)
                        && !assessmentView.getWork().getDescription().contains(query)
                        && !assessmentView.getWork().getPublishyear().contains(query)
                        && !assessmentView.getWork().getType().contains(query)
                        && !assessmentView.getAssessment().contains(query)
                        && !assessmentView.getPostdate().contains(query)
                ){
                    iterator.remove();
                }
            }
            resultList = resultListCopy;
        }
        resultMap.put("result", resultList);
        resultMap.put("total", map.get("total"));
        return resultMap;
    }

    @Override
    public R deleteAssessment(long id) {
        Assessment assessment = assessmentMapper.selectById(id);
        String username = assessment.getUsername();
        String content = assessment.getAssessment();
        User user = userService.getUserByUsername(username);
        String userMail = user.getMail();
        int result = assessmentMapper.deleteById(id);

        if(result == 1) {
            MailContent mailContent = new MailContent();
            mailContent.setSendTo(userMail);
            mailContent.setText("您的评论已被删除，评论内容为:" + content);
            sendMailService.sendSimpleMail(mailContent);
            return R.ok("删除成功,已通知用户");
        } else {
            return R.error("删除失败");
        }
    }

    @Override
    public R addAssessment(Assessment assessmentView) {
        return assessmentMapper.insert(assessmentView) > 0 ? R.ok("添加评价成功") : R.error("添加评价失败");
    }

    @Override
    public R getAssessmentById(long id) {
        Assessment assessmentView = assessmentMapper.selectById(id);
        getUserAndWorkOfAssessment(assessmentView);
        Map<String,Object> map = new HashMap<>();
        map.put("assessment", assessmentView);
        return R.ok(map);
    }

    @Override
    public int countByType(String type) {
        QueryWrapper<Assessment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("objectType", type);
        return assessmentMapper.selectCount(queryWrapper);
    }

    @Override
    public R getAssessmentsByType(String type) {
        QueryWrapper<Assessment> wrapper = new QueryWrapper<>();
        wrapper.eq("objectType", type);
        List<Assessment> assessments = assessmentMapper.selectList(wrapper);
        for(Assessment assessment : assessments){
            getUserAndWorkOfAssessment(assessment);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("assessments", assessments);
        return R.ok(map);
    }

    @Override
    public List<Assessment> getAssessmentsByUsername(String username) {
        QueryWrapper<Assessment> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        List<Assessment> assessments = assessmentMapper.selectList(wrapper);
        for(Assessment assessment : assessments){
            getUserAndWorkOfAssessment(assessment);
        }
        return assessments;
    }

    @Override
    public R updateAssessment(long id, Assessment assessmentView) {
        assessmentView.setId(id);
        return assessmentMapper.updateById(assessmentView) > 0 ? R.ok("修改评价成功") : R.error("修改评价失败");
    }

    private void getUserAndWorkOfAssessment(Assessment assessmentView) {
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
}




