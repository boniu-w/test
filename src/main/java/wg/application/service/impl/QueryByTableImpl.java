package wg.application.service.impl;

import org.springframework.stereotype.Service;
import wg.application.mapper.QueryByTableMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:09 2022/3/8
 * @updateTime: 10:09 2022/3/8
 ************************************************************************/
@Service
public class QueryByTableImpl {

    @Resource
    QueryByTableMapper queryByTableMapper;

    public void queryBy(String tableName){
        List<Map> map = queryByTableMapper.queryBy(tableName);
        System.out.println(map);
    }
}
