package com.baizhi.controller;

import com.baizhi.entity.Atricle;
import com.baizhi.service.AtricleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("atricle")
public class AtricleController {
    @Autowired
    private AtricleService atricleService;
    @RequestMapping("findAllAtricle")
    public Map<String,Object> findAllAtricle(Integer page,Integer rows){
        Map<String, Object> map = atricleService.finaAllAtricle(page, rows);
        return map;
    }
    @RequestMapping("uploadAtricle")
    public Map<String,Object> uploadAtricle(MultipartFile articleImg, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        try {
            File file = new File(request.getServletContext().getRealPath("/back/atricle/img"),articleImg.getOriginalFilename());
            articleImg.transferTo(file);
            map.put("error",0);
            map.put("url","http://localhost:9090/cmfz/back/atricle/img/"+articleImg.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error",1);
        }
        return map;
    }
    @RequestMapping("browse")
    private Map<String,Object> browse(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        File file = new File(request.getServletContext().getRealPath("/back/atricle/img"));
        File[] files = file.listFiles();
        List<Object> list = new ArrayList<>();
        for (File img : files) {
            Map<String, Object> imgObject = new HashMap<>();
            imgObject.put("is_dir",false);
            imgObject.put("has_file",false);
            imgObject.put("filesize",img.length());
            imgObject.put("is_photo",true);
            imgObject.put("filetype", FilenameUtils.getExtension(img.getName()));
            imgObject.put("filename",img.getName());
            imgObject.put("datetime","2018-06-06 00:36:39");
            list.add(imgObject);
        }
        map.put("file_list",list);
        map.put("total_count",list.size());
        map.put("current_url","http://localhost:9090/cmfz/back/atricle/img/");
        return map;
    }
    @RequestMapping("addAtricle")
    public Map<String,Object> addAtricle(Atricle atricle){
        HashMap<String, Object> map = new HashMap<>();
        try {
            atricleService.addAtricle(atricle);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
    @RequestMapping("editAtricle")
    public Map<String,Object> editAtricle(Atricle atricle){
        Map<String, Object> map = new HashMap<>();
        try {
            atricleService.editAtricle(atricle);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
    @RequestMapping("deleteAtrcle")
    public void deleteAtrcle (String id){
        Atricle atricle = new Atricle();
        atricle.setId(id);
        atricleService.deleteAtricle(atricle);
    }

}
