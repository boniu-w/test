package wg.application.util;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 11:27 2022/3/9
 * @updateTime: 11:27 2022/3/9
 ************************************************************************/
public class MapUtil {
    
    /************************************************************************
     * @author: wg
     * @description: 检查map里所有的值是否都为空
     * @params:
     * @return: 如果map里所有的值都为空, return true;
     * @createTime: 11:39  2022/3/9
     * @updateTime: 11:39  2022/3/9
     ************************************************************************/
    public static boolean isAllEmptyValue(Map map) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        map.forEach((k, v) -> {
            if (!ObjectUtils.isEmpty(v)) {
                atomicBoolean.set(false);
            }
        });
        
        if (atomicBoolean.get()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static <T> boolean isEmpty(Map<Long, T> map) {
        return null == map || map.size() == 0;
    }
    
    public static <K, V> Map<K, V> removeSomeKey(Map<K, V> map, final K... keys) {
        for (K key : keys) {
            map.remove(key);
        }
        return map;
    }
    
    public static Map<String, Object> removeSomeKey(Map<String, Object> map, Set<String> keySet) {
        for (String key : keySet) {
            map.remove(key);
        }
        return map;
    }
    
    /************************************************************************
     * @author: wg
     * @description:
     * @params:
     * @return:
     * @createTime: 16:49  2022/9/15
     * @updateTime: 16:49  2022/9/15
     ************************************************************************/
    public static boolean containsSomeKey(Map<String, Object> map, Set<String> keySet) {
        for (String k : keySet) {
            if (map.containsKey(k)) {
                return true;
            }
        }
        
        return false;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 除了 这些 key 还有其他 key
     * @params:
     * @return:
     * @createTime: 16:58  2022/9/15
     * @updateTime: 16:58  2022/9/15
     ************************************************************************/
    public static boolean hasOtherKey(Map<String, Object> map, Set<String> keySet) {
        Map<String, Object> map2 = new HashMap<>(map);
        
        for (String key : keySet) {
            map2.remove(key);
        }
        
        return !map2.isEmpty();
    }
    
    public static boolean containsKey(Map<String, Object> map, Set<String> keySet) {
        for (String s : keySet) {
            if (map.containsKey(s)) return true;
        }
        
        return false;
    }
    
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
                    if (value != null) {
                        if (field.getType() == Date.class && value instanceof String) {
                            if (StringUtil.isNotBlank((String) value)) {
                                // 对于 Date 类型的字段，将字符串转换为 Date 对象
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 根据实际需求选择日期格式
                                Date dateValue = dateFormat.parse((String) value);
                                field.set(obj, dateValue);
                            }
                        } else if (field.getType() == Integer.class && value instanceof String) {
                            if (StringUtil.isNotBlank((String) value) && StringUtil.isNumber(value)) {
                                // 对于 Integer 类型的字段，将字符串转换为 Integer 对象
                                Integer intValue = Integer.parseInt((String) value);
                                field.set(obj, intValue);
                            }
                        } else if (field.getType() == LocalDateTime.class && value instanceof String) {
                            if (StringUtil.isNotBlank((String) value)) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime dateTime = LocalDateTime.parse((String) value, formatter);
                                field.set(obj, dateTime);
                            }
                        } else if (field.getType() == LocalDateTime.class && value instanceof Date) {
                            LocalDateTime localDateTime = DateUtils.toLocalDateTime((Date) value);
                            field.set(obj, localDateTime);
                        } else {
                            field.set(obj, value);
                        }
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | ParseException e) {
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
    
    /************************************************************************
     * @author: wg
     * @description: 层级结构的map 根据key获取 值
     * @params: key = wg.jwt.secret
     * @return:
     * @createTime: 10:57  2023/5/26
     * @updateTime: 10:57  2023/5/26
     ************************************************************************/
    public static Object get(Map<String, Object> hierarchyMap, String key) {
        if (hierarchyMap == null || key == null) {
            return null;
        }
        
        String[] split = key.split("\\.");
        if (!hierarchyMap.containsKey(split[0])) {
            return null;
        }
        
        Object obj = hierarchyMap.get(split[0]);
        if (obj instanceof Map) {
            return get((Map<String, Object>) obj, key.substring(key.indexOf(".") + 1));
        } else {
            return obj;
        }
    }
}
