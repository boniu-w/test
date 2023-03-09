package wg.application.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
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
     * @description: map 转实体类
     * @params:
     * @return:
     * @createTime: 9:22  2023/3/9
     * @updateTime: 9:22  2023/3/9
     ************************************************************************/
    public static <T> List<T> toObjectList(List<Map<String, Object>> detailList, Class<T> tClass) {
        List<T> excelArrayList = new ArrayList<>();
        // map 转实体类
        if (detailList != null && detailList.size() > 0) {
            T t = null;
            for (Map<String, Object> detailMap : detailList) {
                t = JSON.parseObject(JSON.toJSONString(detailMap), tClass);
                if (t != null) excelArrayList.add(t);
            }
        }

        return excelArrayList;
    }

    /************************************************************************
     * @author: wg
     * @description: 另一种写法
     * @params:
     * @return:
     * @createTime: 9:24  2023/3/9
     * @updateTime: 9:24  2023/3/9
     ************************************************************************/
    // public static <T> List<T> toObjectList(List<Map<String, Object>> detailList, Class<T> tClass) {
    //     List<T> excelArrayList = new ArrayList<>();
    //     // map 转实体类
    //     try {
    //         if (detailList != null && detailList.size() > 0) {
    //             T t = null;
    //             for (Map<String, Object> detailMap : detailList) {
    //                 t = tClass.newInstance();
    //                 BeanUtils.populate(t, detailMap);
    //                 // t = JSON.parseObject(JSON.toJSONString(detailMap), tClass);
    //                 if (t != null) excelArrayList.add(t);
    //             }
    //         }
    //     } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
    //         throw new RuntimeException(e);
    //     }
    //     return excelArrayList;
    // }
}
