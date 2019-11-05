package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class TextPoi {
    public static void main(String[] args) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("测试");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        cellStyle.setFont(font);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("zhang");
        workbook.write(new FileOutputStream(new File("D:/baizhi/后期项目/text.xls")));

    }
}
