package wg.application.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wg.application.entity.ExcelParams;
import wg.application.entity.User;
import wg.application.util.ExcelUtil;
import wg.application.util.FileUtil;
import wg.application.util.MapUtil;
import wg.application.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/excel")
public class ExcelTest {
    
    public static void main(String[] args) {
        // importExcelReplaceTest(null);
        try {
            // getList();
            // testHaiyou();
            List<HaiyouguojiExcel> haiyouguoji = getHaiyouguoji();
            testVelocity(haiyouguoji);
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
    
    /************************************************************************
     * @author: wg
     * @description: 中英文
     * @params:
     * @return:
     * @createTime: 15:52  2023/5/11
     * @updateTime: 15:52  2023/5/11
     ************************************************************************/
    public static void testHaiyou() throws Exception {
        List<HaiyouguojiExcel> haiyouguoji = getHaiyouguoji();
        handlerHaiyou(haiyouguoji);
        // handlerHaiyouAll(haiyouguoji);
    }
    
    /************************************************************************
     * @author: wg
     * @description: 海油国际 -> 监测检验
     * @params:
     * @return:
     * @createTime: 17:58  2023/5/10
     * @updateTime: 17:58  2023/5/10
     ************************************************************************/
    public static List<HaiyouguojiExcel> getHaiyouguoji() throws Exception {
        List<HaiyouguojiExcel> lists = new ArrayList<>();
        
        ArrayList<File> files = new ArrayList<>();
        String sourcePath = "C:\\Users\\wg\\Documents\\海油国际设备设施完整性\\表视图";
        List<File> allFile = FileUtil.getAllFile(sourcePath, files);
        
        for (File file : allFile) {
            List<HaiyouguojiExcel> list = ExcelUtil.getImportList(file, null, new HaiyouguojiExcel());
            lists.addAll(list);
        }
        
        // String path = "static/excel/监测检验.xlsx";
        // for (String inputPath : paths) {
        //     ClassPathResource resource = new ClassPathResource(inputPath);
        //     File file = resource.getFile();
        //     List<HaiyouguojiExcel> list = ExcelUtil.getImportList(file, null, new HaiyouguojiExcel());
        //     lists.addAll(list);
        // }
        
        return lists;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 生成中英文
     * @params:
     * @return:
     * @createTime: 15:50  2023/5/11
     * @updateTime: 15:50  2023/5/11
     ************************************************************************/
    public static void handlerHaiyou(List<HaiyouguojiExcel> list) {
        Map<String, List<HaiyouguojiExcel>> collectMap = list.stream()
                .filter(e -> !StringUtil.isBlank(e.getPageName()) && !e.getPageName().equals("表名"))
                .collect(Collectors.groupingBy(HaiyouguojiExcel::getPageName));
        
        for (Map.Entry<String, List<HaiyouguojiExcel>> entry : collectMap.entrySet()) {
            List<HaiyouguojiExcel> haiyouguojiZhongwenList = entry.getValue();
            HashMap<String, Object> fieldMapEN = new HashMap<>();
            HashMap<String, Object> fieldMapCN = new HashMap<>();
            String fileName = StringUtil.getHumpString(haiyouguojiZhongwenList.get(0).getDataTable());
            for (HaiyouguojiExcel haiyou : haiyouguojiZhongwenList) {
                String fieldCN = haiyou.getField();
                String fieldEn = haiyou.getFieldEn();
                String fieldName = StringUtil.getHumpString(fieldEn);
                
                fieldMapEN.put(fieldName, fieldEn.replace("_", " "));
                fieldMapCN.put(fieldName, fieldCN);
            }
            
            String outPathEN = "C:\\Users\\wg\\Documents\\海油国际设备设施完整性\\ts\\en\\" + fileName + ".ts";
            String outPathENJson = "C:\\Users\\wg\\Documents\\海油国际设备设施完整性\\json\\en\\" + fileName + ".json";
            JSONObject jsonObjectEN = new JSONObject(fieldMapEN);
            FileUtil.jsonbject2jsonFile(jsonObjectEN, outPathEN);
            FileUtil.jsonbject2jsonFile(jsonObjectEN, outPathENJson);
            
            String outPathCN = "C:\\Users\\wg\\Documents\\海油国际设备设施完整性\\ts\\cn\\" + fileName + ".ts";
            String outPathCNJson = "C:\\Users\\wg\\Documents\\海油国际设备设施完整性\\json\\cn\\" + fileName + ".json";
            JSONObject jsonObjectCN = new JSONObject(fieldMapCN);
            FileUtil.jsonbject2jsonFile(jsonObjectCN, outPathCN);
            FileUtil.jsonbject2jsonFile(jsonObjectCN, outPathCNJson);
        }
    }
    
    public static void handlerHaiyouAll(List<HaiyouguojiExcel> list) {
        Map<String, List<HaiyouguojiExcel>> collectMap = list.stream()
                .filter(e -> !StringUtil.isBlank(e.getPageName()) && !e.getPageName().equals("表名"))
                .collect(Collectors.groupingBy(HaiyouguojiExcel::getPageName));
        
        for (Map.Entry<String, List<HaiyouguojiExcel>> entry : collectMap.entrySet()) {
            List<HaiyouguojiExcel> haiyouguojiZhongwenList = entry.getValue();
            String fileName = StringUtil.getHumpString(haiyouguojiZhongwenList.get(0).getDataTable());
            String outPath = "C:\\Users\\wg\\Documents\\海油国际设备设施完整性\\json\\all\\" + fileName + ".json";
            FileUtil.object2file(haiyouguojiZhongwenList, outPath);
        }
    }
    
    public static void testVelocity(List<HaiyouguojiExcel> list) throws IOException {
        Map<String, List<HaiyouguojiExcel>> collectMap = list.stream()
                .filter(e -> !StringUtil.isBlank(e.getPageName()) && !e.getPageName().equals("表名"))
                .collect(Collectors.groupingBy(HaiyouguojiExcel::getPageName));
        
        //1.设置velocity的资源加载类
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //2.加载velocity引擎
        Velocity.init(prop);
        //3.加载velocity容器
        for (Map.Entry<String, List<HaiyouguojiExcel>> entry : collectMap.entrySet()) {
            List<HaiyouguojiExcel> haiyouguojiExcelList = entry.getValue();
            ArrayList<Map> mapArrayList = new ArrayList<>();
            String fileName = StringUtil.getHumpString(haiyouguojiExcelList.get(0).getDataTable());
            for (HaiyouguojiExcel haiyouguojiExcel : haiyouguojiExcelList) {
                haiyouguojiExcel.setFieldEn(StringUtil.getHumpString(haiyouguojiExcel.getFieldEn()));
            }
            VelocityContext velocityContext = new VelocityContext();
            if (haiyouguojiExcelList.size() > 0) {
                velocityContext.put("haiyouguojiExcelList", haiyouguojiExcelList);
                
                for (HaiyouguojiExcel haiyouguojiExcel : haiyouguojiExcelList) {
                    Map<String, Object> map = MapUtil.bean2Map(haiyouguojiExcel);
                    if (map.get("fieldEn").toString().contains("ID")) {
                        map.remove("fieldEn");
                    }
                    mapArrayList.add(map);
                }
                velocityContext.put("mapArrayList", mapArrayList);
            }
            velocityContext.put("filedName", fileName);
            //4.加载velocity模板
            Template template = Velocity.getTemplate("templates/haiyouguoji.ts.vm", "utf-8");
            
            String path = "C:\\Users\\wg\\Documents\\海油国际设备设施完整性\\ts\\demo\\" + fileName + ".ts";
            FileUtil.judgeThePath(path);
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            template.merge(velocityContext, fileWriter);
            //6.释放资源
            fileWriter.close();
        }
        
    }
    
}
