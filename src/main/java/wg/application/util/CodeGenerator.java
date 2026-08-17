package wg.application.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class CodeGenerator {

    /**
     * 生成Controller代码
     *
     * @param dtos       DTO类名数组
     * @param services   Service类名数组
     * @param outputPath 输出路径
     * @param fileName   文件名
     * @throws IOException
     */
    public static void generateControllerCode(String[] dtos, String[] services, String[] es, String[] excels,
                                              String outputPath, String fileName) throws IOException {
        // 1. 设置velocity的资源加载类
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", ClasspathResourceLoader.class.getName());

        // 2. 加载velocity引擎
        Velocity.init(prop);

        // 3. 准备数据，加载到velocity容器
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("dtos", dtos);
        velocityContext.put("services", services);
        velocityContext.put("es", es);
        velocityContext.put("excels", excels);

        // 可选：添加其他配置参数
        velocityContext.put("importSuccessMsg", "预防性维修和故障性维修工单数量比 导入成功");
        velocityContext.put("importErrorMsg", "预防性维修和故障性维修工单数量比 导入出错");
        velocityContext.put("excelType", "uga");
        velocityContext.put("excelContentType", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // 4. 加载velocity模板
        Template template = Velocity.getTemplate("templates/ugakpi.vm", "utf-8");

        // 5. 合并数据生成文件
        File file = new File(outputPath, fileName);
        // 确保目录存在
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            template.merge(velocityContext, fileWriter);
        }

        System.out.println("代码已生成: " + file.getAbsolutePath());
    }

    /**
     * 生成Controller代码（使用默认路径）
     */
    public static void generateControllerCode(String[] dtos, String[] services, String[] enties, String[] excels) throws IOException {
        // 默认输出路径
        String outputPath = "E:\\下载";
        String fileName = "GeneratedController.java";
        generateControllerCode(dtos, services, enties, excels, outputPath, fileName);
    }

    /**
     * 批量生成多个Controller代码
     */
    public static void generateMultipleControllers(String[] dtosList, String[] servicesList,
                                                   String[] enties, String[] excels,
                                                   String outputPath) throws IOException {
        for (int i = 0; i < dtosList.length && i < servicesList.length; i++) {
            String fileName = "Controller" + (i + 1) + ".java";
            generateControllerCode(dtosList, servicesList, enties, excels, outputPath, fileName);
        }
    }

    public static void main(String[] args) throws IOException {
        // 示例：配置DTO数组和Service数组
        // String[] dtos = {
        //         "CompletionRateOfCmWoDTO",
        //         "EquipmentMaintenanceDTO",
        //         "InspectionRecordDTO"
        // };
        //
        // String[] services = {
        //         "completionRateOfCmWoService",
        //         "equipmentMaintenanceService",
        //         "inspectionRecordService"
        // };
        //
        // String[] entities = {
        //         "CompletionRateOfCmWoEntity",
        //         "equipmentMaintenanceService",
        //         "inspectionRecordService"
        // };
        //
        // String[] excels = {
        //         "CompletionRateOfCmWoExcel",
        //         "equipmentMaintenanceService",
        //         "inspectionRecordService"
        // };

        // 方式1：使用默认路径生成
        // generateControllerCode(dtos, services, entities, excels);

        // 方式2：指定路径生成
        // generateControllerCode(dtos, services, "D:\\project\\src\\main\\java\\com\\controller\\", "MyController.java");

        // 方式3：批量生成多个Controller
        String[] dtosList = {
                "CompletionRateOfPmWoDTO", "ComplianceRateOfRegulatoryPmDTO", "ComplianceRateOfHsePmDTO", "BacklogOfUnplannedWoDTO",
                "CostRatioOfPmCmDTO", "CompletionRateOfWeeklyMaintenanceDTO"
        };
        String[] servicesList = {
                "completionRateOfPmWoService", "complianceRateOfRegulatoryPmService", "complianceRateOfHsePmExcelService", "backlogOfUnplannedWoService",
                "costRatioOfPmCmService", "completionRateOfWeeklyMaintenanceService"
        };
        String[] entityList = {
                "CompletionRateOfPmWoEntity", "ComplianceRateOfRegulatoryPmEntity", "ComplianceRateOfHsePmEntity", "BacklogOfUnplannedWoEntity",
                "CostRatioOfPmCmEntity", "CompletionRateOfWeeklyMaintenanceEntity"
        };

        String[] excelList = {
                "CompletionRateOfPmWoExcel", "ComplianceRateOfRegulatoryPmExcel", "ComplianceRateOfHsePmExcel", "BacklogOfUnplannedWoExcel",
                "CostRatioOfPmCmExcel", "CompletionRateOfWeeklyMaintenanceExcel"
        };

        // generateMultipleControllers(dtosList, servicesList, entityList, excelList, "E:\\下载\\");
        generateControllerCode(dtosList, servicesList, entityList, excelList);
    }
}
