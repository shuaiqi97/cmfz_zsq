package com.baizhi.dao;

import com.baizhi.entity.UserTrend;

import java.util.List;

public interface UserTrendDao {
    List<UserTrend> findAll(String sex);
}
