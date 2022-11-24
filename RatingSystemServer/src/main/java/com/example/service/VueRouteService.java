package com.example.service;

import com.example.domain.VueRoute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.R;

/**
* @author wangminan
* @description 针对表【VueRoute】的数据库操作Service
* @createDate 2022-11-22 23:16:15
*/
public interface VueRouteService extends IService<VueRoute> {

    R getMenus();

    R getEChartsParams();
}
