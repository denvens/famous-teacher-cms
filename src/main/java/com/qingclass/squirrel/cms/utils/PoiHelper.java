package com.qingclass.squirrel.cms.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class PoiHelper {

    /**
     * @param response
     * @param fileName excel文件名
     * @param headMap  表头map
     * @param dataList 表格数据
     */
    public static void exportXlsx(HttpServletResponse response, String fileName,
                                  Map<String, String> headMap, List<Map<String, Object>> dataList) {

        Workbook workbook = exportXlsx(fileName, headMap, dataList);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Accept-Ranges", "bytes");

        ServletOutputStream outputStream = null;

        try {
            // 通常解决汉字乱码方法用URLEncoder.encode(...)
            String fileNameDisplay = URLEncoder.encode(fileName, "UTF-8") + ".xlsx";

            response.addHeader("Content-Disposition", "attachment;filename=" + fileNameDisplay);
            // httpServletResponse.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName + ".xlsx", "UTF-8") + "\"");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Accept-Ranges", "bytes");
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 导出数据
     *
     * @param headMap
     * @param dataList
     */
    public static Workbook exportXlsx(String sheetName, Map<String, String> headMap, List<Map<String, Object>> dataList) {

        Workbook workbook = new SXSSFWorkbook();

        Sheet sheet = workbook.createSheet(sheetName);


        int rowIndex = 0, columnIndex = 0;
        Set<String> keys = headMap.keySet();

        //表头
        Row row = sheet.createRow(rowIndex++);
        for (String key : keys) {
            Cell cell = row.createCell(columnIndex++);
            cell.setCellValue(headMap.get(key));
        }

        //内容
        if (dataList != null && !dataList.isEmpty()) {
            for (Map<String, Object> map : dataList) {
                row = sheet.createRow(rowIndex++);
                columnIndex = 0;
                for (String key : keys) {
                    Cell cell = row.createCell(columnIndex++);
                    setCellValue(cell, map.get(key));

                }
            }
        }

        return workbook;
    }

    private static void setCellValue(Cell cell, Object obj) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        if (obj == null) {
            return;
        }
        if (obj instanceof String) {
            cell.setCellValue((String) obj);
        } else if (obj instanceof Date) {
            Date date = (Date) obj;
            if (date != null) {
                cell.setCellValue(sdf.format(date));
            }
        } else if (obj instanceof Calendar) {
            Calendar calendar = (Calendar) obj;
            if (calendar != null) {
                cell.setCellValue(sdf.format(calendar.getTime()));
            }
        } else if (obj instanceof Double) {
            cell.setCellValue((Double) obj);
        } else {
            cell.setCellValue(obj.toString());
        }
    }

}
