package wg.application.ioc.factory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BeanFactory {

    private static Properties properties;
    private static Map<String, Object> cache = new HashMap<>();

    static {
        properties = new Properties();
        try {
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("factory.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getDao(String beanName) {
        if (!cache.containsKey(beanName)) {
            synchronized (BeanFactory.class) {
                if (!cache.containsKey(beanName)) {
                    String value = properties.getProperty(beanName);
                    // 通过反射 创建对象
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(value);
                        Object obj = clazz.getConstructor(null).newInstance();
                        cache.put(beanName, obj);
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return cache.get(beanName);
    }
}
