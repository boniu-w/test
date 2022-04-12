package wg.application.mapper;

import org.apache.ibatis.annotations.Mapper;
import wg.application.entity.InformationSchema;

import java.util.List;

@Mapper
public interface InformationSchemaMapper {

    List<InformationSchema> selectInformationSchema(String referencedTableName);

    int deleteData(String tableName, String columnName, String columnVal);

    int getDataCount(String tableName, String columnName, String masterTableId);

    List<String> getIds(String tableName, String columnName, String masterTableId);

    List<String> getAllTablesName(String datasourceName);
}
