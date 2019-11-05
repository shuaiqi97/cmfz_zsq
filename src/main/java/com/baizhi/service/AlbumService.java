package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Star;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map<String,Object> findAllAlbum(Integer page,Integer rows);
    List<Star> findAllStar();
    String insertAlbum(Album album);
    void updateAlbum(Album album);
    Album findAlbumOne(Album album);
}
