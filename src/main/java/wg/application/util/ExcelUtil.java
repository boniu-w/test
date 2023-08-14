package wg.application.util;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import wg.application.excel.annotation.Excel;
import wg.application.entity.ExcelParams;
import wg.application.excel.annotation.ExcelAnnotation;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


/*************************************************************
 * @Package com.Gzs.demo.SpringSecurityDemo.Common1.Utils
 * @author wg
 * @description: 以后不要用了, 直接用 cn.afterturn.easypoi --01122023
 * @date 2020/7/2 10:00
 * @version
 * @Copyright 使用本工具 要结合 我的 Excel 注解使用,
 *************************************************************/
public class ExcelUtil {
    private Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private static Workbook workbook;
    private static Sheet sheet;
    private static Row row;
    
    public static Workbook getWorkbook() {
        return workbook;
    }
    
    /*************************************************************
     * 初始化workbook
     * @author: wg
     * @time: 2020/5/28 9:51
     *************************************************************/
    public static Workbook initWorkbook(MultipartFile file) {
        if (file == null) {
            return null;
        }
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf("."));
        
        try {
            InputStream is = file.getInputStream();
            switch (ext) {
                case ".xls":
                case ".et":
                    workbook = new HSSFWorkbook(is);
                    return workbook;
                case ".xlsx":
                    workbook = new XSSFWorkbook(is);
                    return workbook;
                default:
                    workbook = null;
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //System.out.println("<><><><><>< 初始化 workbook 完成 ><><><><>");
        }
        
        return new HSSFWorkbook();
    }
    
    public static Workbook initWorkbook(File file) throws IOException {
        if (file == null) {
            return null;
        }
        workbook = new XSSFWorkbook(new FileInputStream(file));
        return workbook;
    }
    
