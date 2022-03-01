//package wg.application.util;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.internet.http.HttpServletRequest;
//import javax.internet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Field;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///*************************************************************
// * @Package com.Gzs.demo.SpringSecurityDemo.Controller
// * @author wg
// * @date 2020/7/2 10:43
// * @version
// * @Copyright
// *************************************************************/
//
//public class ExcelOperation<T, S extends IService<T>> {
//
//    @Autowired
//    S service;
//
//
//    /****************************************************************
//     * 导入
//     * @author: wg
//     * @time: 2020/7/3 10:21
//     ****************************************************************/
//    public Map<String, Object> importExcel(HttpServletRequest request, HttpServletResponse response, Class<T> tClass) {
//        try {
//            boolean save = false;
//            List<MultipartFile> files = ExcelUtil.getFileInfo(request);
//
//            for (MultipartFile file : files) {
//                Workbook workbook = ExcelUtil.initWorkbook(file);
//
//                // 表头
//                String[] strings = ExcelUtil.readExcelTitle(0, (Class<org.apache.poi.ss.formula.functions.T>) tClass);
//                //System.out.println("><><><> 表头   " + Arrays.toString(strings));
//
//                Map<Integer, Map<String, Object>> integerMapMap = ExcelUtil.readExcelContent(workbook, strings, 0);
//
//                for (Map<String, Object> map : integerMapMap.values()) {
//
//                    /* 如需操作excel 的内容,就修改map */
//                    map.put("create_by", "d8d32768a3584089945a7ef9bf4efe5e");
//                    map.put("createTime", new Date(System.currentTimeMillis()));
//
//                    T t = JSON.parseObject(JSON.toJSONString(map), tClass);
//
//                    save = service.save(t);
//
//                    if (save == false) {
//                        return ResultVO.result(ResultEnum.FAILURE, false);
//                    }
//                }
//            }
//
//            if (save == true) {
//                return ResultVO.result(ResultEnum.SUCCESS, true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ResultVO.result(ResultEnum.FAILURE, false);
//    }
//
//
//    /****************************************************************
//     * 导出
//     * 有数据时,导出数据,无数据时 只有标题  相当于模板
//     * @author: wg
//     * @time: 2020/7/3 10:21
//     ****************************************************************/
//    public void exportToExcel(List<T> tList,
//                              Class<T> tClass,
//                              String fileName,
//                              HttpServletResponse response) {
//
//
//        if (tList == null) {
//            tList = new ArrayList<>();
//        }
//
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet();
//        HSSFRow row = null;
//        HSSFCell cell = null;
//        Timestamp timestamp = null;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//        Date date = null;
//        OutputStream outputStream = null;
//
//        /************ 找出 T 里面 有@Excel 注解的字段 -> 开始 ************/
//        ArrayList<Field> fieldArrayList = new ArrayList<>();
//
//        Field[] fields = tClass.getDeclaredFields();
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(Excel.class)) {
//                fieldArrayList.add(field);
//            }
//        }
//        /************ 找出 T 里面 有@Excel 注解的字段 -> 结束 ************/
//
//        /************ 创建单元格 ,把 表头写入 -> 开始 ************/
//
//        row = sheet.createRow(0);
//
//        for (int i = 0; i < fieldArrayList.size(); i++) {
//            cell = row.createCell(i);
//            Field field = fieldArrayList.get(i);
//            Excel annotation = field.getAnnotation(Excel.class);
//            cell.setCellType(Cell.CELL_TYPE_STRING);
//            cell.setCellValue(annotation.name());
//        }
//        /************ 创建单元格 ,把 表头写入 -> 结束 ************/
//
//
//        /************ 正文 内容 -> 开始 ************/
//        try {
//            for (int i = 0; i < tList.size(); i++) {
//                row = sheet.createRow(i + 1);
//                T t = tList.get(i);
//                for (int j = 0; j < fieldArrayList.size(); j++) {
//
//                    cell = row.createCell(j);
//
//                    Field field = fieldArrayList.get(j);
//                    field.setAccessible(true);
//                    Object obj = field.get(t);
//
//
//                    if (obj instanceof String) {
//                        cell.setCellValue((String) obj);
//                    } else if (obj instanceof Timestamp) {
//                        timestamp = (Timestamp) obj;
//                        date = new Date(timestamp.getTime());
//                        cell.setCellValue(dateFormat.format(date));
//                    } else if (obj instanceof Float) {
//                        cell.setCellValue((Float) obj);
//                    } else if (obj instanceof Date) {
//                        cell.setCellValue(dateFormat.format((Date) obj));
//                    } else if (obj == null) {
//                        cell.setCellValue("");
//                    } else {
//                        cell.setCellValue(obj.toString());
//
//                    }
//
//
//                }
//
//            }
//            /************ 正文 内容 -> 结束 ************/
//
//
//            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
//                fileName = fileName + ".xls";
//            }
//
//            setResponseHeader(response, fileName);
//
//            /************ 流 -> 开始 ************/
//            outputStream = response.getOutputStream();
//            workbook.write(outputStream);
//
//            /*执行 close() 会异常, 可以不用关闭,这里不会因为outputStream 输出对象没有关闭而占用资源的
//                    第二种: 让方法exportToExcel, controller层也返回 void ; 第三种: 不调用setResponseHeader(response, fileName); */
//            //outputStream.close();
//
//            /************ 流 -> 结束 ************/
//
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //return ResultVO.result(ResultEnum.SUCCESS, true);
//
//    }
//
//
//    /****************************************************************
//     * 设置响应头
//     * @author: wg
//     * @time: 2020/7/3 10:43
//     ****************************************************************/
//    public void setResponseHeader(HttpServletResponse response, String fileName) {
//        try {
//            try {
//                fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
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
//}
