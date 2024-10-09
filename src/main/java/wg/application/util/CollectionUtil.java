package wg.application.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import wg.application.entity.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 该类提供对集合类的高效操作
 *
 * @author Czp
 */

public class CollectionUtil {
    
    /**
     * 不允许实例化
     */
    private CollectionUtil() {
    }
    
    /**
     * 获取两个集合的不同元素
     *
     * @param collmax
     * @param collmin
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> Collection<T> getDifferent(Collection<T> collmax, Collection<T> collmin) {
        //使用LinkeList防止差异过大时,元素拷贝
        Collection csReturn = new LinkedList();
        Collection max = collmax;
        Collection min = collmin;
        //先比较大小,这样会减少后续map的if判断次数
        if (collmax.size() < collmin.size()) {
            max = collmin;
            min = collmax;
        }
        //直接指定大小,防止再散列
        Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
        for (Object object : max) {
            map.put(object, 1);
        }
        for (Object object : min) {
            if (map.get(object) == null) {
                csReturn.add(object);
            } else {
                map.put(object, 2);
            }
        }
        for (Map.Entry<Object, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                csReturn.add(entry.getKey());
            }
        }
        return csReturn;
    }
    
    /**
     * 获取两个集合的不同元素,去除重复
     *
     * @param collmax
     * @param collmin
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> Collection<T> getDifferentNoDuplicate(Collection<T> collmax, Collection<T> collmin) {
        return new HashSet(getDifferent(collmax, collmin));
    }
    
    /************************************************************************
     * @author: wg
     * @description: 找相同
     * @params:
     * @return:
     * @createTime: 17:22  2022/5/31
     * @updateTime: 17:22  2022/5/31
     ************************************************************************/
    public static <T> Collection<T> getSame(Collection<T> list1, Collection<T> list2) {
        ArrayList<T> exist = new ArrayList<>(list2);
        ArrayList<T> same = new ArrayList<>(list2);
        
        exist.removeAll(list1);
        
        same.removeAll(exist);
        
        return same;
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
    
    /**
     * 拆分集合
     *
     * @param <T>           泛型对象
     * @param resList       需要拆分的集合
     * @param subListLength 每个子集合的元素个数
     * @return 返回拆分后的各个集合组成的列表
     * 代码里面用到了guava和common的结合工具类
     **/
    public static <T> List<List<T>> split(List<T> resList, int subListLength) {
        if (CollectionUtils.isEmpty(resList) || subListLength <= 0) {
            return Lists.newArrayList();
        }
        List<List<T>> ret = Lists.newArrayList();
        int size = resList.size();
        if (size <= subListLength) {
            // 数据量不足 subListLength 指定的大小
            ret.add(resList);
        } else {
            int pre = size / subListLength;
            int last = size % subListLength;
            // 前面pre个集合，每个大小都是 subListLength 个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = Lists.newArrayList();
                for (int j = 0; j < subListLength; j++) {
                    itemList.add(resList.get(i * subListLength + j));
                }
                ret.add(itemList);
            }
            // last的进行处理
            if (last > 0) {
                List<T> itemList = Lists.newArrayList();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * subListLength + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }
    
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
    
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        int size = 1099;
        for (int i = 0; i < size; i++) {
            list.add("hello-" + i);
        }
        // 大集合里面包含多个小集合
        List<List<String>> temps = split(list, 100);
        int j = 0;
        // 对大集合里面的每一个小集合进行操作
        for (List<String> obj : temps) {
            System.out.println(String.format("row:%s -> size:%s,data:%s", ++j, obj.size(), obj));
        }
    }
    
    /************************************************************************
     * @author: wg
     * @description: 交集
     * @params:
     * @return:
     * @createTime: 17:29  2023/4/4
     * @updateTime: 17:29  2023/4/4
     ************************************************************************/
    public static Collection and(Collection list1, Collection list2) {
        return CollectionUtils.intersection(list1, list2);
    }
    
    /************************************************************************
     * @author: wg
     * @description: 并集
     * @params:
     * @return:
     * @createTime: 17:30  2023/4/4
     * @updateTime: 17:30  2023/4/4
     ************************************************************************/
    public static Collection or(Collection list1, Collection list2) {
        return CollectionUtils.union(list1, list2);
    }
    
    /************************************************************************
     * @author: wg
     * @description: 差集
     * @params:
     * @return:
     * @createTime: 17:32  2023/4/4
     * @updateTime: 17:32  2023/4/4
     ************************************************************************/
    public static Collection sub(Collection list1, Collection list2) {
        return CollectionUtils.subtract(list1, list2);
    }


    /**
     * @author wg
     * @description 按年龄分组, 但保持原先的顺序
     * @param
     * @return
     * @createTime 17:15  2024/9/30
     * @updateTime 17:15  2024/9/30
     */
    public static List<List<User>> splitByAgeStream(List<User> users) {
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }

        return users.stream()
                .reduce(new ArrayList<List<User>>(), (result, user) -> {
                    if (result.isEmpty() || !result.get(result.size() - 1).get(0).getAge().equals(user.getAge())) {
                        result.add(new ArrayList<>());
                    }
                    result.get(result.size() - 1).add(user);
                    return result;
                }, (result1, result2) -> {
                    throw new UnsupportedOperationException("Parallel processing not supported");
                });
    }
}