package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "用户名称")
    private String username;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "盐")
    private String salt;
    @Excel(name = "真实姓名")
    private String nickname;
    @Excel(name = "电话")
    private String phone;
    @Excel(name = "省份")
    private String province;
    @Excel(name = "市")
    private String city;
    @Excel(name = "签名")
    private String sign;
    @Excel(name = "照片",type = 2)
    private String photo;
    @Excel(name = "性别")
    private String sex;
    @Column(name = "creat_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @Excel(name = "创建日期",format = "yyyy-MM-dd")
    private Date creatDate;
    @Column(name = "star_id")
    @Excel(name = "明星编号")
    private String starId;

}
