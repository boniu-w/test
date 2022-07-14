package wg.application.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:02 2022/3/8
 * @updateTime: 10:02 2022/3/8
 ************************************************************************/
@Mapper
public interface QueryByTableMapper {

    public void queryBy(String tableName, String field);
    public List<Map> queryBy(String tableName);
}
