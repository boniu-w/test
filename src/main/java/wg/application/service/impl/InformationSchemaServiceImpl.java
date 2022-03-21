package wg.application.service.impl;

import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import wg.application.entity.InformationSchema;
import wg.application.mapper.InformationSchemaMapper;
import wg.application.service.InformationSchemaService;
import wg.application.util.FileUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InformationSchemaServiceImpl implements InformationSchemaService {

    @Resource
    InformationSchemaMapper informationSchemaMapper;

    /************************************************************************
     * @description: 根据表名 查询此表的主键是哪些表的外键
     * @author: wg
     * @date: 13:24  2021/11/2
     * @params:
     * @return:
     ************************************************************************/
    public List<InformationSchema> selectInformationSchema(String tableName) {
        List<InformationSchema> informationSchemas = informationSchemaMapper.selectInformationSchema(tableName);
        informationSchemas.forEach(System.out::println);
        return informationSchemas;
    }

    @Override
    public List<InformationSchema> readJsonOfInformationSchema(String tableName, ClassPathResource classPathResource) {
        ArrayList<InformationSchema> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = FileUtil.readJson(classPathResource);
            JsonNode node = jsonNode.get(tableName);
            for (int i = 0; i < node.size(); i++) {
                InformationSchema informationSchema = objectMapper.convertValue(node.get(i), InformationSchema.class);
                list.add(informationSchema);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取数据中所有的表名
     */
    public List<String> getAllTablesName(String datasourceName) {
        return informationSchemaMapper.getAllTablesName(datasourceName);
    }

    @Override
    public int deleteData(String tableName, String columnName, String columnVal) {

        return informationSchemaMapper.deleteData(tableName, columnName, columnVal);
    }

    public Map<String, List<InformationSchema>> getAllSchemaMap() {
        String datasourceName = "V7104_pipeline-integrity-management-system";
        List<String> tableNames = getAllTablesName(datasourceName);
        Map<String, List<InformationSchema>> map = new HashMap<>();
        for (int i = 0; i < tableNames.size(); i++) {
            List<InformationSchema> informationSchemaList = informationSchemaMapper.selectInformationSchema(tableNames.get(i));
            map.put(tableNames.get(i), informationSchemaList);
        }

        return map;
    }

    public void writeToJson(Map<String, List<InformationSchema>> allSchema, ClassPathResource classPathResource) {
        allSchema.forEach((k, v) -> System.out.println(k + ": " + v));
        try {
            ClassPathResource resource = new ClassPathResource("/json/informationSchema.json");
            FileUtil.writeToJson(allSchema, resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
