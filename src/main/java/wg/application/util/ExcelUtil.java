package wg.application.util;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
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
import wg.application.annotation.Excel;
import wg.application.entity.ExcelParams;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    public static <T> String[] readExcelTitle(@Nullable ExcelParams excelParams, T t) throws NullPointerException {
        if (workbook == null) {
            try {
                throw new Exception("Workbook对象为空！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int numberOfSheets = workbook.getNumberOfSheets();
        //System.out.println("numberOfSheets: "+numberOfSheets);

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
        Field[] fields = t.getClass().getDeclaredFields();

        // System.out.println(Arrays.toString(fields));

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
        if (ObjectUtils.isEmpty(excelParams) || ObjectUtils.isEmpty(excelParams.getContentIndex())) {
            i = 1;
        } else {
            i = excelParams.getContentIndex();
        }
        HashMap<String, Object> cellValue = new HashMap<String, Object>();
        for (; i <= rowNum; i++) {
            cellValue = new HashMap<String, Object>();
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

    /************************************************************************
     * @author: wg
     * @description: 适用于直接导入, 不需要replace等操作
     * @params:
     * @return:
     * @createTime: 21:09  2022/3/8
     * @updateTime: 21:09  2022/3/8
     ************************************************************************/
    public static <T> Map<Integer, T> readExcelContent(Workbook workbook,
                                                       String[] titleArray,
                                                       @Nullable ExcelParams excelParams,
                                                       Class<T> tClass) throws Exception {
        Map<Integer, T> contentMap = new HashMap<Integer, T>();

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
        if (ObjectUtils.isEmpty(excelParams) || ObjectUtils.isEmpty(excelParams.getContentIndex())) {
            i = 1;
        } else {
            i = excelParams.getContentIndex();
        }
        for (; i <= rowNum; i++) {
            Map<String, Object> cellValue = new HashMap<String, Object>();
            int j = 0;
            row = sheet.getRow(i);
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                String titleFieldName = titleArray[j];
                cellValue.put(titleFieldName, obj);
                j++;
            }
            T t = JSON.parseObject(JSON.toJSONString(cellValue), tClass);
            contentMap.put(i, t);
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

    /************************************************************************
     * @author: wg
     * @description: 导入时只需要引用这个方法就行
     * @params:
     * @return:
     * @createTime: 14:19  2022/3/9
     * @updateTime: 14:19  2022/3/9
     ************************************************************************/
    public static <T> List<T> getImportList(File file, @Nullable ExcelParams excelParams, T t) throws Exception {
        List<T> list = new ArrayList<>();
        workbook = initWorkbook(file);
        String[] titles = readExcelTitle(excelParams, t);
        Map<Integer, Map<String, Object>> contentMap = readExcelContent(workbook, titles, excelParams);
        Map<String, Map<String, String>> importReplaceMap = getImportReplaceMap(t.getClass());
        contentMap.forEach((lineIndex, objectMap) -> {
            // objectMap(字段名, 单元格的值)
            objectMap.forEach((fieldName, cellValue) -> {
                // replaceMap(字段名, replace = {"INT_1", "EXT_0"})
                importReplaceMap.forEach((fieldName$, replaceValues) -> {
                    if (fieldName.equals(fieldName$)) {
                        replaceValues.forEach((excelVal, tableVal) -> {
                            if (excelVal.equals(cellValue.toString().trim())) {
                                objectMap.put(fieldName, tableVal);
                            }
                        });
                    }
                });
            });
            list.add((T) JSON.parseObject(JSON.toJSONString(objectMap), t.getClass()));
        });

        return list;
    }

    /************************************************************************
     * @author: wg
     * @description: 获取网络传输的excel文件
     * @params:
     * @return:
     * @createTime: 14:32  2022/3/9
     * @updateTime: 14:32  2022/3/9
     ************************************************************************/
    public static <T> List<T> getImportList(MultipartFile file, @Nullable ExcelParams excelParams, T t) throws Exception {
        List<T> list = new ArrayList<>();
        workbook = initWorkbook(file);
        String[] titles = readExcelTitle(excelParams, t);
        Map<Integer, Map<String, Object>> contentMap = readExcelContent(workbook, titles, excelParams);
        Map<String, Map<String, String>> importReplaceMap = getImportReplaceMap(t.getClass());
        contentMap.forEach((lineIndex, objectMap) -> {
            // objectMap(字段名, 单元格的值)
            objectMap.forEach((fieldName, cellValue) -> {
                // replaceMap(字段名, replace = {"INT_1", "EXT_0"})
                importReplaceMap.forEach((fieldName$, replaceValues) -> {
                    if (fieldName.equals(fieldName$)) {
                        replaceValues.forEach((excelVal, tableVal) -> {
                            if (excelVal.equals(cellValue.toString().trim())) {
                                objectMap.put(fieldName, tableVal);
                            }
                        });
                    }
                });
            });
            list.add((T) JSON.parseObject(JSON.toJSONString(objectMap), t.getClass()));
        });

        return list;
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
    public static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";

        DecimalFormat decimalFormat = new DecimalFormat();

        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    short s = cell.getCellStyle().getDataFormat();
                    if (ExcelDateUtil.isCellDateFormatted(cell)) {
                        cellvalue = cell.getDateCellValue();
                    } else {
                        cellvalue = decimalFormat.format(cell.getNumericCellValue()).replace(",", "");
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString().replace(",", "");
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellvalue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellvalue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellvalue = "非法字符";
                    break;
                case Cell.CELL_TYPE_BLANK:
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

    // apache poi 4.1.2
    // public static Object getCellFormatValue(Cell cell) {
    //     Object cellvalue = "";
    //     CellType cellType = cell.getCellType();
    //
    //     DecimalFormat decimalFormat = new DecimalFormat();
    //
    //     if (cellType != null) {
    //         switch (cellType) {
    //             case NUMERIC: {
    //                 short s = cell.getCellStyle().getDataFormat();
    //                 if (ExcelDateUtil.isCellDateFormatted(cell)) {
    //                     Date date = cell.getDateCellValue();
    //                     cellvalue = date;
    //                 } else {
    //                     cellvalue = decimalFormat.format(cell.getNumericCellValue()).replace(",", "");
    //                 }
    //                 break;
    //             }
    //             case STRING:
    //                 cellvalue = cell.getRichStringCellValue().getString().replace(",", "");
    //                 break;
    //             case BOOLEAN:
    //                 cellvalue = String.valueOf(cell.getBooleanCellValue());
    //                 break;
    //             case FORMULA:
    //                 cellvalue = String.valueOf(cell.getCellFormula());
    //                 break;
    //             case ERROR:
    //                 cellvalue = "非法字符";
    //                 break;
    //             case BLANK:
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

    /***************************************************
     * 导出excel
     * @author: wg
     * @time: 2020/4/17 15:01
     ***************************************************/
    public static void export(String data, HttpServletResponse response, Class<T> clazz, ExcelParams excelParams) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Timestamp timestamp = null;
        Date date = null;
        Float aFloat = null;
        String s = null;

        List<T> objectList = new ArrayList<>();
        if (!StringUtils.isEmpty(data)) {
            objectList = JSON.parseArray(data, clazz);
        }

        String fileName = excelParams.getExportFileName();
        String sheetName = excelParams.getExportSheetName();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFRow row = null;
        HSSFCell cell = null;

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        /************ 把所有 带有@Excel 注解的字段拿出来 形成arrayList 以形成表头,并创建单元格写入表头标题 -> 开始 ************/
        Field[] fields = clazz.getDeclaredFields();
        ArrayList<Field> fieldArrayList = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Excel.class)) {
                fieldArrayList.add(field);
            }
        }
        row = sheet.createRow(0);
        for (int i = 0; i < fieldArrayList.size(); i++) {
            cell = row.createCell(i);
            Field field = fieldArrayList.get(i);
            Excel annotation = field.getAnnotation(Excel.class);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(annotation.name());
        }
        /************ 把所有 带有@Excel 注解的字段拿出来 形成arrayList 以形成表头,并创建单元格写入表头标题 -> 结束 ************/

        /************ 导出表的正文 field.get(bankFlow) -> 开始 ************/
        try {
            for (int i = 0; i < objectList.size(); i++) {
                row = sheet.createRow(i + 1);
                T flow = objectList.get(i);
                for (int j = 0; j < fieldArrayList.size(); j++) {
                    cell = row.createCell(j);

                    //if (!StringUtils.isEmpty(flow.getTick()) && flow.getTick().equals("conditionExcel")) {
                    //    cell.setCellStyle(cellStyle);
                    //}

                    Field field = fieldArrayList.get(j);

                    //System.out.println("field.toString(): " + field.toString());

                    field.setAccessible(true);

                    if (field.get(flow) instanceof String) {
                        s = (String) field.get(flow);
                        cell.setCellValue((String) field.get(flow));
                        System.out.println(s);

                    }

                    if (field.get(flow) instanceof Timestamp) {
                        timestamp = (Timestamp) field.get(flow);
                        date = new Date(timestamp.getTime());

                        String format = dateFormat.format(date);
                        cell.setCellValue(format);
                        System.out.println(format);
                    }

                    if (field.get(flow) instanceof Float) {
                        aFloat = (Float) field.get(flow);
                        cell.setCellValue(aFloat.toString());
                        System.out.println(aFloat.toString());
                    }
                }
            }
            /************ 导出表的正文 -> 结束 ************/

            /************ 设置响应header -> 开始  ************/
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            /************ 设置响应header -> 结束  ************/

            /************ 流 -> 开始 ************/
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            /************ 流 -> 结束 ************/
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
