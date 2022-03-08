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
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/excel")
public class ExcelTest {

    public static void main(String[] args) {
        importExcel(null);
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
            String[] excelTitle = ExcelUtil.readExcelTitle(excelParams, new IliDetailExcel());
            Map<Integer, Map<String, Object>> map = ExcelUtil.readExcelContent(workbook, excelTitle, excelParams);

            ArrayList<String> dictList = new ArrayList<>();
            map.forEach((k, v) -> {
                v.forEach((key, value) -> {
                    if ("sex".equals(key)) {
                        switch (value.toString()) {
                            case "男":
                                value = 1;
                                break;
                            case "女":
                                value = 0;
                                break;
                        }
                        v.put(key, value);
                    }
                    // if ("isInternal".equals(key)) {
                    //     if ("INT".equals(value)) {
                    //         value = 1;
                    //     } else {
                    //         value = 0;
                    //     }
                    //     v.put(key, value);
                    // }
                    if ("holiday".equals(key)) {
                        if (dictList.contains(value)) {
                            value = "123";
                        }
                        v.put(key, value);
                    }
                });

                IliDetailExcel iliDetailExcel = JSON.parseObject(JSON.toJSONString(v), IliDetailExcel.class);
                // 添加到数据库
                System.out.println(iliDetailExcel);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/test")
    public void test() {
        importExcel(null);
    }
}
