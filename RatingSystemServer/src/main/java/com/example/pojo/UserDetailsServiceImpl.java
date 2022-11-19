package com.example.pojo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.domain.Admin;
import com.example.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author : [wangminan]
 * @description : [一句话描述该类的功能]
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {

        // 查询用户信息
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username);
        Admin admin = adminMapper.selectOne(queryWrapper);

        // 判断用户是否存在
        if(admin == null){
            // ExceptionTranslationFilter会捕获该异常并抛出
            throw new RuntimeException("用户名或密码错误");
        }
//        List<String> list = menuMapper.selectPermsByUserId(user.getId());

        //不需要权限了

        // 否则返回UserDetails实现类
        return new LoginAdmin(admin,null);
    }
}