    /***************************************************
     * 获取 前端 上传的文件信息
     * @author: wg
     * @time: 2020/4/26 11:38
     ***************************************************/
    public static List<MultipartFile> getUploadFiles(HttpServletRequest request) {
        // (这里使用Vector，而不使用ArrayLsit，是怕引起线程安全问题，因为后面会引用到相同的内存地址)
        List<MultipartFile> fileVector = new Vector<>();
        
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            
            if (commonsMultipartResolver.isMultipart(request)) {
                while (iterator.hasNext()) {
                    // 将当前文件名一致的文件流放入同一个集合中
                    List<MultipartFile> fileRows = multipartHttpServletRequest.getFiles(iterator.next());
                    
                    // 对文件做去重设置
                    // 判断集合是否存在，并且是否大于0
                    if (fileRows != null && fileRows.size() != 0) {
                        for (MultipartFile file : fileRows) {
                            String name = file.getName();
                            
                            if (file != null && !file.isEmpty()) {
                                fileVector.add(file);
                            }
                        }
                    }
                }
                return fileVector;
            }
        }
        return fileVector;
    }
    
    /****************************************************************
     * 读取第一行 默认是标题行
     * @author: wg
     * @time: 2020/7/2 15:05
     ****************************************************************/
    public static <T> String[] readExcelTitle(@Nullable ExcelParams excelParams, Class<T> tClass) throws NullPointerException {
        if (workbook == null) {
            try {
                throw new Exception("Workbook对象为空！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        int numberOfSheets = workbook.getNumberOfSheets();
        
        // sheet 所在
        if (!ObjectUtils.isEmpty(excelParams) && !ObjectUtils.isEmpty(excelParams.getSheetIndex())) {
            sheet = workbook.getSheetAt(excelParams.getSheetIndex());
        } else {
            sheet = workbook.getSheetAt(0);
        }
        
        // 标题行
        if (!ObjectUtils.isEmpty(excelParams) && !ObjectUtils.isEmpty(excelParams.getTitleIndex())) {
            row = sheet.getRow(excelParams.getTitleIndex());
        } else {
            row = sheet.getRow(0);
        }
        
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        colNum = row.getLastCellNum();
        String[] title = new String[colNum];
        Field[] fields = tClass.getDeclaredFields();
        
        String cellValue = "";
        Excel annotation = null;
        String annotationName = "";
        
        for (int i = 0; i < colNum; i++) {
            cellValue = row.getCell(i).getStringCellValue();
            for (int j = 0; j < fields.length; j++) {
                if (fields[j].isAnnotationPresent(Excel.class)) {
                    annotation = fields[j].getAnnotation(Excel.class);
                    annotationName = annotation.name();
                    if (annotationName.equals(cellValue)) {
                        title[i] = fields[j].getName();
                    }
                }
            }
        }
        
        return title;
    }
    
    public static <T> String[] _readExcelTitle(@Nullable ExcelParams excelParams, Class<T> tClass) throws NullPointerException {
        if (workbook == null) {
            try {
                throw new Exception("Workbook对象为空！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        int numberOfSheets = workbook.getNumberOfSheets();
        
        // sheet 所在
        if (!ObjectUtils.isEmpty(excelParams) && !ObjectUtils.isEmpty(excelParams.getSheetIndex())) {
            sheet = workbook.getSheetAt(excelParams.getSheetIndex());
        } else {
            sheet = workbook.getSheetAt(0);
        }
        
        // 标题行
        if (!ObjectUtils.isEmpty(excelParams) && !ObjectUtils.isEmpty(excelParams.getTitleIndex())) {
            row = sheet.getRow(excelParams.getTitleIndex());
        } else {
            row = sheet.getRow(0);
        }
        
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        colNum = row.getLastCellNum();
        String[] title = new String[colNum];
        Field[] fields = tClass.getDeclaredFields();
        
        String cellValue = "";
        ExcelAnnotation annotation = null;
        String[] annotationName = new String[0];
        
        for (int i = 0; i < colNum; i++) {
            cellValue = row.getCell(i).getStringCellValue();
            for (int j = 0; j < fields.length; j++) {
                if (fields[j].isAnnotationPresent(ExcelAnnotation.class)) {
                    annotation = fields[j].getAnnotation(ExcelAnnotation.class);
                    annotationName = annotation.name();
                    for (String name : annotationName) {
                        if (name.equals(cellValue)) {
                            title[i] = fields[j].getName();
                        }
                    }
                }
            }
        }
        
        return title;
    }
    /****************************************************************
     * 读取的 excel 内容 应该以 表头对应字段 为键 形成map
     * @author: wg
     * @time: 2020/7/2 14:09
     ****************************************************************/
    public static Map<Integer, Map<String, Object>> readExcelContent(Workbook workbook,
                                                                     String[] titleArray,
                                                                     @Nullable ExcelParams excelParams) throws Exception {
        Map<Integer, Map<String, Object>> contentMap = new HashMap<Integer, Map<String, Object>>();
        
        // 得到总行数
        if (ObjectUtils.isEmpty(excelParams) || ObjectUtils.isEmpty(excelParams.getSheetIndex())) {
            sheet = workbook.getSheetAt(0);
        } else {
            sheet = workbook.getSheetAt(excelParams.getSheetIndex());
        }
        int rowNum = sheet.getLastRowNum();
        
        // 总列数
        if (ObjectUtils.isEmpty(excelParams) || ObjectUtils.isEmpty(excelParams.getTitleIndex())) {
            row = sheet.getRow(0);
        } else {
            row = sheet.getRow(excelParams.getTitleIndex());
        }
        int colNum = row.getPhysicalNumberOfCells();
        
        // 默认正文内容应该从第二行开始,第一行为表头的标题
        int i;
        if (ObjectUtils.isEmpty(excelParams) || ObjectUtils.isEmpty(excelParams.getContentStartIndex())) {
            i = 1;
        } else {
            i = excelParams.getContentStartIndex();
        }
        
        int endIndex = rowNum;
        if (!ObjectUtils.isEmpty(excelParams) && !ObjectUtils.isEmpty(excelParams.getContentEndIndex())) {
            endIndex = excelParams.getContentEndIndex();
        }
        
        LinkedHashMap<String, Object> cellValue = new LinkedHashMap<>();
        for (; i <= endIndex; i++) {
            cellValue = new LinkedHashMap<String, Object>();
            int j = 0;
            row = sheet.getRow(i);
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                cellValue.put(titleArray[j], obj);
                j++;
            }
            // 忽略空行
            if (!MapUtil.isAllEmptyValue(cellValue)) {
                contentMap.put(i, cellValue);
            }
        }
        return contentMap;
    }
    
    public static <T> Map<String, Map<String, String>> getImportReplaceMap(Class<T> tClass) throws InstantiationException, IllegalAccessException {
        Field[] declaredFields = tClass.getDeclaredFields();
        
        Map<String, Map<String, String>> fieldReplaceMap = new HashMap<>();
        for (int j = 0; j < declaredFields.length; j++) {
            Excel annotation = declaredFields[j].getAnnotation(Excel.class);
            String fieldName = declaredFields[j].getName();
            String dicCode = annotation.dicCode();
            String dictTable = annotation.dictTable();
            String dicText = annotation.dicText();
            boolean anImport = annotation.isImport();
            boolean anExport = annotation.isExport();
            // 如果字典字段为 null, 则 只替换 replace 字段
            if (org.apache.commons.lang3.StringUtils.isEmpty(dicCode)) {
                String[] replace = annotation.replace();
                HashMap<String, String> replaceMap = new HashMap<>();
                if (replace.length > 0) {
                    for (int k = 0; k < replace.length; k++) {
                        String replaceVal = replace[k];
                        String[] split = replaceVal.split("_");
                        if (split.length == 2) {
                            replaceMap.put(split[0], split[1]);
                            // if (anImport) {
                            //     replaceMap.put(split[0], split[1]);
                            // }
                            // if (anExport) {
                            //     replaceMap.put(split[1], split[0]);
                            // }
                        }
                    }
                    // 解析 replace 得到完整的 map 之后, 用字段名为键, 存储起来
                    fieldReplaceMap.put(fieldName, replaceMap);
                }
            }
            // 如果字典字段不为 null, 则 去 字典表里查, 查出要替换的
            
        }
        return fieldReplaceMap;
    }

    public static <T> Map<String, Map<String, String>> getExportReplaceMap(Class<T> tClass) throws InstantiationException, IllegalAccessException {
        Field[] declaredFields = tClass.getDeclaredFields();
        
        Map<String, Map<String, String>> fieldReplaceMap = new HashMap<>();
        for (int j = 0; j < declaredFields.length; j++) {
            Excel annotation = declaredFields[j].getAnnotation(Excel.class);
            String fieldName = declaredFields[j].getName();
            String dicCode = annotation.dicCode();
            String dictTable = annotation.dictTable();
            String dicText = annotation.dicText();
            boolean anImport = annotation.isImport();
            boolean anExport = annotation.isExport();
            // 如果字典字段为 null, 则 只替换 replace 字段
            if (org.apache.commons.lang3.StringUtils.isEmpty(dicCode)) {
                String[] replace = annotation.replace();
                HashMap<String, String> replaceMap = new HashMap<>();
                if (replace.length > 0) {
                    for (int k = 0; k < replace.length; k++) {
                        String replaceVal = replace[k];
                        String[] split = replaceVal.split("_");
                        if (split.length == 2) {
                            replaceMap.put(split[1], split[0]);
                        }
                    }
                    // 解析 replace 得到完整的 map 之后, 用字段名为键, 存储起来
                    fieldReplaceMap.put(fieldName, replaceMap);
                }
            }
            // 如果字典字段不为 null, 则 去 字典表里查, 查出要替换的
            
        }
        return fieldReplaceMap;
    }
    
    /**
     * 根据Cell类型设置数据
     *
     * @param
     */
    // public static Object getCellFormatValue(Cell cell) {
    //     Object cellvalue = "";
    //
    //     DecimalFormat decimalFormat = new DecimalFormat();
    //
    //     if (cell != null) {
    //         switch (cell.getCellType()) {
    //             case Cell.CELL_TYPE_NUMERIC: {
    //                 short s = cell.getCellStyle().getDataFormat();
    //                 if (ExcelDateUtil.isCellDateFormatted(cell)) {
    //                     cellvalue = cell.getDateCellValue();
    //                 } else {
    //                     cellvalue = decimalFormat.format(cell.getNumericCellValue()).replace(",", "");
    //                 }
    //                 break;
    //             }
    //             case Cell.CELL_TYPE_STRING:
    //                 cellvalue = cell.getRichStringCellValue().getString().replace(",", "");
    //                 break;
    //             case Cell.CELL_TYPE_BOOLEAN:
    //                 cellvalue = String.valueOf(cell.getBooleanCellValue());
    //                 break;
    //             case Cell.CELL_TYPE_FORMULA:
    //                 cellvalue = String.valueOf(cell.getCellFormula());
    //                 break;
    //             case Cell.CELL_TYPE_ERROR:
    //                 cellvalue = "非法字符";
    //                 break;
    //             case Cell.CELL_TYPE_BLANK:
    //                 cellvalue = "";
    //                 break;
    //             default:
    //                 cellvalue = "未知类型";
    //                 break;
    //         }
    //     } else {
    //         cellvalue = "";
    //     }
    //     return cellvalue;
    // }
    
    // apache poi 4.1.2
    public static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        CellType cellType = cell.getCellType();
        
        DecimalFormat decimalFormat = new DecimalFormat();
        
        if (cellType != null) {
            switch (cellType) {
                case NUMERIC: {
                    short s = cell.getCellStyle().getDataFormat();
                    if (ExcelDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {
                        cellvalue = decimalFormat.format(cell.getNumericCellValue()).replace(",", "");
                    }
                    break;
                }
                case STRING:
                    cellvalue = cell.getRichStringCellValue().getString().replace(",", "");
                    break;
                case BOOLEAN:
                    cellvalue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    CellType cachedFormulaResultType = cell.getCachedFormulaResultType();
                    cellvalue = handlerFormula(cell, cachedFormulaResultType);
                    break;
                case ERROR:
                    cellvalue = "非法字符";
                    break;
                case BLANK:
                    cellvalue = "";
                    break;
                default:
                    cellvalue = "未知类型";
                    break;
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    public static Object handlerFormula(Cell cell, CellType cachedFormulaResultType) {
        Object cellvalue = "";
        DecimalFormat decimalFormat = new DecimalFormat();

        if (cachedFormulaResultType != null) {
            switch (cachedFormulaResultType) {
                case NUMERIC: {
                    if (ExcelDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {
                        cellvalue = decimalFormat.format(cell.getNumericCellValue()).replace(",", "");
                    }
                    break;
                }
                case STRING:
                    cellvalue = cell.getRichStringCellValue().getString().replace(",", "");
                    break;
                case BOOLEAN:
                    cellvalue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case ERROR:
                    cellvalue = "非法字符";
                    break;
                case BLANK:
                    cellvalue = "";
                    break;
                default:
                    cellvalue = "未知类型";
                    break;
            }
        }
        return cellvalue;
    }
    
    public static Cell setCellValue(Cell cell, Object cellValue) {
        if (cellValue == null) return cell;
        
        Workbook workbook = cell.getSheet().getWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        
        if (cellValue instanceof String) {
            cell.setCellValue((String) cellValue);
        } else if (cellValue instanceof Double) {
            cell.setCellValue((Double) cellValue);
        } else if (cellValue instanceof Integer) {
            cell.setCellValue((Integer) cellValue);
        } else if (cellValue instanceof Boolean) {
            cell.setCellValue((Boolean) cellValue);
        } else if (cellValue instanceof Date) {
            cell.setCellValue((Date) cellValue);
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/MM/dd"));
            cell.setCellStyle(dateCellStyle);
        } else if (cellValue instanceof Calendar) {
            cell.setCellValue((Calendar) cellValue);
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/MM/dd"));
            cell.setCellStyle(dateCellStyle);
        } else if (cellValue instanceof LocalDateTime){
            cell.setCellValue((LocalDateTime) cellValue);
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/MM/dd"));
            cell.setCellStyle(dateCellStyle);
        }
        return cell;
    }

    public void exportWorkbook(Workbook workbook, String fileName, HttpServletResponse response) throws IOException {
        try (ServletOutputStream out = response.getOutputStream()) {
            String name = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + name + ".xlsx");

            workbook.write(out);
            out.flush();
        }
    }
    
    /************************************************************************
     * @author: wg
     * @description: 获取上传的excel全部信息
     * @params:
     * @return:
     * @createTime: 11:15  2023/6/28
     * @updateTime: 11:15  2023/6/28
     ************************************************************************/
    public static Map<String, Map<Integer, Map<Integer, Cell>>> getWorkbookData(List<MultipartFile> uploadFiles) {
        if (uploadFiles == null || uploadFiles.size() == 0) {
            return null;
        }
        
        if (uploadFiles.size() == 1) {
            MultipartFile multipartFile = uploadFiles.get(0);
            Workbook workbook = ExcelUtil.initWorkbook(multipartFile);
            Map<String, Map<Integer, Map<Integer, Cell>>> workbookMap = new HashMap<>();
            for (Sheet sheet : workbook) {
                Map<Integer, Map<Integer, Cell>> map = new HashMap<>();
                for (int i = 0; i < sheet.getLastRowNum(); i++) {
                    Map<Integer, Cell> rowMap = new HashMap<>();
                    Row rowi = sheet.getRow(i);
                    for (int j = 0; j < rowi.getLastCellNum(); j++) {
                        Cell cell = rowi.getCell(j);
                        rowMap.put(j, cell);
                    }
                    // 忽略空行
                    if (!MapUtil.isAllEmptyValue(rowMap)) {
                        map.put(i, rowMap);
                    }
                }
                workbookMap.put(sheet.getSheetName(), map);
            }
            return workbookMap;
        }
        return null;
    }
    
    public static Map<String, Map<Integer, Map<Integer, Object>>> getWorkbookData(Map<String, Map<Integer, Map<Integer, Cell>>> workbookData) {
        Map<String, Map<Integer, Map<Integer, Object>>> workbookMap = new HashMap<>();
        Map<Integer, Map<Integer, Object>> rowMap = new HashMap<>();
        Map<Integer, Object> map = new HashMap<>();
        
        for (Map.Entry<String, Map<Integer, Map<Integer, Cell>>> mapEntry : workbookData.entrySet()) {
            String sheetName = mapEntry.getKey();
            Map<Integer, Map<Integer, Cell>> value = mapEntry.getValue();
            rowMap = new HashMap<>();
            for (Map.Entry<Integer, Map<Integer, Cell>> entry : value.entrySet()) {
                Integer rowIndex = entry.getKey();
                Map<Integer, Cell> cellMap = entry.getValue();
                map = new HashMap<>();
                for (Map.Entry<Integer, Cell> cellEntry : cellMap.entrySet()) {
                    Integer cellIndex = cellEntry.getKey();
                    Cell cell = cellEntry.getValue();
                    Object obj = ExcelUtil.getCellFormatValue(cell);
                    map.put(cellIndex, obj);
                }
                rowMap.put(rowIndex, map);
            }
            workbookMap.put(sheetName, rowMap);
        }
        
        return workbookMap;
    }

    public static <T> List<T> getData(File file, ExcelParams excelParams, Class<T> tClass) throws Exception {
        Workbook workbook = initWorkbook(file);
        return getData(workbook, excelParams, tClass);
    }

    public static <T> List<T> getData(MultipartFile file, ExcelParams excelParams, Class<T> tClass) throws Exception {
        Workbook workbook = initWorkbook(file);
        return getData(workbook, excelParams, tClass);
    }

    public static <T> List<T> getData(Workbook workbook, ExcelParams excelParams, Class<T> tClass) throws Exception {
        String[] titles = readExcelTitle(excelParams, tClass);
        Map<Integer, Map<String, Object>> content = readExcelContent(workbook, titles, excelParams);
        Map<String, Map<String, String>> replaceMap = getImportReplaceMap(tClass);
        List<T> tList = toObject(tClass, content, replaceMap);
        
        return tList;
    }
    
    public static <T> List<T> toObject(Class<T> tClass, Map<Integer, Map<String, Object>> contentMap, Map<String, Map<String, String>> importReplaceMap) {
        List<T> list = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, Object>> integerMapEntry : contentMap.entrySet()) {
            Map<String, Object> objectMap = integerMapEntry.getValue();
            for (Map.Entry<String, Object> objectEntry : objectMap.entrySet()) {
                String fieldName = objectEntry.getKey();
                Object cellValue = objectEntry.getValue();
                if (importReplaceMap != null && !importReplaceMap.isEmpty()) {
                    for (Map.Entry<String, Map<String, String>> stringMapEntry : importReplaceMap.entrySet()) {
                        Map<String, String> replaceValues = stringMapEntry.getValue();
                        for (Map.Entry<String, String> entry : replaceValues.entrySet()) {
                            String excelVal = entry.getKey();
                            String tableVal = entry.getValue();
                            if (excelVal.equals(cellValue.toString().trim())) {
                                objectMap.put(fieldName, tableVal);
                            }
                        }
                    }
                }
            }
            list.add(MapUtil.map2Bean(objectMap, tClass));
        }
        return list;
    }

}
