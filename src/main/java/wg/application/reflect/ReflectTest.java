package wg.application.reflect;

import wg.application.entity.User;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ReflectTest {

    public static void main(String[] args) {
        getGenericity();
    }

    /************************************************************************
     * @description: 根据对象中字段属性值，动态java反射调用相应的get方法
     * @author: wg
     * @date: 13:38  2021/11/3
     * @params:
     * @return:
     ************************************************************************/
    public static void getCodeValue() {
        HashMap<String, Object> hashMap = new HashMap<>();
        Class<User> entityClass = User.class;
        User user = new User();
        user.setAge(12);
        user.setName("lili");

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            hashMap.put(field.getName(), getResult(field.getName(), user));
        }
        hashMap.forEach((k, v) -> System.out.println(k + " " + v));


        // 获取泛型
        Class<? extends HashMap> aClass = hashMap.getClass();
        Type genericSuperclass = aClass.getGenericSuperclass();
        System.out.println("genericSuperclass: " + genericSuperclass); //  java.util.AbstractMap<K, V>

        // java.util.Map<K, V>
        // interface java.lang.Cloneable
        // interface java.io.Serializable
        Type[] genericInterfaces = aClass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            System.out.println(genericInterface);
        }
    }

    public static Object getResult(Object fieldName, User user) {
        Class<? extends User> aClass = user.getClass();
        try {
            Field declaredField = aClass.getDeclaredField(fieldName.toString());
            declaredField.setAccessible(true);
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(declaredField.getName(), aClass);
            Method readMethod = propertyDescriptor.getReadMethod();
            return readMethod.invoke(user);
        } catch (NoSuchFieldException | IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /************************************************************************
     * @author: wg
     * @description: 获取 泛型
     * @params:
     * @return:
     * @createTime: 11:15  2022/9/20
     * @updateTime: 11:15  2022/9/20
     ************************************************************************/
    public static  void getGenericity(){
        ArrayList<String> list = new ArrayList<>();
        list.add("123");

        Class<? extends ArrayList> aClass = list.getClass();
        Type genericSuperclass = aClass.getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        for (Type actualTypeArgument : actualTypeArguments) {
            System.out.println(actualTypeArgument);
        }
    }
}
