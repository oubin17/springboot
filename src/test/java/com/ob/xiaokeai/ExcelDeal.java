package com.ob.xiaokeai;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @Author: oubin
 * @Date: 2019/11/30 14:36
 * @Description: symbol-0  typeid-1  year-2
 */
public class ExcelDeal {

    private static Workbook workbook;

    private static Sheet sheet;

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\desk\\abc.xlsx";
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
                    dealExcelData(exportExcelData, tempData);
                    break;
                }
            } else {
                dealExcelData(exportExcelData, tempData);
                symbol = list.get(0);
                tempData = Lists.newArrayList();
                tempData.add(list);
                if (i == excelDataList.size() - 1) {
                    dealExcelData(exportExcelData, tempData);
                    break;
                }
            }
        }
        exportData(exportExcelData);

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
        FileOutputStream fos = new FileOutputStream("D:\\desk\\export.xlsx");
        xssfWorkbook.write(fos);
        fos.close();
    }

    /**
     * 处理excel数据
     *
     * @param exportExcelData
     * @param tempData
     */
    public static void dealExcelData(List<List<String>> exportExcelData, List<List<String>> tempData) {
        String symbol = tempData.get(0).get(0);
        Map<String, Integer> yearValue = Maps.newHashMap();
        Map<String, String> yearTypeId = Maps.newHashMap();
        for (int i = 0; i < tempData.size(); i++) {
            List<String> list = tempData.get(i);
            List<String> years = Lists.newArrayList();
            String typeId = list.get(1);
            String yearStr = list.get(2);
            if (StringUtils.isNotEmpty(yearStr)) {
                IntStream.range(0, yearStr.length() / 4).forEach(operand ->
                        years.add(yearStr.substring(operand * 4, operand * 4 + 4))
                );
                years.stream()
                        .filter(year -> !StringUtils.equals("0000", year))
                        .forEach(year -> {
                            yearValue.merge(year, 1, (a, b) -> a + b);
                            yearTypeId.merge(year, typeId, (a, b) -> a + "," + b);
                        });
            }
        }
        //去重
        yearTypeId.forEach((year, typeId) -> {
            List<String> typeIdList = Arrays.asList(typeId.split(","));
            Set<String> typeIdSet = new HashSet<>(typeIdList);
            StringJoiner stringJoiner = new StringJoiner(",");
            typeIdSet.forEach(stringJoiner::add);
            yearTypeId.put(year, stringJoiner.toString());
        });
        List<List<String>> exportData = Lists.newArrayList();
        for (int i = 2000; i <= 2018; i++) {
            List<String> exportList = Lists.newArrayList();
            exportList.add(symbol);
            exportList.add(StringUtils.isEmpty(yearTypeId.get(String.valueOf(i))) ? "" : yearTypeId.get(String.valueOf(i)));
            exportList.add(String.valueOf(i));
            exportList.add(null == yearValue.get(String.valueOf(i)) ? "0" : String.valueOf(yearValue.get(String.valueOf(i))));
            exportData.add(exportList);
        }
        exportExcelData.addAll(exportData);
    }

    /**
     * 从excel中获取所有数据
     *
     * @param workbook
     * @return
     */
    public static List<List<String>> excelData(Workbook workbook) {
        sheet = workbook.getSheetAt(0);
        List<List<String>> excelDataList = Lists.newArrayList();
        for (int i = 1; i <= sheet.getLastRowNum(); i ++) {
            List<String> list = Lists.newArrayList();
            Row row = sheet.getRow(i);
            BigDecimal decimal = new BigDecimal(row.getCell(1).getNumericCellValue());
            String symbol = decimal.toString();

            String violationTypeId = row.getCell(7).getStringCellValue();
            String violationYear;
            CellType cellType = row.getCell(9).getCellType();
            switch (cellType) {
                case BLANK:
                case _NONE:
                case STRING:
                    violationYear = row.getCell(9).getStringCellValue();
                    break;
                case NUMERIC:
                    BigDecimal bigDecimal = new BigDecimal(row.getCell(9).getNumericCellValue());
                    violationYear = bigDecimal.toString();
                    break;
                default:
                    violationYear = "";
                    break;

            }
            list.add(symbol);
            list.add(violationTypeId);
            list.add(violationYear);
            excelDataList.add(list);
        }
        return excelDataList;

    }
}
