package com.example.service;

import com.example.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author wangminan
* @description 针对表【User】的数据库操作Service
* @createDate 2022-11-19 20:42:40
*/
@Service
public interface UserService extends IService<User> {

    Map<String,Object> getAllUsers(QueryInfo queryInfo);

    R addUser(User user);

    R deleteUser(long id);

    R updateUser(long id, User user);

    R registerUser(User user);

    boolean login(User user);

    User getUserByUsername(String username);

    R getUserById(long id);
}
