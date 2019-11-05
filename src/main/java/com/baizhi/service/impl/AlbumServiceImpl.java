package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("albumService")
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private StarDao starDao;
    @Autowired
    private AlbumDao albumDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllAlbum(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Album> albumList = albumDao.selectByRowBounds(album, rowBounds);
        for (Album album1 : albumList) {
            Star star = starDao.selectByPrimaryKey(album1.getStarId());
            album1.setStar(star);
        }
        int count = albumDao.selectCount(album);
        map.put("page",page);
        map.put("rows",albumList);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public List<Star> findAllStar() {
        List<Star> stars = starDao.selectAll();
        return stars;
    }

    @Override
    public String insertAlbum(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCount(0);
        album.setScore(10.0);
        int i = albumDao.insertSelective(album);
        if (i==0){
            throw new RuntimeException("添加失败");
        } else{
            return album.getId();
        }
    }

    @Override
    public void updateAlbum(Album album) {
        if ("".equals(album.getCover())){
            album.setCover(null);
        }
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    public Album findAlbumOne(Album album) {
        Album primaryKey = albumDao.selectByPrimaryKey(album);
        return primaryKey;
    }
}
