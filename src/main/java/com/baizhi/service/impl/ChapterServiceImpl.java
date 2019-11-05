package com.baizhi.service.impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("chapterService")
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllChapter(String albumId, Integer rows, Integer page) {
        Map<String, Object> map = new HashMap<>();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        int count = chapterDao.selectCount(chapter);
        List<Chapter> chapterList = chapterDao.selectByRowBounds(chapter, rowBounds);
        map.put("page",page);
        map.put("rows",chapterList);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public String insertChapter(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreateDate(new Date());
        int i = chapterDao.insert(chapter);
        if (i==0){
            throw new RuntimeException("添加失败");
        }else {
            return chapter.getId();
        }

    }

    @Override
    public void updateChaptetr(Chapter chapter) {
        int i = chapterDao.updateByPrimaryKeySelective(chapter);
        if (i==0){
            throw new RuntimeException("修改失败");
        }
    }
}
