package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Admin;
import com.example.service.AdminService;
import com.example.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author wangminan
* @description 针对表【Admin】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




