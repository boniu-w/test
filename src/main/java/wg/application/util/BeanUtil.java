package wg.application.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
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

    /************************************************************************
     * @author: wg
     * @description: 对象及所有属性为null
     * @params:
     * @return:
     * @createTime: 9:46  2023/11/3
     * @updateTime: 9:46  2023/11/3
     ************************************************************************/
    public static boolean isNullFields(Object object) {
        if (object == null) return true;
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        boolean flag = true;
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }

}
