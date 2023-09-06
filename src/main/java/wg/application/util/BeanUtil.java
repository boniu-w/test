package wg.application.util;

import java.lang.reflect.Field;
import java.util.Map;

/************************************************************************
 * author: wg
 * description: BeanUtil 
 * createTime: 12:54 2023/9/6
 * updateTime: 12:54 2023/9/6
 ************************************************************************/
public class BeanUtil {


    /************************************************************************
     * @author: wg
     * @description: 从 dto里, remove 掉 entity 字段
     * @params:
     * @return:
     * @createTime: 11:20  2023/9/6
     * @updateTime: 11:20  2023/9/6
     ************************************************************************/
    public static <D, E> Map<String, Object> removeEntityFields(D dto, E entity) {
        Map<String, Object> dtoMap = MapUtil.bean2Map(dto);

        if (dtoMap != null) {
            Field[] entityFields = entity.getClass().getDeclaredFields();

            for (Field field : entityFields) {
                dtoMap.remove(field.getName());
            }
        }

        return dtoMap;
    }
}
