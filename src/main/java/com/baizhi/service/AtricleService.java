package com.baizhi.service;

import com.baizhi.entity.Atricle;


import java.util.Map;

public interface AtricleService {
    Map<String,Object> finaAllAtricle(Integer page,Integer rows);
    void addAtricle(Atricle atricle);
    void editAtricle(Atricle atricle);
    void deleteAtricle(Atricle atricle);
}
