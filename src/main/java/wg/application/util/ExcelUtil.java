package wg.application.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import wg.application.entity.BankFlow;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wg
 * @Package wg.application.util
 * @date 2020/4/16 15:50
 * @Copyright
 */
public class ExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public Class<BankFlow> clazz;

    public ExcelUtil(Class<BankFlow> clazz) {
        this.clazz = clazz;
    }


    public void exportExcel(List<BankFlow> list, String sheetName, HttpServletResponse response) {

        OutputStream outputStream = null;
        HSSFWorkbook hssfWorkbook = null;
        try {
            Field[] allFields = clazz.getDeclaredFields();
            ArrayList<Field> fieldArrayList = new ArrayList<>();
            for (Field field : allFields) {
                if (field.isAnnotationPresent(Excel.class)) {
                    fieldArrayList.add(field);
                }
            }

            hssfWorkbook = new HSSFWorkbook();

            HSSFSheet sheet = hssfWorkbook.createSheet(sheetName);
            HSSFRow row;
            HSSFCell cell;

            row = sheet.createRow(0);
            for (int i = 0; i < fieldArrayList.size(); i++) {
                Field field = fieldArrayList.get(i);
                Excel fieldAnnotation = field.getAnnotation(Excel.class);
                cell = row.createCell(i);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(fieldAnnotation.name());

            }

            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                BankFlow bankFlow = list.get(i);
                for (int j = 0; j < fieldArrayList.size(); j++) {
                    Field field = fieldArrayList.get(j);
                    field.setAccessible(true);
                    cell = row.createCell(j);
                    cell.setCellValue(StringUtils.isEmpty(field.get(bankFlow)) ? "" : field.get(bankFlow)+"");
                }


            }


            this.setResponseHeader(response, "");

            outputStream = response.getOutputStream();

            hssfWorkbook.write(outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
