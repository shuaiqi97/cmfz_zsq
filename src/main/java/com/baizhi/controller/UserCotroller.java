package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.entity.UserTrend;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserCotroller {
    @Autowired
    private UserService userService;
    @RequestMapping("findAllUser")
    public Map<String,Object> findAllUser(Integer page,Integer rows,String starId){
        Map<String, Object> map = userService.findAllUser(starId, rows, page);
        return map;
    }
    @RequestMapping("selectAll")
    public Map<String,Object> selectAll (Integer page,Integer rows){
        Map<String, Object> map = userService.selectAll(page, rows);
        return map;
    }
    @RequestMapping("selectAllUser")
    public List<User> selectAllUser(){
        List<User> userList = userService.selectAllUser();
        return userList;
    }
    @RequestMapping("userPoi")
    public void userPoi(HttpServletResponse response) {
        List<User> list = userService.selectAllUser();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户信息","用户"),User.class, list);
        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        try {
            fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("UserTrend")
    public Map<String,Object> UserTrend(String sex){
        Map<String, Object> map = new HashMap<>();
        List<UserTrend> allTrend = userService.findAllTrend("男");
        int[] nan = {0,0,0,0,0,0};
        for (UserTrend userTrend : allTrend) {
            String month = userTrend.getMonth();
            for (int j = 1;j<=6;j++){
                String s = String.valueOf(j);
                if (month.equals(s)){
                    nan [j-1] =userTrend.getCount();
                }
            }
        }
        map.put("nan",nan);
        List<UserTrend> allTrend1 = userService.findAllTrend("女");
        int[] nv = {0,0,0,0,0,0};
        for (UserTrend userTrend1 : allTrend1) {
            String month = userTrend1.getMonth();
            for (int j = 1;j<=6;j++){
                String s = String.valueOf(j);
                if (month.equals(s)){
                    nv [j-1] =userTrend1.getCount();
                }
            }
        }
        map.put("nv",nv);
        return map;
    }
}
