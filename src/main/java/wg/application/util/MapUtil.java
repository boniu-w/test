package wg.application.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
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
}
