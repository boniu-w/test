package wg.application.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.PostConstruct;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WgUtil {
    private static final Logger logger = LoggerFactory.getLogger(WgUtil.class);

    private WgUtil wgUtil;

    @PostConstruct
    public void initWgUtil() {
        wgUtil = this;
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
    }

    public static void test() {
        String s1 = double2ScientificNotation(5.720970255000001E-6);
        System.out.println(s1);
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
}
