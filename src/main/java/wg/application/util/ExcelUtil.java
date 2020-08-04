package wg.application.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

/*************************************************************
 * @Package com.Gzs.demo.SpringSecurityDemo.Common1.Utils
 * @author wg
 * @date 2020/7/2 10:00
 * @version
 * @Copyright
 *************************************************************/
@Slf4j
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
                    workbook = new HSSFWorkbook(is);
                    return workbook;
                case ".xlsx":
                    workbook = new XSSFWorkbook(is);
                    return workbook;
                case ".et":
                    workbook = new HSSFWorkbook(is);
                    return workbook;
                default:
                    workbook = null;
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("<><><><><>< 初始化 workbook 完成 ><><><><>");
        }

        return new HSSFWorkbook();
    }


    /***************************************************
     * 获取 前端 上传的文件信息
     * @author: wg
     * @time: 2020/4/26 11:38
     ***************************************************/
    public static List<MultipartFile> getFileInfo(HttpServletRequest request) {
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
    public static String[] readExcelTitle(int sheetIndex, Class<T> tClass) {
        if (workbook == null) {
            try {
                throw new Exception("Workbook对象为空！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sheet = workbook.getSheetAt(sheetIndex);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();


        String[] title = new String[colNum];

        Field[] fields = tClass.getDeclaredFields();

        String cellValue = "";
        Excel annotation = null;
        String annotationName = "";
        try {
            for (int i = 0; i < colNum; i++) {
                cellValue = row.getCell(i).getStringCellValue();
                for (int j = 0; j < fields.length; j++) {
                    if (fields[j].isAnnotationPresent(Excel.class)){
                        annotation = fields[j].getAnnotation(Excel.class);
                        annotationName = annotation.name();
                        if (annotationName.equals(cellValue)) {
                            title[i] = fields[j].getName();
                        }
                    }

                }
            }
        } catch (NullPointerException nullPointer) {
            nullPointer.printStackTrace();
        }


        return title;


    }


    /****************************************************************
     * 读取的 excel 内容 应该以 表头对应字段 为键 形成map
     * @author: wg
     * @time: 2020/7/2 14:09
     ****************************************************************/
    public static Map<Integer, Map<String, Object>> readExcelContent(Workbook workbook, String[] titleArray,
                                                                     int sheetIndex) throws Exception {
        Map<Integer, Map<String, Object>> contentMap = new HashMap<Integer, Map<String, Object>>();
        // 得到总行数
        sheet = workbook.getSheetAt(sheetIndex);
        int rowNum = sheet.getLastRowNum();

        // 总列数
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();

        // 默认正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            HashMap<String, Object> cellValue = new HashMap<String, Object>();
            int j = 0;
            row = sheet.getRow(i);
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                cellValue.put(titleArray[j], obj);
                j++;
            }
            contentMap.put(i, cellValue);

        }

        System.out.println(contentMap);
        return contentMap;
    }

    /**
     * 根据Cell类型设置数据
     *
     * @param cell
     */
    public static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";

        DecimalFormat decimalFormat = new DecimalFormat();


        if (cell != null) {

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    short s = cell.getCellStyle().getDataFormat();
                    if (ExcelDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
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

}
