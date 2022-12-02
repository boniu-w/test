package wg.application.util;

import org.apache.commons.lang3.ObjectUtils;

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
}
