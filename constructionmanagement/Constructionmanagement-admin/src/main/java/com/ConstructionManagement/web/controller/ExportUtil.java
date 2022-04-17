package com.ConstructionManagement.web.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ConstructionManagement.system.domain.WmPartEntry;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportUtil {
    /**
     * @param response
     * @param sheetName 表格名
     * @param list      导出的数据
     * @param T         class
     */
    public void outPut(HttpServletResponse response, String sheetName, List list, Class T) {
        ServletOutputStream outputStream = null;
        try {
            // 设置响应输出的头类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            ExportParams exportParams = new ExportParams();
            // 设置sheet得名称
            exportParams.setSheetName(sheetName);
            Map<String, Object> map = new HashMap<>();
            // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            map.put("title", exportParams);
            // 模版导出对应得实体类型，即包含了List的对象
            map.put("entity", T);
            // sheet中要填充得数据
            map.put("data", list);
            List<Map<String, Object>> sheetsList = new ArrayList<>();
            sheetsList.add(map);
            //创建excel文件的方法
            Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
            //通过response输出流直接输入给客户端
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
