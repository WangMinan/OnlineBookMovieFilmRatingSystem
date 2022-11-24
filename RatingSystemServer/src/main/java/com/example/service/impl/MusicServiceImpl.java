package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Music;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.MusicService;
import com.example.mapper.MusicMapper;
import com.example.util.PageGetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author wangminan
* @description 针对表【Music】的数据库操作Service实现
* @createDate 2022-11-19 20:42:40
*/
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music>
    implements MusicService{

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public Map<String, Object> getAllMusics(QueryInfo queryInfo) {
        return PageGetUtil.getPage(musicMapper, queryInfo);
    }

    @Override
    public R addMusic(Music music) {
        return musicMapper.insert(music) > 0 ? R.ok("添加音乐成功") : R.error("添加音乐失败");
    }

    @Override
    public R deleteMusic(long id) {
        return musicMapper.deleteById(id) > 0 ? R.ok("删除音乐成功") : R.error("删除音乐失败");
    }

    @Override
    public R updateMusic(long id, Music music) {
        music.setId(id);
        return musicMapper.updateById(music) > 0 ? R.ok("修改音乐成功") : R.error("修改音乐失败");
    }

    @Override
    public R getMusicById(long id) {
        Music music = musicMapper.selectById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("music", music);
        return R.ok(map);
    }
}




