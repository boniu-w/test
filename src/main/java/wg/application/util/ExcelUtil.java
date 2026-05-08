package wg.application.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import wg.application.entity.ExcelParams;
import wg.application.excel.annotation.Excel;
import wg.application.excel.annotation.ExcelAnnotation;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DecimalFormat;
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
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private static Workbook workbook;
    private static Sheet sheet;
    private static Row row;
    
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##########");
    
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
            throw new IllegalStateException("Workbook对象为空！");
        }

        // sheet 所在
        int sheetIndex = (excelParams != null && excelParams.getSheetIndex() != null)
                ? excelParams.getSheetIndex() : 0;
        sheet = workbook.getSheetAt(sheetIndex);

        // 标题行
        // 标题行
        int titleIndex = (excelParams != null && excelParams.getTitleIndex() != null)
                ? excelParams.getTitleIndex() : 0;
        row = sheet.getRow(titleIndex);

        if (row == null) {
            throw new IllegalStateException("标题行为空！");
        }

        // 标题总列数
        int colNum = row.getLastCellNum();
        String[] title = new String[colNum];

        // 构建字段名映射表，提高查找效率
        Field[] fields = tClass.getDeclaredFields();
        Map<String, String> fieldNameMap = new HashMap<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Excel.class)) {
                Excel annotation = field.getAnnotation(Excel.class);
                fieldNameMap.put(annotation.name(), field.getName());
            }
        }

        // 填充标题数组
        for (int i = 0; i < colNum; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                String cellValue = cell.getStringCellValue();
                String fieldName = fieldNameMap.get(cellValue);
                if (fieldName != null) {
                    title[i] = fieldName;
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
        if (workbook == null) {
            throw new IllegalArgumentException("Workbook不能为空");
        }
        if (titleArray == null || titleArray.length == 0) {
            throw new IllegalArgumentException("标题数组不能为空");
        }

        // 获取sheet
        int sheetIndex = (excelParams != null && excelParams.getSheetIndex() != null)
                ? excelParams.getSheetIndex() : 0;
        sheet = workbook.getSheetAt(sheetIndex);

        // 获取起始行
        int contentStartIndex = (excelParams != null && excelParams.getContentStartIndex() != null)
                ? excelParams.getContentStartIndex() : 1;
        int endIndex = (excelParams != null && excelParams.getContentEndIndex() != null)
                ? excelParams.getContentEndIndex() : sheet.getLastRowNum();

        Map<Integer, Map<String, Object>> contentMap = new HashMap<>();

        for (int i = contentStartIndex; i <= endIndex; i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow == null) {
                continue;
            }

            LinkedHashMap<String, Object> cellValue = new LinkedHashMap<>();
            for (int j = 0; j < titleArray.length; j++) {
                Cell cell = currentRow.getCell(j);
                Object value = getCellFormatValue(cell);
                cellValue.put(titleArray[j], value);
            }

            // 忽略空行
            if (!MapUtil.isAllEmptyValue(cellValue)) {
                contentMap.put(i, cellValue);
            }
        }

        return contentMap;
    }


    public static <T> Map<String, Map<String, String>> getImportReplaceMap(Class<T> tClass) {
        Field[] declaredFields = tClass.getDeclaredFields();
        Map<String, Map<String, String>> fieldReplaceMap = new HashMap<>();

        for (Field field : declaredFields) {
            Excel annotation = field.getAnnotation(Excel.class);
            if (annotation == null) {
                continue;
            }

            String fieldName = field.getName();
            String dicCode = annotation.dicCode();

            // 如果没有字典代码，只处理replace字段
            if (org.apache.commons.lang3.StringUtils.isEmpty(dicCode)) {
                String[] replace = annotation.replace();
                if (replace != null && replace.length > 0) {
                    HashMap<String, String> replaceMap = new HashMap<>();
                    for (String replaceVal : replace) {
                        String[] split = replaceVal.split("_");
                        if (split.length == 2) {
                            replaceMap.put(split[0], split[1]);
                        }
                    }
                    if (!replaceMap.isEmpty()) {
                        fieldReplaceMap.put(fieldName, replaceMap);
                    }
                }
            } else {
                // TODO: 实现字典表查询逻辑
                logger.warn("字段 {} 使用了字典代码 {}，但字典查询功能尚未实现", fieldName, dicCode);
            }
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
        if (cell == null) {
            return "";
        }
        
        CellType cellType = cell.getCellType();
        if (cellType == null) {
            return "";
        }
        
        try {
            switch (cellType) {
                case NUMERIC:
                    return handleNumericCell(cell);
                case STRING:
                    return handleStringCell(cell);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return handleFormulaCell(cell);
                case BLANK:
                    return "";
                case ERROR:
                    logger.warn("单元格包含错误值，位置: {}", getCellPosition(cell));
                    return "";
                default:
                    logger.warn("未知单元格类型: {}, 位置: {}", cellType, getCellPosition(cell));
                    return "";
            }
        } catch (Exception e) {
            logger.error("读取单元格数据异常，位置: {}, 错误: {}", getCellPosition(cell), e.getMessage());
            return "";
        }
    }
    
    private static Object handleNumericCell(Cell cell) {
        if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
        // if (ExcelDateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue();
        } else {
            double numericValue = cell.getNumericCellValue();
            if (numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue)) {
                return String.valueOf((long) numericValue);
            } else {
                return DECIMAL_FORMAT.format(numericValue);
            }
        }
    }
    
    private static Object handleStringCell(Cell cell) {
        String value = cell.getRichStringCellValue().getString();
        return value != null ? value.trim() : "";
    }
    
    private static Object handleFormulaCell(Cell cell) {
        try {
            CellType cachedResultType = cell.getCachedFormulaResultType();
            if (cachedResultType == null) {
                return "";
            }
            
            switch (cachedResultType) {
                case NUMERIC:
                    return handleNumericCell(cell);
                case STRING:
                    return handleStringCell(cell);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case BLANK:
                    return "";
                case ERROR:
                    logger.warn("公式单元格计算结果为错误，位置: {}", getCellPosition(cell));
                    return "";
                default:
                    return "";
            }
        } catch (Exception e) {
            logger.error("计算公式值异常，位置: {}, 错误: {}", getCellPosition(cell), e.getMessage());
            return "";
        }
    }
    
    private static String getCellPosition(Cell cell) {
        if (cell == null) return "未知";
        Row row = cell.getRow();
        if (row == null) return "未知";
        Sheet sheet = row.getSheet();
        String sheetName = sheet != null ? sheet.getSheetName() : "未知";
        int rowIndex = row.getRowNum() + 1;
        int colIndex = cell.getColumnIndex() + 1;
        return String.format("%s!%s%d", sheetName, getColumnLetter(colIndex), rowIndex);
    }
    
    private static String getColumnLetter(int columnIndex) {
        StringBuilder columnLetter = new StringBuilder();
        while (columnIndex > 0) {
            columnIndex--;
            columnLetter.insert(0, (char) ('A' + columnIndex % 26));
            columnIndex /= 26;
        }
        return columnLetter.toString();
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

    public static <T> List<T> toObject(Class<T> tClass, Map<Integer, Map<String, Object>> contentMap,
                                       Map<String, Map<String, String>> importReplaceMap) {
        List<T> list = new ArrayList<>(contentMap.size());

        for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
            Map<String, Object> objectMap = entry.getValue();

            // 先处理替换逻辑
            if (importReplaceMap != null && !importReplaceMap.isEmpty()) {
                for (Map.Entry<String, Object> fieldEntry : objectMap.entrySet()) {
                    String fieldName = fieldEntry.getKey();
                    Object cellValue = fieldEntry.getValue();

                    Map<String, String> replaceValues = importReplaceMap.get(fieldName);
                    if (replaceValues != null && cellValue != null) {
                        String trimmedValue = cellValue.toString().trim();
                        String replacedValue = replaceValues.get(trimmedValue);
                        if (replacedValue != null) {
                            objectMap.put(fieldName, replacedValue);
                        }
                    }
                }
            }

            list.add(MapUtil.map2Bean(objectMap, tClass));
        }

        return list;
    }

}
