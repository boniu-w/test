package wg.application.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wg.application.entity.DutyEntity;
import wg.application.util.ExcelUtil;
import wg.application.vo.Result;
import wg.application.vo.ResultData;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
        // 获取上传的excel数据
        HashMap<Integer, HashMap<Integer, Object>> importedData = this.getImportData(request);
        // 处理excel数据, 形成 list
        ArrayList<DutyEntity> dutyEntities = this.handleData(importedData);
        
        System.out.println(dutyEntities.size() + "  <>  " + dutyEntities);
        // 批量插入数据库
        for (int i = 0; i < dutyEntities.size(); i++) {
        
        }
        
        return ResultData.build().data(importedData);
    }
    
    /****************************************************************
     * 获取上传的excel数据
     * @author: wg
     * @time: 2020/12/21 16:28
     ****************************************************************/
    public HashMap<Integer, HashMap<Integer, Object>> getImportData(HttpServletRequest request) {
        
        List<MultipartFile> uploadFiles = ExcelUtil.getUploadFiles(request);
        
        // 先假定是单文件上传
        for (MultipartFile multipartFile : uploadFiles) {
            
            HashMap<Integer, HashMap<Integer, Object>> map = new HashMap();
            
            Workbook workbook = ExcelUtil.initWorkbook(multipartFile);
            Sheet sheet = workbook.getSheetAt(0);
            
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            int lastRowNum = sheet.getLastRowNum();
            
            System.out.println(physicalNumberOfRows + "   " + lastRowNum);
            for (int i = 0; i < 12; i++) {
                Row row = sheet.getRow(i);
                System.out.println("row.getLastCellNum() -> " + row.getLastCellNum());
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                
                HashMap<Integer, Object> cellMap = new HashMap();
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
    public ArrayList<DutyEntity> handleData(HashMap<Integer, HashMap<Integer, Object>> importedData) {
        try {
            ArrayList<DutyEntity> dutyEntities = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            for (int i = 3; i < 11; i++) {
                HashMap<Integer, Object> hashMap = importedData.get(Integer.valueOf(i));
                
                // 值班日期
                ArrayList<String> dutyDateList = new ArrayList<>();
                for (int j = 0; j < 5; j++) {
                    String dutyDateString = ((String) hashMap.get(Integer.valueOf(j))).trim();
                    if (StringUtils.isNotBlank(dutyDateString)) {
                        String dutyDate = dutyDateString.substring(0, dutyDateString.indexOf("周") - 1).trim();
                        int year = LocalDate.now().getYear();
                        dutyDateList.add(year + "年" + dutyDate);
                    }
                }
                
                // 字符串list,转换为日期list
                Date[] dutyDateArray = new Date[dutyDateList.size()];
                for (int j = 0; j < dutyDateArray.length; j++) {
                    if (StringUtils.isNotEmpty(dutyDateList.get(j))) {
                        dutyDateArray[j] = simpleDateFormat.parse(dutyDateList.get(j));
                    }
                }
                
                
                System.out.println("值班日期 -> " + dutyDateList);
                
                String dutyLeader = (String) hashMap.get(Integer.valueOf(5));
                String onDutyMonitor = (String) hashMap.get(Integer.valueOf(6));
                
                StringBuilder onDutyMembers = new StringBuilder();
                for (int j = 7; j < 13; j++) {
                    String onDutyMember = (String) hashMap.get(Integer.valueOf(j));
                    onDutyMembers.append(onDutyMember);
                    onDutyMembers.append(" ");
                }
                
                System.out.println("总队带班 -> " + dutyLeader);
                System.out.println("值守组 组长 -> " + onDutyMonitor);
                System.out.println("值班组 成员 -> " + onDutyMembers);
                
                String flexibleMonitor = (String) hashMap.get(Integer.valueOf(13));
                StringBuilder flexibleMembers = new StringBuilder();
                for (int j = 14; j < 20; j++) {
                    String flexibleMember = (String) hashMap.get(Integer.valueOf(j));
                    flexibleMembers.append(flexibleMember);
                    flexibleMembers.append(" ");
                }
                
                System.out.println("机动带班长 -> " + flexibleMonitor);
                System.out.println("机动组成员 -> " + flexibleMembers);
                
                DutyEntity dutyEntity = new DutyEntity();
                dutyEntity.setDutyLeader(dutyLeader);
                dutyEntity.setOnDutyMonitor(onDutyMonitor.replace("  ", ","));
                dutyEntity.setOnDutyMember(onDutyMembers.toString().replace("  ", ","));
                dutyEntity.setFlexibleMonitor(flexibleMonitor);
                dutyEntity.setFlexibleMember(flexibleMembers.toString().replace("  ", ","));
                dutyEntity.setDutyDateArray(dutyDateArray);
                
                dutyEntities.add(dutyEntity);
            }
            
            ArrayList<DutyEntity> dutyEntityArrayList = new ArrayList<>();
            for (int i = 0; i < dutyEntities.size(); i++) {
                DutyEntity dutyEntity = dutyEntities.get(i);
                Date[] dutyDateArray = dutyEntity.getDutyDateArray();
                if (dutyDateArray.length > 0) {
                    for (int j = 0; j < dutyDateArray.length; j++) {
                        DutyEntity entity = new DutyEntity();
                        entity.setDutyDate(dutyDateArray[j]);
                        entity.setId(UUID.randomUUID().toString().replace("-", ""));
                        entity.setDutyLeader(dutyEntity.getDutyLeader());
                        entity.setOnDutyMonitor(dutyEntity.getOnDutyMonitor());
                        entity.setOnDutyMember(dutyEntity.getOnDutyMember());
                        entity.setFlexibleMonitor(dutyEntity.getFlexibleMonitor());
                        entity.setFlexibleMember(dutyEntity.getFlexibleMember());
                        dutyEntityArrayList.add(entity);
                    }
                }
            }
            return dutyEntityArrayList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    /************************************************************************
     * @author: wg
     * @description: test
     * @params:
     * @return:
     * @createTime: 16:19  2023/5/10
     * @updateTime: 16:19  2023/5/10
     ************************************************************************/
    @RequestMapping(value = "/$importExcel")
    @ResponseBody
    public ResultData $importExcel(HttpServletRequest request) {
        // 获取上传的excel数据
        HashMap<Integer, HashMap<Integer, Object>> importedData = this.$getImportData(request);
       
        
        return ResultData.build().data(importedData);
    }
    
    public HashMap<Integer, HashMap<Integer, Object>> $getImportData(HttpServletRequest request) {
        List<MultipartFile> uploadFiles = ExcelUtil.getUploadFiles(request);
        
        // 先假定是单文件上传
        for (MultipartFile multipartFile : uploadFiles) {
            HashMap<Integer, HashMap<Integer, Object>> map = new HashMap();
            
            Workbook workbook = ExcelUtil.initWorkbook(multipartFile);
            Sheet sheet = workbook.getSheetAt(0);
            
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            int lastRowNum = sheet.getLastRowNum();
            
            System.out.println(physicalNumberOfRows + "   " + lastRowNum);
            for (int i = 0; i < lastRowNum; i++) {
                Row row = sheet.getRow(i);
                System.out.println("row.getLastCellNum() -> " + row.getLastCellNum());
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                
                HashMap<Integer, Object> cellMap = new HashMap();
                for (int j = 0; j < physicalNumberOfCells; j++) {
                    Cell cell = row.getCell(j);
                    Object cellFormatValue = ExcelUtil.getCellFormatValue(cell);
                    cellMap.put(j, cellFormatValue);
                }
                map.put(i, cellMap);
            }
            return map;
        }
        return new HashMap<>();
    }
    
}
