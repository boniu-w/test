package wg.application.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/************************************************************************
 * author: wg
 * description: ConvertUtil 
 * createTime: 15:05 2023/6/1
 * updateTime: 15:05 2023/6/1
 ************************************************************************/
public class ConvertUtil {
    
    /************************************************************************
     * @author: wg
     * @description: map 转 实体类
     * @params:
     * @return:
     * @createTime: 15:05  2023/6/1
     * @updateTime: 15:05  2023/6/1
     ************************************************************************/
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz) {
        T obj;
        try {
            obj = clazz.newInstance(); // 创建实体类对象
            Field[] fields = clazz.getDeclaredFields(); // 获取实体类的全部成员变量
            for (Field field : fields) {
                field.setAccessible(true); // 设置字段可访问以便修改值
                if (map.containsKey(field.getName())) { // 找到对应的键
                    Object value = map.get(field.getName());
                    field.set(obj, value); // 设置实体类对象的成员变量的值
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 实体类 转 map
     * @params:
     * @return:
     * @createTime: 15:11  2023/6/1
     * @updateTime: 15:11  2023/6/1
     ************************************************************************/
    public static <T> Map<String, Object> bean2Map(T obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] fields = obj.getClass().getDeclaredFields(); // 获取实体类的全部成员变量
            for (Field field : fields) {
                field.setAccessible(true); // 设置字段可访问以便取值
                map.put(field.getName(), field.get(obj)); // 将实体类对象的成员变量的键值对放入 Map 中
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }
}
