package com.baizhi.controller;

import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.AsyncBoxView;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("star")
@RestController
public class StarController {
    @Autowired
    private StarService starService;
    @RequestMapping("findAll")
    private Map<String,Object> findAll(Integer page,Integer rows){
        Map<String, Object> map = starService.findAllStar(page, rows);
        return map;
    }
    @RequestMapping("edit")
    private Map<String,Object> editStar(Star star, String oper, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)){
                String insertStar = starService.insertStar(star);
                map.put("message",insertStar);
                map.put("status",true);
            }
            if ("edit".equals(oper)){
                String upteStar = starService.upteStar(star);
                map.put("message",upteStar);
                map.put("status",true);
            }
            if ("del".equals(oper)){

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message",e.getMessage());
        }
        return  map;
    }
    @RequestMapping("updateStarPhoto")
    private Map<String,Object> updateStarPhoto(String id,HttpServletRequest request,MultipartFile photo){
        Map<String, Object> map = new HashMap<>();
        try {
            photo.transferTo(new File(request.getServletContext().getRealPath("/back/star/img"),photo.getOriginalFilename()));
            Star star = new Star();
            star.setId(id);
            star.setPhoto(photo.getOriginalFilename());
            starService.upteStar(star);
            map.put("status",true);

        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
