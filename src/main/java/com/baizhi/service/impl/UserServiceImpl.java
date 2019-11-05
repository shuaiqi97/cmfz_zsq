package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.dao.UserTrendDao;
import com.baizhi.entity.User;
import com.baizhi.entity.UserTrend;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserTrendDao userTrendDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllUser(String starId, Integer rows, Integer page) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setId(starId);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> userList = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        map.put("page",page);
        map.put("rows",userList);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> userList = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        map.put("page",page);
        map.put("rows",userList);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectAllUser() {
        List<User> userList = userDao.selectAll();
        return userList;
    }

    @Override
    public List<UserTrend> findAllTrend(String sex) {
        List<UserTrend> all = userTrendDao.findAll(sex);
        return all;
    }
}
