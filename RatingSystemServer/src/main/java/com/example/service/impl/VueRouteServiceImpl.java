package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.VueRoute;
import com.example.pojo.R;
import com.example.service.VueRouteService;
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

    @Override
    public R getMenus() {
        List<VueRoute> tmpRoutes = vueRouteMapper.selectList(null);
        // 根据father是否为空来判断是否为一级菜单
        List<VueRoute> routes = new ArrayList<>();
        for(VueRoute route : tmpRoutes){
            route.setChildren(new ArrayList<>());
            if(route.getFather() == null){
                routes.add(route);
            } else {
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
}




