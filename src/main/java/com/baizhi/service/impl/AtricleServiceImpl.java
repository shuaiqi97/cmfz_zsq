package com.baizhi.service.impl;

import com.baizhi.dao.AtricleDao;
import com.baizhi.entity.Atricle;
import com.baizhi.service.AtricleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("atricleService")
@Transactional
public class AtricleServiceImpl implements AtricleService {
    @Autowired
    private AtricleDao atricleDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> finaAllAtricle(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Atricle atricle = new Atricle();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        int count = atricleDao.selectCount(atricle);
        List<Atricle> atricleList = atricleDao.selectByRowBounds(atricle, rowBounds);
        map.put("page",page);
        map.put("rows",atricleList);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public void addAtricle(Atricle atricle) {
        atricle.setId(UUID.randomUUID().toString());
        atricle.setCreateDate(new Date());
        int insert = atricleDao.insert(atricle);
        if (insert==0){
            throw new RuntimeException("添加错误");
        }

    }

    @Override
    public void editAtricle(Atricle atricle) {
        if ("".equals(atricle.getContent())){
            atricle.setContent(null);
            System.out.println("*******************************"+atricle);
        }
        System.out.println("---------------------"+atricle);
        int i = atricleDao.updateByPrimaryKeySelective(atricle);
    }

    @Override
    public void deleteAtricle(Atricle atricle) {
        atricleDao.delete(atricle);
    }
}
