package com.baizhi.poi;


import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class TextExport {
    public static void main(String[] args) throws Exception {
        ArrayList<Users> list = new ArrayList<>();
        for (int i=0 ; i<=10;i++){
            Users users = new Users();
            users.setId(i+"");
            users.setName("zhang"+i);
            users.setBir(new Date());
            list.add(users);
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);
        HSSFSheet sheet = workbook.createSheet("学生信息");
        for (int i =0;i<list.size();i++){
            Users users = list.get(i);
            HSSFRow row = sheet.createRow(i);

            HSSFCell cell = row.createCell(0);
            cell.setCellValue(users.getId());

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(users.getName());

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(users.getBir());
            cell2.setCellStyle(cellStyle);
        }
        workbook.write(new FileOutputStream(new File("D:/baizhi/后期项目/texts.xls")));
    }
}
