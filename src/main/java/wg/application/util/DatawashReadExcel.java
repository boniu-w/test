package wg.application.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wg
 * @Package org.jeecg.modules.demo.datawash.util
 * @date 2020/1/8 17:34
 * @Copyright
 */
public class DatawashReadExcel {


    private static Logger logger = LoggerFactory.getLogger(DatawashReadExcel.class);
    private static Workbook workbook;
    private static Sheet sheet;
    private static Row row;
    private int sheetQuantities;

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
        }

        return new HSSFWorkbook();
    }

    /**
     * 读取Excel表格表头的内容
     *
     * @param n sheet 的 index
     * @return String 表头内容的数组
     */
    public static String[] readExcelTitle(int n) {
        if (workbook == null) {
            try {
                throw new Exception("Workbook对象为空！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sheet = workbook.getSheetAt(n);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();

        //System.out.println("colNum: " + colNum);

        String[] title = new String[colNum];

        try {
            for (int i = 0; i < colNum; i++) {
                title[i] = row.getCell(i).getStringCellValue();
                //System.out.println(Arrays.toString(title));

            }
        } catch (NullPointerException nullPointer) {
            nullPointer.printStackTrace();
        }


        return title;


    }

    /**
     * 读取的 excel 内容 应该以 表头对应字段 为键 形成map
     *
     * @param titleArray 表头数组
     * @param sheetIndex sheet 的 index
     */
    public static Map<Integer, Map<String, Object>> readExcelContent(Workbook workbook, String[] titleArray, int sheetIndex) throws Exception {
        Map<Integer, Map<String, Object>> contentMap = new HashMap<Integer, Map<String, Object>>();
        // 得到总行数
        sheet = workbook.getSheetAt(sheetIndex);
        int rowNum = sheet.getLastRowNum();

        // 总列数
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();

        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            HashMap<String, Object> cellValue = new HashMap<String, Object>();
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                cellValue.put(titleArray[j], obj);
                j++;
            }
            contentMap.put(i, cellValue);

        }
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


    public int getSheetQuantities() {
        return sheetQuantities;
    }
}
