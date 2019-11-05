package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("findAll")
    public Map<String,Object> findAllBanner(Integer page, Banner banner, Integer rows){
        Map<String, Object> allBanner = bannerService.findAllBanner(page, banner, rows);
        return allBanner;
    }
    @RequestMapping("edit")
    public Map<String,Object> editBanner(String oper,Banner banner,HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)){
                String addBanner = bannerService.addBanner(banner);
                map.put("status",true);
                map.put("message",addBanner);
            }
            if ("edit".equals(oper)){
                bannerService.editBanner(banner);
            }
            if ("del".equals(oper)){
                String id = banner.getId();
                bannerService.deleteBanner(id,request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile cover,String id,HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            cover.transferTo(new File(request.getServletContext().getRealPath("/back/banner/img"),cover.getOriginalFilename()));
            Banner banner = new Banner();
            banner.setId(id);
            banner.setCover(cover.getOriginalFilename());
            bannerService.editBanner(banner);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
