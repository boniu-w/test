package wg.application.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/************************************************************************
 * author: wg
 * description: ArrayUtil 
 * createTime: 9:44 2024/1/11
 * updateTime: 9:44 2024/1/11
 ************************************************************************/
public class ArrayUtil {

    /**
     * @author: wg
     * @description: 融合并去除多余元素
     * @params:
     * @return:
     * @createTime: 15:32  2024/2/18
     * @updateTime: 15:32  2024/2/18
     */
    public static String[] mergeAndRemoveDuplicates(String[]... arrays) {
        Set<String> set = new HashSet<>();

        // 遍历所有数组，将元素添加到集合中
        for (String[] array : arrays) {
            set.addAll(Arrays.asList(array));
        }

        // 将集合转换为数组
        return set.toArray(new String[0]);
    }
}
