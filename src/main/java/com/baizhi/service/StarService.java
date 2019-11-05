package com.baizhi.service;

import com.baizhi.entity.Star;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface StarService {
    Map<String,Object> findAllStar(Integer page,Integer rows);
    String insertStar(Star star);
    String upteStar(Star star);
}
