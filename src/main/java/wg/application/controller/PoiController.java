package wg.application.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wg.application.util.ExcelUtil;
import wg.application.vo.Result;
import wg.application.vo.ResultData;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/12/21 11:24
 * @version
 * @Copyright
 *************************************************************/
@Controller
@RequestMapping(value = "/poiController")
public class PoiController {


    /****************************************************************
     * 按照市局值班表的格式导入
     * @author: wg
     * @time: 2020/12/21 11:25
     ****************************************************************/
    @RequestMapping(value = "/importExcel")
    @ResponseBody
    public ResultData importExcel(HttpServletRequest request) {

        HashMap importedData = this.getImportData(request);

        return ResultData.build().data(importedData);
    }

    /****************************************************************
     * 获取上传的excel数据
     * @author: wg
     * @time: 2020/12/21 16:28
     ****************************************************************/
    public HashMap getImportData(HttpServletRequest request) {

        List<MultipartFile> uploadFiles = ExcelUtil.getUploadFiles(request);

        // 先假定是单文件上传
        for (MultipartFile multipartFile : uploadFiles) {

            HashMap map = new HashMap();

            Workbook workbook = ExcelUtil.initWorkbook(multipartFile);
            Sheet sheet = workbook.getSheetAt(0);

            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            int lastRowNum = sheet.getLastRowNum();

            System.out.println(physicalNumberOfRows + "   " + lastRowNum);
            for (int i = 0; i < 12; i++) {
                Row row = sheet.getRow(i);
                System.out.println("row.getLastCellNum() -> "+row.getLastCellNum());
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();

                HashMap cellMap = new HashMap();
                for (int j = 0; j < physicalNumberOfCells; j++) {
                    Cell cell = row.getCell(j);
                    Object cellFormatValue = ExcelUtil.getCellFormatValue(cell);
                    cellMap.put(j, cellFormatValue);
                }

                map.put(i, cellMap);

            }

            System.out.println(map);

            return map;
        }

        return new HashMap();
    }


    /****************************************************************
     * 处理数据
     * @author: wg
     * @time: 2020/12/21 16:30
     ****************************************************************/
    public void handleData(HttpServletRequest request){
        HashMap importedData = this.getImportData(request);



    }


}
