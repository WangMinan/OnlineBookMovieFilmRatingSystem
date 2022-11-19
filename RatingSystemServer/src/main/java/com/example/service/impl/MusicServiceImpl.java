package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Music;
import com.example.service.MusicService;
import com.example.mapper.MusicMapper;
import org.springframework.stereotype.Service;

/**
* @author wangminan
* @description 针对表【Music】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music>
    implements MusicService{

}




