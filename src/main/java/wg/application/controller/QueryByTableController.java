package wg.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.service.impl.QueryByTableImpl;

import javax.annotation.Resource;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:10 2022/3/8
 * @updateTime: 10:10 2022/3/8
 ************************************************************************/
@RestController
@RequestMapping(value = "/querybytable")
public class QueryByTableController {

    @Resource
    QueryByTableImpl queryByTable;

    @GetMapping(value = "/queryby")
    public void queryBy() {
        String tableName = "user";
        queryByTable.queryBy(tableName);
    }
}
