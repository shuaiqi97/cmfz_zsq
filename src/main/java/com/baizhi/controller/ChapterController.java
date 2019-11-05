package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;
    @RequestMapping("findAllChapter")
    public Map<String,Object> findAllChapter(String albumId,Integer rows,Integer page){
        Map<String, Object> map = chapterService.findAllChapter(albumId, rows, page);
        return map;
    }
    @RequestMapping("edit")
    public Map<String,Object> editChapter(Chapter chapter,String oper){
        HashMap<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)){
                String insertChapter = chapterService.insertChapter(chapter);
                map.put("message",insertChapter);
                map.put("status",true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("updateChapter")
    public Map<String,Object> updateChapter(String id, String albumId, MultipartFile name, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            //文件上传
            File file = new File(request.getServletContext().getRealPath("/back/album/music"),name.getOriginalFilename());
            name.transferTo(file);
            //修改文件名称
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setName(name.getOriginalFilename());
            //修改文件大小
            BigDecimal size = new BigDecimal(name.getSize());
            BigDecimal mod = new BigDecimal(1024);
            BigDecimal realdSize = size.divide(mod).divide(mod).setScale(2,BigDecimal.ROUND_HALF_UP);
            chapter.setSize(realdSize+"MB");
            //修改文件时长
            Encoder encoder = new Encoder();
            long duration = encoder.getInfo(file).getDuration();
            chapter.setDuration(duration/1000/60+":"+duration/1000%60);
            chapterService.updateChaptetr(chapter);
            //修改专辑数量
            Album album = new Album();
            album.setId(albumId);
            Album albumOne = albumService.findAlbumOne(album);
            albumOne.setCount(albumOne.getCount()+1);
            albumService.updateAlbum(albumOne);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
