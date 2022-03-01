package wg.application.util;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TableUtil {

    // @Resource
    // private InformationSchemaDao informationSchemaDao;
    //
    // public static final Set<String> tables = new HashSet<String>();
    //
    // public static TableUtil tableUtil;
    //
    // @PostConstruct
    // public void init() {
    //     tableUtil = this;
    // }
    //
    // /************************************************************************
    //  * @description: 没啥用
    //  * @author: wg
    //  * @date: 16:57  2021/11/17
    //  * @params:
    //  * @return:
    //  ************************************************************************/
    // public static <E> UpdateWrapper<E> setUpdateWrapper(E e, UpdateWrapper<E> updateWrapper) {
    //     Field[] declaredFields = e.getClass().getDeclaredFields();
    //     String tableFieldName = null;
    //     String entityFieldName = null;
    //     for (Field field : declaredFields) {
    //         boolean accessible = field.isAccessible();
    //         field.setAccessible(true);
    //         TableField tableField = field.getAnnotation(TableField.class);
    //         Class<?> type = field.getType();
    //         if (tableField != null) {
    //             tableFieldName = tableField.value();
    //             entityFieldName = field.getName();
    //             if (SevenMeUtil.getter(entityFieldName, e) != null && tableFieldName != null) {
    //                 updateWrapper.set(tableFieldName, SevenMeUtil.getter(entityFieldName, e));
    //             }
    //         }
    //     }
    //     return updateWrapper;
    // }
    //
    // /************************************************************************
    //  * @description: 查询 此表 与之 关联的所有表
    //  * @author: wg
    //  * @date: 14:36  2021/11/26
    //  * @params:
    //  * @return: 获取 所有关联表的 表名
    //  ************************************************************************/
    // public static Set<String> getTable(String tableName) {
    //     List<InformationSchema> informationSchemas = tableUtil.informationSchemaDao.selectInformationSchema(tableName);
    //     if (informationSchemas.size() > 0) {
    //         for (InformationSchema informationSchema : informationSchemas) {
    //             tableName = informationSchema.getTableName();
    //             tables.add(tableName);
    //             getTable(tableName);
    //         }
    //     }
    //     return tables;
    // }
    //
    // /************************************************************************
    //  * @description:
    //  * 外键关联形式
    //  * 获取此id关联的有数据的关联表; 查询 此表 中 某条数据 与之 关联的所有其他表中有数据的表
    //  * @author: wg
    //  * @date: 9:38  2021/12/1
    //  * @params:
    //  * @return:
    //  ************************************************************************/
    // public static List<String> getRelation(String masterTableName, String masterTableId) {
    //     ArrayList<String> tableNames = new ArrayList<>();
    //     String tableName = "";
    //     String columnName = "";
    //     List<InformationSchema> informationSchemas = tableUtil.informationSchemaDao.selectInformationSchema(masterTableName);
    //     for (InformationSchema informationSchema : informationSchemas) {
    //         tableName = informationSchema.getTableName();
    //         columnName = informationSchema.getColumnName();
    //         int size = tableUtil.informationSchemaDao.getDataCount(tableName, columnName, masterTableId);
    //         if (size != 0) {
    //             tableNames.add(tableName);
    //         }
    //     }
    //     return tableNames;
    // }
    //
    // /************************************************************************
    //  * @description:
    //  * json 形式
    //  * 获取此id关联的有数据的关联表; 查询 此表 中 某条数据 与之 关联的所有其他表中有数据的表
    //  * @author: wg
    //  * @date: 10:47  2021/12/1
    //  * @params:
    //  * @return:
    //  ************************************************************************/
    // public static List<String> getInformationSchema(String masterTableName, String masterTableId) {
    //     ArrayList<String> tableNames = new ArrayList<>();
    //     JSONObject informationSchemaJson = getJsonByTableName(masterTableName);
    //     if (informationSchemaJson != null) {
    //         JSONArray tableNameJson = informationSchemaJson.getJSONArray("TABLE_NAME");
    //         String columnName = informationSchemaJson.getStr("COLUMN_NAME");
    //         for (Object tableName : tableNameJson) {
    //             int size = tableUtil.informationSchemaDao.getDataCount(tableName.toString(), columnName, masterTableId);
    //             if (size != 0) {
    //                 tableNames.add(tableName.toString());
    //             }
    //         }
    //         return tableNames;
    //     }
    //     return tableNames;
    // }
    //
    // public static JSONObject getJsonByTableName(String tableName) {
    //     ClassPathResource resource = new ClassPathResource("json/informationSchema.json");
    //     File file = resource.getFile();
    //     if (file.exists()) {
    //         try {
    //             String jsonStr = FileUtils.readFile(new FileInputStream(file));
    //
    //             JSONObject jsonObject = new JSONObject(jsonStr);
    //             String informationSchema = jsonObject.getStr(tableName);
    //
    //             JSONObject informationSchemaJson = new JSONObject(informationSchema);
    //
    //             return informationSchemaJson;
    //         } catch (FileNotFoundException e) {
    //             e.printStackTrace();
    //         }
    //     }
    //     return null;
    // }
    //
    // public static int count;
    // public static int step;
    //
    // /************************************************************************
    //  * @description: 删除 与 主表 关联 的 所有表的 数据 , 感觉 成功了
    //  * @author: wg
    //  * @date: 16:20  2021/11/30
    //  * @params:
    //  * masterTableName = section_layout_history
    //  * masterTableId = section_layout_history.id (9e0e63662d114d15940e0ea4f7a68617)
    //  * @return:
    //  ************************************************************************/
    // public static void deleteAllRelation(String masterTableName, String masterTableId) {
    //     String tableName = "";
    //     String columnName = "";
    //     List<InformationSchema> informationSchemas = tableUtil.informationSchemaDao.selectInformationSchema(masterTableName);
    //     if (informationSchemas.size() > 0) {
    //         for (InformationSchema informationSchema : informationSchemas) {
    //             tableName = informationSchema.getTableName();
    //             columnName = informationSchema.getColumnName();
    //             List<String> ids = tableUtil.informationSchemaDao.getIds(tableName, columnName, masterTableId);
    //             if (ids.size() > 0) {
    //                 List<InformationSchema> schemaList = tableUtil.informationSchemaDao.selectInformationSchema(tableName);
    //                 if (schemaList.size() == 0) {
    //                     count++;
    //                     System.out.println("第 " + count + " 次删除, 删除的是 " + tableName + " 里面的相关数据");
    //                     //  delete from api5792007_detail where corrosion_assessment_history_id = #{masterTableId}
    //                     // tableUtil.informationSchemaDao.deleteData(tableName, columnName, masterTableId);
    //                 } else {
    //                     for (String id : ids) {
    //                         deleteAllRelation(tableName, id);
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
    //
    // public static void deleteAllRelationByStepOrder(String masterTableName, String masterTableId, int stepOrder) {
    //     String tableName = "";
    //     String columnName = "";
    //     List<InformationSchema> informationSchemas = tableUtil.informationSchemaDao.selectInformationSchema(masterTableName);
    //     if (informationSchemas.size() > 0) {
    //         for (InformationSchema informationSchema : informationSchemas) {
    //             tableName = informationSchema.getTableName();
    //             columnName = informationSchema.getColumnName();
    //             List<String> ids = tableUtil.informationSchemaDao.getIds(tableName, columnName, masterTableId);
    //             if (ids.size() > 0) {
    //                 step++;
    //                 if (step == stepOrder) {
    //                     System.out.println("删除, 超过 " + stepOrder + " 次 关联, 给出提示 --- ");
    //                     return;
    //                 }
    //                 List<InformationSchema> schemaList = tableUtil.informationSchemaDao.selectInformationSchema(tableName);
    //                 if (schemaList.size() == 0) {
    //                     count++;
    //                     System.out.println("ByStepOrder 第 " + count + " 次删除, 删除的是 " + tableName + " 里面的相关数据");
    //                     //  delete from api5792007_detail where corrosion_assessment_history_id = #{masterTableId}
    //                     // tableUtil.informationSchemaDao.deleteData(tableName, columnName, masterTableId);
    //                 } else {
    //                     for (String id : ids) {
    //                         deleteAllRelation(tableName, id);
    //                         // deleteAllRelationByStepOrder(tableName, id, stepOrder);
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }

}
