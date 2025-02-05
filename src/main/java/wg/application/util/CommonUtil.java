package wg.application.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import wg.application.entity.User;

import javax.annotation.PostConstruct;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    private static CommonUtil commonUtil;

    @PostConstruct
    public void initCommonUtil() {
        System.out.println(" >>>>>>>>>>>  commonutil init  <<<<<<<<<<<");
        commonUtil = this;
    }

    /************************************************************************
     * @description: 把 Object 转成 target 类型
     * @author: wg
     * @date: 15:35  2021/11/8
     * @params:
     * @return:
     ************************************************************************/
    public static <T> T sourceToTarget(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            logger.error("convert error ", e);
        }

        return targetObject;
    }

    /************************************************************************
     * @description: 把集合的泛型 转成 target 类型
     * @author: wg
     * @date: 15:33  2021/11/8
     * @params:
     * @return:
     ************************************************************************/
    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }

        List<T> targetList = new ArrayList<>(sourceList.size());
        try {
            for (Object source : sourceList) {
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        } catch (Exception e) {
            logger.error("convert error ", e);
        }

        return targetList;
    }

    /************************************************************************
     * @description: 驼峰转下划线
     * @author:
     * @date: 11:22  2021/9/1
     ************************************************************************/
    public static String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    /************************************************************************
     * @description: 下划线转驼峰
     * @author: wg
     * @date: 13:51  2021/11/11
     * @params:
     * @return:
     ************************************************************************/
    public static String getHumpString(String str) {
        String[] s = str.split("_");
        StringBuilder stringBuilder = new StringBuilder(s[0]);
        for (int k = 0; k < s.length - 1; k++) {
            stringBuilder.append(s[k + 1].substring(0, 1).toUpperCase()).append(s[k + 1].substring(1));
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String[] s = subStringByFixedLength("0123456", 6);
        System.out.println(Arrays.toString(s));

        test();
    }

    public static void test() {
        String s1 = double2ScientificNotation(5.720970255000001E-6);
        System.out.println(s1); // 5.721E-6

        String s2 = double2ScientificNotation(1);
        System.out.println(s2); // 1.000E+00
    }

    /************************************************************************
     * @description: 转成科学计数法
     * @author: wg
     * @date: 16:04  2021/11/11
     * @params:
     * @return:
     ************************************************************************/
    public static String double2ScientificNotation(double num) {
        String str = String.format("%E", num);//获取直接格式化结果
        str = str.replace("E-0", "E-");//将E-0N处理为E-N
        //处理结果
        String temp = str.substring(0, str.indexOf("E"));
        //精确到小数点后3位
        String f = String.format("%.3f", Double.parseDouble(temp));
        str = f + str.substring(str.indexOf("E"));
        return str;
    }

    /************************************************************************
     * @description:
     * 转全角的方法(SBC case) 半角转全角
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * @author: wg
     * @date: 11:09  2021/11/12
     * @params: 任意字符串
     * @return: 全角字符串
     ************************************************************************/
    public static String toFullAngle(String input) {
        //半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /************************************************************************
     * @description: 任意字符串 转 半角
     * @author: wg
     * @date: 11:11  2021/11/12
     * @params:
     * @return:
     ************************************************************************/
    public static String toHalfAngle(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /************************************************************************
     * @description: PropertyDescriptor PropertyUtils
     * @author: wg
     * @date: 18:14  2021/11/17
     * @params:
     * @return:
     ************************************************************************/
    public static void test1(Class<?> aclazz) {
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(aclazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            System.out.println(propertyDescriptor);
            propertyDescriptor.getName();
        }

        Class<?> superclass = aclazz.getSuperclass();
        Field[] fields = superclass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("超类的字段: " + field);
        }


        // 能获取 超类 的私有字段, 但 不能获取超类的私有字段的注解
        Field[] declaredFields = aclazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
            // Excel annotation = declaredField.getAnnotation(Excel.class);
            // String name = annotation.name();
            // System.out.println(name);
            // String value = annotation.value();
            // System.out.println(value);

            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(declaredField.getName(), aclazz);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        }
    }

    /************************************************************************
     * @description: 按 指定长度 分割字符串
     * @author: wg
     * @date: 14:47  2021/11/19
     * @params:
     * @return:
     ************************************************************************/
    public static String[] subStringByFixedLength(String str, int len) {
        if (str.length() <= len) {
            return new String[]{str};
        }

        String halfAngle = toHalfAngle(str);
        char[] chars = halfAngle.toCharArray();
        int i = chars.length % len == 0 ? chars.length / len : chars.length / len + 1;
        String[] targetString = new String[i];
        String substring = null;
        for (int j = 0; j < i; ) {
            if ((j + 1) * len > chars.length) {
                substring = halfAngle.substring(j * len, chars.length);
                targetString[j] = substring;
                break;
            }
            substring = halfAngle.substring(j * len, (j + 1) * len); // 012345
            targetString[j] = substring;
            j++;
        }

        return targetString;
    }

    /************************************************************************
     * @description: 每行显示 指定个数的字符串
     * @author: wg
     * @date: 9:57  2021/11/26
     * @params:
     * @return:
     ************************************************************************/
    public static String[] arraySplitOutput(String[] strs, int m) {
        int len = strs.length;
        int line = len / m;
        for (int i = 0; i < line; i++) {
            for (int j = m * i; j < m * (i + 1); j++) {
                System.out.print(String.format("%-10s", strs[j]));
            }
            System.out.println();
        }
        for (int i = m * line; i < len; i++) {
            System.out.print(String.format("%-10s", strs[i]));
        }
        return null;
    }

    /************************************************************************
     * @description:
     * .isInstance() : a 能否 强转为 b
     * instanceof : a的值 是不是 B 这种类型
     * @author: wg
     * @date: 10:40  2021/12/3
     * @params:
     * @return:
     ************************************************************************/
    public static <T> Object instanceTest(Class<T> t, T t1) {
        if (t.isInstance(User.class)) {
            System.out.println("user");
            return new User();
        }
        if (t.isInstance(new User())) {
            System.out.println("-------");
        }

        if (t1 instanceof String) {
            System.out.println("string");
        } else if (t1 instanceof Optional) {
            System.out.println("optional");
        } else if (t1 instanceof User) {
            System.out.println("user");
        }

        if (t1 instanceof org.apache.el.stream.Optional) {
            System.out.println("stream optional");
        }
        if (t1 instanceof Class) {
            System.out.println("class");
        }

        return null;
    }

    /************************************************************************
     * @description: 反射 根据字段名, 执行其 get 方法
     * @author: wg
     * @date: 14:27  2021/11/3
     * @params:
     * @return: 返回 get 到的值
     ************************************************************************/
    public static <T> Object getter(String fieldName, T entity) {
        Class<?> aClass = entity.getClass();
        try {
            Field declaredField = aClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(declaredField.getName(), aClass);
            Method readMethod = propertyDescriptor.getReadMethod();
            return readMethod.invoke(entity);
        } catch (NoSuchFieldException | IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /************************************************************************
     * @description: 通过反射 set 值
     * @author: wg
     * @date: 13:32  2021/11/8
     * @params:
     * @return:
     ************************************************************************/
    public static <T> void setter(T t, String fieldName, Object attributeValue) {
        try {
            PropertyDescriptor p = new PropertyDescriptor(fieldName, t.getClass());
            Method writeMethod = p.getWriteMethod();
            writeMethod.invoke(t, attributeValue);
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException introspectionException) {
            introspectionException.printStackTrace();
        }
    }

    /************************************************************************
     * @description:
     * hashMap 的 key 是 字段名 , value 是 要 赋的值
     * @author: wg
     * @date: 14:30  2021/11/10
     * @params:
     * @return:
     ************************************************************************/
    public static <T> void setter(T t, Map<String, Object> hashMap) {
        hashMap.forEach((k, v) -> {
            PropertyDescriptor p = null;
            try {
                p = new PropertyDescriptor(k, t.getClass());
                Method writeMethod = p.getWriteMethod();
                writeMethod.invoke(t, v);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    /************************************************************************
     * @description: 计算 list 里 的某个 项的 和
     * map 键相同 值求和
     * @author: wg
     * @date: 16:51  2021/12/8
     * @params:
     * @return:
     ************************************************************************/
    public static Map<Integer, Long> calculateSum(ArrayList<Map<Integer, Long>> list) {
        Map<Integer, Long> sumMap = new HashMap<>();
        list.stream().map(item -> {
            item.forEach((k, v) -> {
                Long aLong = sumMap.get(k);
                if (aLong == null) {
                    sumMap.put(k, v);
                } else {
                    aLong += v;
                    sumMap.put(k, aLong);
                }
            });
            return sumMap;
        }).collect(Collectors.toList());
        return sumMap;
    }

    /************************************************************************
     * @description: 判断是否是数字
     * @author: wg
     * @date: 15:50  2021/12/14
     * @params:
     * @return:
     ************************************************************************/
    public static boolean isNumber(String val) {
        if (null == val || "".equals(val)) {
            return false;
        }

        String rex = "^[+-]?\\d*\\.?\\d*$";
        boolean numbMatch = Pattern.matches(rex, val);
        if (numbMatch) {
            return numbMatch;
        }

        rex = "^[+-]?\\d+\\.?\\d*[Ee]*[+-]*\\d+$";
        boolean compile = Pattern.matches(rex, val);
        if (compile) {
            return compile;
        }
        return false;
    }

    /************************************************************************
     * @description: 是否是整数
     * "3." 也是整数
     * @author: wg
     * @date: 17:02  2021/12/14
     * @params:
     * @return:
     ************************************************************************/
    public static boolean isInteger(String val) {
        if (null == val || "".equals(val)) {
            return false;
        }

        String rex = "^[+-]?\\d*\\.?0*$";
        boolean numbMatch = Pattern.matches(rex, val);
        if (numbMatch) {
            return true;
        }

        rex = "^[+-]?\\d*[Ee]*[+-]*\\d+$";
        boolean science = Pattern.matches(rex, val);
        if (science) {
            return true;
        }

        return false;
    }

    /************************************************************************
     * @author: wg
     * @description: 加密字符串, nacos 使用的密码加密策略
     * @params:
     * @return:
     * @createTime: 15:25  2022/2/24
     * @updateTime: 15:25  2022/2/24
     ************************************************************************/
    // public static String passwordEncode(String password) {
    //     return new BCryptPasswordEncoder().encode(password);
    // }

    /************************************************************************
     * @author: wg
     * @description: 这个是错误演示, 文件 必须在 resource/static 下才有用, 在 main/java/** 下没用
     * @params:
     * @return:
     * @createTime: 9:57  2022/3/2
     * @updateTime: 9:57  2022/3/2
     ************************************************************************/
    public static Properties getProperties(String path) {
        Properties properties = new Properties();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            properties.load(bufferedReader);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /************************************************************************
     * @author: wg
     * @description: 通过 classPathResource,  读取 任意位置 下面的配置文件(json, xml, properties, yaml, yml)
     * @params:
     * @return:
     * @createTime: 9:29  2022/3/2
     * @updateTime: 9:29  2022/3/2
     ************************************************************************/
    public static Properties getProperties(ClassPathResource classPathResource) {
        Properties properties = new Properties();
        try {
            File file = File.createTempFile(Objects.requireNonNull(classPathResource.getFilename()), null);
            FileUtils.copyInputStreamToFile(classPathResource.getInputStream(), file);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            properties.load(bufferedReader);
            bufferedReader.close();
            file.delete();
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /************************************************************************
     * @author: wg
     * @description: 会有类型转换问题, 待解决, 错误示范 在 service/ExcelTest 里面
     * @params:
     * @return:
     * @createTime: 17:24  2022/3/4
     * @updateTime: 17:24  2022/3/4
     ************************************************************************/
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                String fieldName = field.getName();
                if (map.containsKey(fieldName)) {
                    field.set(obj, map.get(fieldName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /************************************************************************
     * @author: wg
     * @description: 反射判断 是否 有这个字段
     * @params:
     * @return:
     * @createTime: 14:37  2022/8/17
     * @updateTime: 14:37  2022/8/17
     ************************************************************************/
    public static <T> boolean  hasField(T t, String fieldName) {
        Class<?> aClass = t.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field f : fields) {
            if (fieldName.equals(f.getName())) {
                return true;
            }
        }
        return false;
    }

}
