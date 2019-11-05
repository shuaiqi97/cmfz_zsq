package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserTrend;

import java.util.List;
import java.util.Map;

public interface UserService {
    //根据Id查询分页
    Map<String,Object> findAllUser(String starId,Integer rows,Integer page);
    //分页查询
    Map<String,Object> selectAll(Integer page,Integer rows);
    //查询所有
    List<User> selectAllUser();
    //查询用户注册趋势图
    List<UserTrend> findAllTrend(String sex);
}
