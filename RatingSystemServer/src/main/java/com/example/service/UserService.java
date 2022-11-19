package com.example.service;

import com.example.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author wangminan
* @description 针对表【User】的数据库操作Service
* @createDate 2022-11-19 20:42:40
*/
@Service
public interface UserService extends IService<User> {

}
