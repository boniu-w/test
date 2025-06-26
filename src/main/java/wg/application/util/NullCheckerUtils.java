package wg.application.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component(value = "nullCheckerUtils")
public class NullCheckerUtils {

    public boolean allFieldsNull(Object obj) {
        if (obj == null) {
            return true;
        }

        Field[] fields = obj.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true); // 允许访问私有字段
                String name = field.getName();
                if ("serialVersionUID".equals(name)) continue;
                Object value = field.get(obj);
                if (value != null) {
                    return false; // 只要有一个字段不为 null
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("无法访问字段", e);
        }

        return true; // 所有字段都为 null
    }
}
