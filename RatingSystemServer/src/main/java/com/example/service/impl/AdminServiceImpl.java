package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Admin;
import com.example.pojo.LoginAdmin;
import com.example.pojo.R;
import com.example.service.AdminService;
import com.example.mapper.AdminMapper;
import com.example.util.JwtUtil;
import com.example.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @author wangminan
* @description 针对表【Admin】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public R login(Admin admin) {
// 获取AuthenticationManager的方法 authenticate(Authentication authentication) 进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword());
        // authenticate方法会调用UserDetailsService的loadUserByUsername方法
        /**
         * @see com.example.service.impl.UserDetailsServiceImpl#loadUserByUsername(String)
         */
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 认证未通过 抛出异常
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登录失败");
        }
        // 如果认证通过 使用Userid生成jwt
        LoginAdmin loginAdmin = (LoginAdmin) authentication.getPrincipal();
        String userid = loginAdmin.getAdmin().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        // 把完整的用户信息存入redis userid为key
        Map<String,Object> map = new HashMap<>();
        map.put("token",jwt);

        redisCache.setCacheObject("adminLogin:" + userid, loginAdmin);

        return R.ok(map);
    }

    @Override
    public R logout() {
        // 获取SecurityContextHolder中的用户id
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
//                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) authentication;
        LoginAdmin loginAdmin = (LoginAdmin) usernamePasswordAuthenticationToken.getPrincipal();
        String userid = loginAdmin.getAdmin().getId().toString();
        // 删除redis中的token
        redisCache.deleteObject("adminLogin:" + userid);
//        System.out.println("退出成功");
        return R.ok("退出成功");
    }
}




