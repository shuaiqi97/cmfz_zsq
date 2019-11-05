package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BannerService {
    Map<String,Object> findAllBanner(Integer page, Banner banner, Integer rows);
    void editBanner(Banner banner);
    String addBanner(Banner banner);
    void deleteBanner(String id, HttpServletRequest request);
}
