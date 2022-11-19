package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Admin;
import com.example.domain.User;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.UserService;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @author wangminan
* @description 针对表【User】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String,Object> getAllUsers(QueryInfo queryInfo) {
        Page<User> page = new Page<>(queryInfo.getPagenum(), queryInfo.getPagesize());
        Page<User> userList;
        if(Objects.equals(queryInfo.getQuery(), "") || queryInfo.getQuery() == null){
            userList = userMapper.selectPage(page, null);
        } else {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.like("username", queryInfo.getQuery());
            userList = userMapper.selectPage(page, wrapper);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total", userList.getTotal());
        map.put("result", userList.getRecords());
        return map;
    }

    @Override
    public R addUser(User user) {
        // 加密后插入数据库
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        return userMapper.insert(user) > 0 ? R.ok("添加用户成功") : R.error("添加用户失败");
    }
}




