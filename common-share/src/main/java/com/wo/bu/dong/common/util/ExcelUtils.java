package com.wo.bu.dong.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.wo.bu.dong.common.exception.ExcelException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelUtils {
    private final static int rowAccessWindowSize = 1000; // keep 1000 rows in memory, exceeding rows will be flushed to disk

    private ExcelUtils() {
    }

    /**
     * export excel
     * cellTitles value:propertyName=cellTitle
     * 
     * @param cellTitles
     * @param data
     * @param output
     */
    public static void exportExcel(List<String> cellTitles, List<Object> data, OutputStream output) {
        //check
        if (CollectionUtils.isEmpty(data) || CollectionUtils.isEmpty(cellTitles) || null == output) {
            return;
        }
        //create excel
        try (SXSSFWorkbook wb = new SXSSFWorkbook(rowAccessWindowSize)) {
            Sheet sheet = wb.createSheet();
            int rowNum = 0;
            int cellTotal = cellTitles.size();
            //cell map to object property(key:cell,value:peropertyName)
            Map<Integer, String> cellPropsMap = new HashMap<>();
            //add title
            Row titleRow = sheet.createRow(rowNum++);
            Cell titleCell = null;
            for (int i = 0; i < cellTotal; i++) {
                titleCell = titleRow.createCell(i);
                String title = cellTitles.get(i);
                titleCell.setCellValue(StringUtils.substringAfterLast(title, "="));
                //init cellPropsMap
                cellPropsMap.put(i, StringUtils.substringBefore(title, "+"));
            }
            //add data 
            Row dataRow = null;
            for (Object obj : data) {
                dataRow = sheet.createRow(rowNum++);
                for (Integer cellNum : cellPropsMap.keySet()) {
                    dataRow.createCell(cellNum).setCellValue(BeanUtils.getProperty(obj, cellPropsMap.get(cellNum)));
                }
            }
            wb.write(output);
            wb.dispose();
        } catch (Exception e) {
            log.error("exportExcel, export excel error msg:{}", e.getMessage(), e);
            throw new ExcelException("export excel error", e);
        }

    }

    /**
     * import excel convert to list
     * 
     * @param input
     * @return
     */
    public static List<List<Object>> importExcel(InputStream input) {
        if (null == input)
            return null;
        List<List<Object>> sheetList = new ArrayList<>();
        List<Object> rowList = null;
        try (Workbook wb = WorkbookFactory.create(input)) {
            for (Sheet sheet : wb) {
                if (null == sheet)
                    continue;
                for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                    rowList = new ArrayList<>();
                    for (Cell cell : sheet.getRow(i)) {
                        rowList.add(getCellValue(cell));
                    }
                    sheetList.add(rowList);
                }
            }
        } catch (Exception e) {
            log.error("importExcel, import excel error msg:{}", e.getMessage(), e);
            throw new ExcelException("import excel error", e);
        }
        return sheetList;
    }

    private static Object getCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return null;
            default:
                return null;
        }

    }

}
