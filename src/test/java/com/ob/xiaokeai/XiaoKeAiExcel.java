package com.ob.xiaokeai;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/12/1 13:45
 * @Description:
 */
public class XiaoKeAiExcel {

    private static Workbook workbook;

    private static Sheet sheet;

    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\oubin\\Desktop\\xiaokeai.xlsx";
        InputStream is = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(is);
        List<List<String>> excelDataList =excelData(workbook);
        List<List<String>> exportExcelData = Lists.newArrayList();
        String symbol = excelDataList.get(0).get(0);
        List<List<String>> tempData = Lists.newArrayList();
        for (int i = 0; i < excelDataList.size(); i ++) {
            List<String> list = excelDataList.get(i);
            if (symbol.equals(list.get(0))) {
                tempData.add(list);
                if (i == excelDataList.size() - 1) {
                    dealExcelDataFromOneToZero(exportExcelData, tempData);
                    break;
                }
            } else {
                dealExcelDataFromOneToZero(exportExcelData, tempData);
                symbol = list.get(0);
                tempData = Lists.newArrayList();
                tempData.add(list);
                if (i == excelDataList.size() - 1) {
                    dealExcelDataFromOneToZero(exportExcelData, tempData);
                    break;
                }
            }
        }
        exportData(exportExcelData);
    }

    /**
     * 处理数据：从0变成1的位置
     *
     * @param exportExcelData
     * @param tempData
     */
    private static void dealExcelDataFromZeroToOne(List<List<String>> exportExcelData, List<List<String>> tempData) {

        int index = tempData.size() - 1;
        for (int i = 0; i < tempData.size() - 1; i++) {
            int x = Integer.parseInt(tempData.get(i).get(3));
            int y = Integer.parseInt(tempData.get(i + 1).get(3));
            if (y - x == 1) {
                index = i;
                break;
            }
        }
        for (int i = 0; i < tempData.size(); i++) {
            if (i <= index + 1) {
                tempData.get(i).add("0");
            } else {
                tempData.get(i).add("1");
            }
        }
        exportExcelData.addAll(tempData);
    }

    /**
     * 处理数据：从1变成0的位置
     *
     * @param exportExcelData
     * @param tempData
     */
    private static void dealExcelDataFromOneToZero(List<List<String>> exportExcelData, List<List<String>> tempData) {

        int index = tempData.size() - 1;
        for (int i = 0; i < tempData.size() - 1; i++) {
            int x = Integer.parseInt(tempData.get(i).get(2));
            int y = Integer.parseInt(tempData.get(i + 1).get(2));
            if (y - x == -1) {
                index = i;
                break;
            }
        }
        for (int i = 0; i < tempData.size(); i++) {
            if (i <= index) {
                tempData.get(i).add("0");
            } else {
                tempData.get(i).add("1");
            }
        }
        exportExcelData.addAll(tempData);
    }

    /**
     * 导出数据到excel
     *
     * @param exportExcelData
     * @throws IOException
     */
    public static void exportData(List<List<String>> exportExcelData) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet();
        for (int i = 0; i < exportExcelData.size(); i++) {
            XSSFRow row = sheet.createRow(i);
            List<String> list = exportExcelData.get(i);
            for (int j = 0; j < list.size(); j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(list.get(j));
            }
        }
        FileOutputStream fos = new FileOutputStream("C:\\Users\\oubin\\Desktop\\export.xlsx");
        xssfWorkbook.write(fos);
        fos.close();
    }

    private static List<List<String>> excelData(Workbook workbook) {
        sheet = workbook.getSheetAt(0);
        List<List<String>> excelDataList = Lists.newArrayList();
        for (int i = 1; i <= sheet.getLastRowNum(); i ++) {
            Row row = sheet.getRow(i);
            int firstCellNum = row.getFirstCellNum();
            int lastCellNum = row.getLastCellNum();
            List<String> list = Lists.newArrayList();
            for (int j = firstCellNum; j < lastCellNum; j ++) {
                Cell cell = row.getCell(j);
                CellType cellType = cell.getCellType();
                switch (cellType) {
                    case BLANK:
                    case _NONE:
                    case STRING:
                        list.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());
                        list.add(bigDecimal.toString());
                        break;
                    default:
                        list.add("");
                        break;
                }
            }
            excelDataList.add(list);
        }
        return excelDataList;
    }
}
