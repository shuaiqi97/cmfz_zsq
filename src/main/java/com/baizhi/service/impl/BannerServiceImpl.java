package com.baizhi.service.impl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.management.RuntimeMBeanException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service("bannerService")
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> findAllBanner(Integer page, Banner banner, Integer rows) {
        int offset = (page-1)*rows;
        RowBounds rowBounds = new RowBounds(offset,rows);
        List<Banner> banners = bannerDao.selectByRowBounds(banner, rowBounds);
        Map<String,Object> map = new HashMap<>();
        int count = bannerDao.selectCount(banner);
        map.put("page",page);
        map.put("rows",banners);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public void editBanner(Banner banner) {
        if ("".equals(banner.getCover())){
            banner.setCover(null);
        }
        try {
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public String addBanner(Banner banner) {
        try {
            banner.setId(UUID.randomUUID().toString());
            banner.setCreateDate(new Date());
            bannerDao.insert(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加失败");
        }
        return banner.getId();
    }

    @Override
    public void deleteBanner(String id, HttpServletRequest request) {
        Banner banner = bannerDao.selectByPrimaryKey(id);
        int i = bannerDao.deleteByPrimaryKey(id);
        if (i==0){
            throw new RuntimeException("删除失败");
        }else {
            String cover = banner.getCover();
            File file = new File(request.getServletContext().getRealPath("/back/banner/img"),cover);
            boolean delete = file.delete();
            if (delete==false){
                throw new RuntimeException("删除轮播图失败");
            }
        }
    }
}
