package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Map<String,Object> getAllUsers(QueryInfo queryInfo) {
        // 这里没有用既定的Util来处理 wise choice 这玩意和书影音不一样的
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

    @Override
    public R deleteUser(long id) {
        return userMapper.deleteById(id) > 0 ? R.ok("删除用户成功") : R.error("删除用户失败");
    }

    @Override
    public R updateUser(long id, User user) {
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.updateById(user) > 0 ? R.ok("更新用户成功") : R.error("更新用户失败,可能与现有用户名重复或用户不存在");
    }

    @Override
    public R registerUser(User user) {
        // 加密后插入数据库
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userMapper.insert(user) > 0 ? R.ok("注册用户成功") : R.error("注册用户失败,可能与现有用户名重复");
    }

    @Override
    public boolean login(User user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", user.getUsername());
        User user1 = userMapper.selectOne(wrapper);
        if(user1 == null){
            return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(user.getPassword(), user1.getPassword());
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public R getUserById(long id) {
        Map<String,Object> map = new HashMap<>();
        User user = userMapper.selectById(id);
        map.put("user", user);
        return R.ok(map);
    }
}




