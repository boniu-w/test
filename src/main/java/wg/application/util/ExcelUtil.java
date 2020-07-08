//package wg.application.util;
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.formula.functions.T;
//import org.apache.poi.ss.usermodel.Cell;
//import org.jeecgframework.poi.excel.ExcelImportUtil;
//import org.jeecgframework.poi.excel.entity.ImportParams;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import wg.application.entity.BankFlow;
//import wg.application.vo.Result;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author wg
// * @Package wg.application.util
// * @date 2020/4/16 15:50
// * @Copyright
// */
//public class ExcelUtil<T, S extends IService<T>> {
//    @Autowired
//    S service;
//
//    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
//
//    public Class<BankFlow> clazz;
//
//    public ExcelUtil(Class<BankFlow> clazz) {
//        this.clazz = clazz;
//    }
//
//
//    public void exportExcel(List<BankFlow> list, String sheetName, HttpServletResponse response) {
//
//        OutputStream outputStream = null;
//        HSSFWorkbook hssfWorkbook = null;
//        try {
//            Field[] allFields = clazz.getDeclaredFields();
//            ArrayList<Field> fieldArrayList = new ArrayList<>();
//            for (Field field : allFields) {
//                if (field.isAnnotationPresent(Excel.class)) {
//                    fieldArrayList.add(field);
//                }
//            }
//
//            hssfWorkbook = new HSSFWorkbook();
//
//            HSSFSheet sheet = hssfWorkbook.createSheet(sheetName);
//            HSSFRow row;
//            HSSFCell cell;
//
//            row = sheet.createRow(0);
//            for (int i = 0; i < fieldArrayList.size(); i++) {
//                Field field = fieldArrayList.get(i);
//                Excel fieldAnnotation = field.getAnnotation(Excel.class);
//                cell = row.createCell(i);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                cell.setCellValue(fieldAnnotation.name());
//
//            }
//
//            for (int i = 0; i < list.size(); i++) {
//                row = sheet.createRow(i + 1);
//                BankFlow bankFlow = list.get(i);
//                for (int j = 0; j < fieldArrayList.size(); j++) {
//                    Field field = fieldArrayList.get(j);
//                    field.setAccessible(true);
//                    cell = row.createCell(j);
//                    cell.setCellValue(StringUtils.isEmpty(field.get(bankFlow)) ? "" : field.get(bankFlow) + "");
//                }
//
//
//            }
//
//
//            this.setResponseHeader(response, "");
//
//            outputStream = response.getOutputStream();
//
//            hssfWorkbook.write(outputStream);
//
//            outputStream.flush();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void setResponseHeader(HttpServletResponse response, String fileName) {
//        try {
//            try {
//                fileName = new String(fileName.getBytes(), "ISO8859-1");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//            response.setContentType("application/vnd.ms-excel");
//            response.setCharacterEncoding("utf-8");
//            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//            response.addHeader("Pargam", "no-cache");
//            response.addHeader("Cache-Control", "no-cache");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//
//    /****************************************************************
//     * 导入
//     * @author: wg
//     * @time: 2020/7/2 10:28
//     ****************************************************************/
//    public Result importExcelTest(HttpServletRequest request, HttpServletResponse response, Class<T> clazz, Object obj) {
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//
//        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//            MultipartFile file = entity.getValue();
//            ImportParams params = new ImportParams();
//
//            // 表格标题行数,
//            params.setTitleRows(2);
//
//            // 表头行数
//            params.setHeadRows(1);
//
//            // 是否需要保存上传的Excel,默认为false
//            params.setNeedSave(true);
//
//            try {
//                List<T> list = ExcelImportUtil.importExcel(file.getInputStream(), clazz, params);
//                long start = System.currentTimeMillis();
//
//                service.saveBatch(list);
//
//                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
//
//                return Result.ok("文件导入成功！数据行数：" + list.size());
//
//            } catch (Exception e) {
//                //log.error(e.getMessage(), e);
//                return Result.error("文件导入失败");
//            } finally {
//                try {
//                    file.getInputStream().close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return Result.error("文件导入失败！");
//    }
//
//
//}
