package com.baizhi.service.impl;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service("starService")
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllStar(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Star star = new Star();
        RowBounds rowBounds = new RowBounds((page-1)*rows, rows);
        List<Star> starList = starDao.selectByRowBounds(star, rowBounds);
        int count = starDao.selectCount(star);
        map.put("page", page);
        map.put("rows", starList);
        map.put("records", count);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        return map;
    }

    @Override
    public String insertStar(Star star) {
        star.setId(UUID.randomUUID().toString());
        int i = starDao.insert(star);
        if (i == 1) {
            return star.getId();
        } else {
            throw new RuntimeException("信息添加失败");
        }
    }
    @Override
    public String upteStar(Star star) {
        if ("".equals(star.getPhoto())) {
            star.setPhoto(null);
        }
        System.out.println(star);
        int i = starDao.updateByPrimaryKeySelective(star);
        if(i==0){
            throw new RuntimeException("修改错误");
        }else{
            return star.getId();
        }

    }
}

