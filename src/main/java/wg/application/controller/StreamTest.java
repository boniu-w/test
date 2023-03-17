package wg.application.controller;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wg
 * @Package wg.application.controller
 * @date 2020/4/21 14:12
 * @Copyright
 */
public class StreamTest {


    public static void main(String[] args) {
        List<Integer> myList = new ArrayList<>();
        for (int i = 0; i < 1E+7; i++) {
            myList.add(i);
        }
        long result = 0;
        long loopStartTime = System.currentTimeMillis();
        for (int i : myList) {
            if (i % 2 == 0) {
                result += i; // 所有偶数的和
            }
        }
        long loopEndTime = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("Loop total Time = " + (loopEndTime - loopStartTime));

        long streamStartTime = System.currentTimeMillis();
        System.out.println(myList.stream().filter(value -> value % 2 == 0).mapToLong(Long::valueOf).sum());
        long streamEndTime = System.currentTimeMillis();
        System.out.println("Stream total Time = " + (streamEndTime - streamStartTime));

        long parallelStreamStartTime = System.currentTimeMillis();
        System.out.println(myList.parallelStream().filter(value -> value % 2 == 0).mapToLong(Long::valueOf).sum());
        long parallelStreamEndTime = System.currentTimeMillis();
        System.out.println("Parallel Stream total Time = " + (parallelStreamEndTime - parallelStreamStartTime));
    }

    /************************************************************************
     * @author: wg
     * @description: 取最小值
     * @params:
     * @return:
     * @createTime: 18:04  2023/3/17
     * @updateTime: 18:04  2023/3/17
     ************************************************************************/
    public static void getMin() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(new BigDecimal("10"));
        numbers.add(new BigDecimal("20"));
        numbers.add(new BigDecimal("5"));

        Stream<BigDecimal> numberStream = numbers.stream();
        BigDecimal minNumber = numberStream.min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        System.out.println("最小值为：" + minNumber);
    }

    public static void streamTest() {
        // 生成若干个随机数
        List<Double> limit = Stream.generate(Math::random).limit(5).collect(Collectors.toList());
        limit.forEach(System.out::println);

        // 取最小值
        double asDouble = limit.stream().mapToDouble(Double::valueOf).min().getAsDouble();
        System.out.println("最小值 " + asDouble);
        // T t1 = list.stream()
        //         .filter((T t) -> getter(detailFieldMap.get("remainStrengthDetailFieldName"), t) != null)
        //         .min(Comparator.comparing(e -> (BigDecimal) getter(detailFieldMap.get("remainStrengthDetailFieldName"), e)))
        //         .get();

        // 排序
        // limit.sort(Double::compare); // 从小到大
        limit.sort(Comparator.comparing(Double::valueOf, (e1, e2) -> e2.compareTo(e1)));  // 从大到小
        System.out.println("collect 排序:  " + limit);

        // 过滤
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(" ?? "));
        System.out.println("合并字符串: " + mergedString);

        // 转map
        Map<String, String> map = filtered.stream().collect(Collectors.toMap(item -> item, item -> item + "  wg", (k1, k2) -> k2));
        map.forEach((k, v) -> {
            System.out.print(k);
            System.out.print(" --- ");
            System.out.print(v);
            System.out.println();
        });
    }

}
