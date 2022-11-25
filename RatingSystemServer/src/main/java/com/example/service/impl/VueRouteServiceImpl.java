package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.VueRoute;
import com.example.pojo.R;
import com.example.service.*;
import com.example.mapper.VueRouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author wangminan
* @description 针对表【VueRoute】的数据库操作Service实现
* @createDate 2022-11-22 23:16:15
*/
@Service
public class VueRouteServiceImpl extends ServiceImpl<VueRouteMapper, VueRoute>
        implements VueRouteService{

    @Autowired
    private VueRouteMapper vueRouteMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssessmentService assessmentService;

    @Override
    public R getMenus() {
        List<VueRoute> tmpRoutes = vueRouteMapper.selectList(null);
        // 根据father是否为空来判断是否为一级菜单
        List<VueRoute> routes = new ArrayList<>();
        for(VueRoute route : tmpRoutes){
            route.setChildren(new ArrayList<>());
            if(route.getFather() == null){
                routes.add(route);
            }
        }
        // 递归添加子菜单
        for(VueRoute route : tmpRoutes){
            if(!(route.getFather() == null)){
                // 其他节点根据father的id来找到父节点 并将自己加入到父节点的children中
                for(VueRoute father : routes){
                    if(father.getId().equals(route.getFather())){
                        father.getChildren().add(route);
                        break;
                    }
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        // 按照id大小对routes进行排序
        routes.sort(Comparator.comparing(VueRoute::getId));
        map.put("routes",routes);
        return R.ok(map);
    }

    @Override
    public R getEChartsParams() {
        // 获取书籍总数
        int bookCount = bookService.count();
        // 获取电影总数
        int filmCount = filmService.count();
        // 获取音乐总数
        int musicCount = musicService.count();
        // 获取用户总数
        int userCount = userService.count();

        // 获取留言中按照书籍、电影、音乐分类的数量 输出在3个int里面
        int bookAssessmentCount = assessmentService.countByType("book");
        int filmAssessmentCount = assessmentService.countByType("film");
        int musicAssessmentCount = assessmentService.countByType("music");

        Map<String,Object> map = new HashMap<>();
        map.put("totalBook",bookCount);
        map.put("totalFilm",filmCount);
        map.put("totalMusic",musicCount);
        map.put("totalUser",userCount);
        map.put("totalBookAssessment",bookAssessmentCount);
        map.put("totalFilmAssessment",filmAssessmentCount);
        map.put("totalMusicAssessment",musicAssessmentCount);
        return R.ok(map);
    }
}




