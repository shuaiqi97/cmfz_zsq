package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("findAllAlbum")
    @ResponseBody
    public Map<String,Object> findAllAlbum(Integer page,Integer rows){
        Map<String, Object> map = albumService.findAllAlbum(page, rows);
        return map;
    }
    @RequestMapping("findAllStar")
    public void findAllStar(HttpServletResponse response) throws IOException {
        List<Star> allStar = albumService.findAllStar();
        StringBuilder builder = new StringBuilder();
        builder.append("<select>");
        allStar.forEach(star ->{
            builder.append("<option value=").append(star.getId()).append(">").append(star.getRealname()).append("</option>");
        });
        builder.append("</select>");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(builder.toString());
    }
    @ResponseBody
    @RequestMapping("editAlbum")
    public Map<String,Object> editAlbum(Album album,String oper){
        HashMap<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)){
                String insertAlbum = albumService.insertAlbum(album);
                map.put("status",true);
                map.put("message",insertAlbum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("updateAlbum")
    public Map<String,Object> updateAlbum(HttpServletRequest request, MultipartFile cover,String id){
        Album album = new Album();
        HashMap<String, Object> map = new HashMap<>();
        try {
            cover.transferTo(new File(request.getServletContext().getRealPath("/back/album/img"),cover.getOriginalFilename()));
            album.setId(id);
            album.setCover(cover.getOriginalFilename());
            albumService.updateAlbum(album);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }

}
