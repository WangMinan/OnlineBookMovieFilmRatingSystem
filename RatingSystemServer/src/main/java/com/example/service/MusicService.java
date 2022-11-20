package com.example.service;

import com.example.domain.Music;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author wangminan
* @description 针对表【Music】的数据库操作Service
* @createDate 2022-11-19 20:42:40
*/
@Service
public interface MusicService extends IService<Music> {

    Map<String, Object> getAllMusics(QueryInfo queryInfo);

    R addMusic(Music music);

    R deleteMusic(long id);

    R updateMusic(long id, Music music);
}
