package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    Map<String,Object> findAllChapter(String albumId,Integer rows,Integer page);
    String insertChapter(Chapter chapter);
    void updateChaptetr(Chapter chapter);
}
