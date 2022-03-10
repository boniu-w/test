package wg.application.excel;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wg.application.entity.ExcelParams;
import wg.application.util.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/excel")
public class ExcelTest {

    public static void main(String[] args) {
        // importExcelReplaceTest(null);
        try {
            getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importExcel(MultipartFile file) {
        String path = "static/excel/内检测数据.xlsx";
        try {
            ClassPathResource resource = new ClassPathResource(path);
            File file1 = resource.getFile();
            Workbook workbook = ExcelUtil.initWorkbook(file1);
            ExcelParams excelParams = new ExcelParams();
            excelParams.setTitleIndex(0);
            excelParams.setSheetIndex(0);
            excelParams.setContentIndex(1);

            long currentTimeMillis = System.currentTimeMillis();

            String[] excelTitle = ExcelUtil.readExcelTitle(excelParams, new IliDetailExcel());
            Map<Integer, Map<String, Object>> map = ExcelUtil.readExcelContent(workbook, excelTitle, excelParams);

            long l = System.currentTimeMillis();
            System.out.println("用时: " + (l - currentTimeMillis) + " 毫秒");

            ArrayList<String> dictList = new ArrayList<>();
            map.forEach((lineIndex, objectMap) -> {
                objectMap.forEach((fieldName, fieldValue) -> {
                    if ("sex".equals(fieldName)) {
                        switch (fieldValue.toString()) {
                            case "男":
                                fieldValue = 1;
                                break;
                            case "女":
                                fieldValue = 0;
                                break;
                        }
                        objectMap.put(fieldName, fieldValue);
                    }
                    if ("isInternal".equals(fieldName)) {
                        if ("INT".equals(fieldValue)) {
                            fieldValue = 1;
                        } else {
                            fieldValue = 0;
                        }
                        objectMap.put(fieldName, fieldValue);
                    }
                    if ("holiday".equals(fieldName)) {
                        if (dictList.contains(fieldValue)) {
                            fieldValue = "123";
                        }
                        objectMap.put(fieldName, fieldValue);
                    }
                });

                IliDetailExcel iliDetailExcel = JSON.parseObject(JSON.toJSONString(objectMap), IliDetailExcel.class);
                // 添加到数据库
                System.out.println(iliDetailExcel);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importExcelReplaceTest(MultipartFile file) {
        String path = "static/excel/内检测数据.xlsx";
        try {
            ClassPathResource resource = new ClassPathResource(path);
            File file1 = resource.getFile();
            Workbook workbook = ExcelUtil.initWorkbook(file1);
            ExcelParams excelParams = new ExcelParams();
            excelParams.setTitleIndex(0);
            excelParams.setSheetIndex(0);
            excelParams.setContentIndex(1);

            long currentTimeMillis = System.currentTimeMillis();

            String[] excelTitle = ExcelUtil.readExcelTitle(excelParams, new IliDetailExcel());
            Map<Integer, Map<String, Object>> map = ExcelUtil.readExcelContent(workbook, excelTitle, excelParams);
            Map<String, Map<String, String>> replaceMap = ExcelUtil.getImportReplaceMap(IliDetailExcel.class);

            ArrayList<String> dictList = new ArrayList<>();
            map.forEach((lineIndex, objectMap) -> {
                // objectMap(字段名, 单元格的值)
                objectMap.forEach((fieldName, cellValue) -> {
                    // replaceMap(字段名, replace = {"INT_1", "EXT_0"})
                    replaceMap.forEach((fieldName$, replaceValues) -> {
                        if (fieldName.equals(fieldName$)) {
                            replaceValues.forEach((excelVal, tableVal) -> {
                                if (excelVal.equals(cellValue.toString().trim())) {
                                    objectMap.put(fieldName, tableVal);
                                }
                            });
                        }
                    });
                });

                IliDetailExcel iliDetailExcel = JSON.parseObject(JSON.toJSONString(objectMap), IliDetailExcel.class);

                // 添加到数据库
                System.out.println(iliDetailExcel);
            });
            long l = System.currentTimeMillis();
            System.out.println("用时: " + (l - currentTimeMillis) + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getList() throws Exception {
        String path = "static/excel/内检测数据.xlsx";
        ClassPathResource resource = new ClassPathResource(path);
        File file = resource.getFile();
        List<IliDetailExcel> list = ExcelUtil.getImportList(file, null, new IliDetailExcel());
        list.forEach(System.out::println);
    }

    @GetMapping(value = "/test")
    public void test() {
        importExcel(null);
    }
}
