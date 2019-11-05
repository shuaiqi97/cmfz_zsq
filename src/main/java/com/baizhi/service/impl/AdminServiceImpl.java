package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void login(Admin admin, String inputCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");
        Admin selectOne = adminDao.selectOne(admin);
        if (inputCode.equalsIgnoreCase(securityCode)){
            if (selectOne ==null){
                throw new RuntimeException("账户不存在或密码错误");
            }else {
                session.setAttribute("selectOne",selectOne);
            }
        }else {
            throw new RuntimeException("验证码错误");
        }
    }
}
