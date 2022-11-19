package com.example.service;

import com.example.domain.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.R;
import org.springframework.stereotype.Service;

/**
* @author wangminan
* @description 针对表【Admin】的数据库操作Service
* @createDate 2022-11-19 20:42:40
*/
@Service
public interface AdminService extends IService<Admin> {

    R login(Admin admin);

    R logout();
}
