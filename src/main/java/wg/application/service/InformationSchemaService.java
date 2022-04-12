package wg.application.service;


import cn.hutool.core.io.resource.ClassPathResource;
import wg.application.entity.InformationSchema;

import java.util.List;
import java.util.Map;

public interface InformationSchemaService {
    /**
     * 假如数据库中写明了外键关联, 查询 关联关系
     */
    List<InformationSchema> selectInformationSchema(String tableName);

    /**
     * 通过读取 json 文件的形式, 获取数据库中所有表的外键关联
     */
    List<InformationSchema> readJsonOfInformationSchema(String tableName, ClassPathResource classPathResource);

    /**
     * 假如数据库中写明了外键关联, 查询数据库, 获取数据库中所有表的外键关联, 转成 map
     */
    public Map<String, List<InformationSchema>> getAllSchemaMap();

    /**
     * 将数据库中所有表的外键关联的 map 形式 写入 json 文件中
     */
    void writeToJson(Map<String, List<InformationSchema>> allSchema, ClassPathResource classPathResource);

    /**
     * 获取数据库中所有的表名
     */
    public List<String> getAllTablesName(String datasourceName);

    int deleteData(String tableName, String columnName, String columnVal);
}
