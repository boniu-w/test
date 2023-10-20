package wg.application.util;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
                                String standardLocalDateStr = StringUtil.toStandardLocalDateStr((String) value);
                                if (DateUtil.isValidDate(standardLocalDateStr)) {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    LocalDate localDate = LocalDate.parse(standardLocalDateStr, formatter);
                                    LocalDateTime localDateTime = DateUtil.toLocalDateTime(localDate);
                                    field.set(obj, localDateTime);
                                }
                                if (DateUtil.isValidDateTime(standardLocalDateStr)) {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    LocalDateTime dateTime = LocalDateTime.parse(standardLocalDateStr, formatter);
                                    field.set(obj, dateTime);
                                }
                            }
                        } else if (field.getType() == LocalDateTime.class && value instanceof Date) {
                            LocalDateTime localDateTime = DateUtil.toLocalDateTime((Date) value);
                            field.set(obj, localDateTime);
                        } else if (field.getType() == BigDecimal.class && value instanceof String) {
                            if (StringUtil.isNotBlank((String) value)) {
                                if (StringUtil.isNumber(value)) {
                                    BigDecimal bigDecimal = new BigDecimal((String) value);
                                    field.set(obj, bigDecimal);
                                }
                            }
                        } else if (field.getType() == LocalDate.class && value instanceof LocalDateTime) {
                            LocalDateTime localDateTime = (LocalDateTime) value;
                            LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
                            field.set(obj, localDate);
                        } else if (field.getType() == LocalDate.class && value instanceof Date) {
                            LocalDateTime localDateTime = DateUtil.toLocalDateTime((Date) value);
                            LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
                            field.set(obj, localDate);
                        } else if (field.getType() == LocalDate.class && value instanceof String) {
                            if (StringUtil.isNotBlank((String) value)) {
                                LocalDateTime localDateTime = DateUtil.toLocalDateTime((String) value, "yyyy-MM-dd");
                                if (localDateTime != null) {
                                    LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
                                    field.set(obj, localDate);
                                }
                            }
                        } else if (field.getType() == String.class && value instanceof Date) {
                            String patternString = DateUtil.format((Date) value, null);
                            field.set(obj, patternString);
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

    /**
     * @author: wg
     * @description: 实体类 转 map, 忽略 null 值
     * @params:
     * @return:
     * @createTime: 13:36  2023/9/5
     * @updateTime: 13:36  2023/9/5
     */
    public static <T> Map<String, Object> bean2MapIgnoreNullValue(T obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] fields = obj.getClass().getDeclaredFields(); // 获取实体类的全部成员变量
            for (Field field : fields) {
                field.setAccessible(true); // 设置字段可访问以便取值
                if (field.get(obj) != null) {
                    map.put(field.getName(), field.get(obj)); // 将实体类对象的成员变量的键值对放入 Map 中
                }
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


    /************************************************************************
     * @author: wg
     * @description: remove 掉 entity 字段
     * @params:
     * @return:
     * @createTime: 11:20  2023/9/6
     * @updateTime: 11:20  2023/9/6
     ************************************************************************/
    public static <D, E> Map<String, Object> removeEntityFields(D dto, E entity) {
        Map<String, Object> dtoMap = bean2MapIgnoreNullValue(dto);

        if (dtoMap != null) {
            Field[] entityFields = entity.getClass().getDeclaredFields();

            for (Field field : entityFields) {
                dtoMap.remove(field.getName());
            }
        }

        return dtoMap;
    }

    public static <E> Map<String, Object> removeEntityFields(Map<String, Object> dtoMap, Class<E> entityClass) {
        if (dtoMap != null) {
            Field[] entityFields = entityClass.getDeclaredFields();

            for (Field field : entityFields) {
                dtoMap.remove(field.getName());
            }
        }

        return dtoMap;
    }


    /************************************************************************
     * @author: wg
     * @description: 把map里, 含有entity字段组成entityfieldmap
     * @params:
     * @return:
     * @createTime: 10:06  2023/10/20
     * @updateTime: 10:06  2023/10/20
     ************************************************************************/
    public static <E> Map<String, Object> separateEntityFields(Map<String, Object> dtoMap, Class<E> entityClass) {
        Map<String, Object> map = new HashMap<>();
        if (dtoMap != null) {
            Field[] entityFields = entityClass.getDeclaredFields();

            for (Field field : entityFields) {
                map.put(field.getName(), dtoMap.get(field.getName()));
            }
        }

        return map;
    }
}
