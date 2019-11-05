package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Column;
import javax.persistence.Id;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    @Id
    private String id;
    private String singer;
    private String size;
    private String duration;
    private String name;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "album_id")
    private String albumId;
}
