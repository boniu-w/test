package wg.application;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*****************************************
 * @description:
 * @date: 13:54 2021/6/28
 * @auth: wg
 *****************************************/
@Slf4j
public class QueryGenerator {


    /*****************************************************
     * @params:
     * @description: 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
     * @author: wg
     * @date: 2021/6/28 9:59
     *****************************************************/
    public static <T> QueryWrapper<T> initQueryWrapper(T searchObj, Map<String, String[]> parameterMap) {

        long start = System.currentTimeMillis();
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        installMplus(queryWrapper, searchObj, parameterMap);
        log.debug("---查询条件构造器初始化完成,耗时:" + (System.currentTimeMillis() - start) + "毫秒----");
        return queryWrapper;


    }


    public static void installMplus(QueryWrapper<?> queryWrapper, Object searchObj, Map<String, String[]> parameterMap) {
        PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(searchObj);
        Map<String, Object> ruleMap = getRuleMap();
        String name, type, column;
        for (int i = 0; i < origDescriptors.length; i++) {
            name = origDescriptors[i].getName();
            type = origDescriptors[i].getPropertyType().toString();


            if (!PropertyUtils.isReadable(searchObj, name) || judgedIsUselessField(name)) {
                continue;
            }


        }

        return;
    }

    /*****************************************************
     * @params:
     * @description: 获取请求对应的数据权限规则
     * @author: wg
     * @date: 2021/6/28 13:42
     *****************************************************/
    public static Map<String, Object> getRuleMap() {
        Map<String, Object> ruleMap = new HashMap<String, Object>();
        //获取登录用户信息

        return ruleMap;
    }

    /*****************************************************
     * @params:
     * @description: 是否是无用字段
     * @author: wg
     * @date: 2021/6/28 13:42
     *****************************************************/
    private static boolean judgedIsUselessField(String name) {
        return "class".equals(name) || "ids".equals(name)
                || "page".equals(name) || "rows".equals(name)
                || "sort".equals(name) || "order".equals(name);
    }


    /*****************************************************
     * @params:
     * @description: 获取sql中的#{key} 这个key组成的set
     * @author: wg
     * @date: 2021/6/28 13:58
     *****************************************************/
    public static Set<String> getSqlRuleParams(String sql) {
        if (StringUtils.isEmpty(sql)) {
            return null;
        }
        Set<String> varParams = new HashSet<String>();
        String regex = "\\#\\{\\w+\\}";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String var = m.group();
            varParams.add(var.substring(var.indexOf("{") + 1, var.indexOf("}")));
        }
        return varParams;
    }

}
