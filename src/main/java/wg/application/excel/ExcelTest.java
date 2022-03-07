package wg.application.excel;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;
import wg.application.entity.ExcelParams;
import wg.application.util.ExcelUtil;

import java.io.File;
import java.util.Map;

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

            map.forEach((k, v) -> {
                IliDetailExcel iliDetailExcel = JSON.parseObject(JSON.toJSONString(v), IliDetailExcel.class);
                // 添加到数据库
                System.out.println(iliDetailExcel);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
