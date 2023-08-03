package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/************************************************************************
 * author: wg
 * description: StreamTest 
 * createTime: 11:06 2023/8/3
 * updateTime: 11:06 2023/8/3
 ************************************************************************/
@SpringBootTest
public class StreamTest {

    @Test
    public void test() {
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");

        // 使用 Stream 进行过滤
        List<String> filteredFruits = fruits.stream()
                .filter(fruit -> fruit.startsWith("A"))
                .collect(Collectors.toList());

        System.out.println("Filtered fruits starting with 'A': " + filteredFruits);

        // 使用 Stream 进行映射
        List<String> upperCaseFruits = fruits.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Upper case fruits: " + upperCaseFruits);

        // 使用 Stream 进行排序
        List<String> sortedFruits = fruits.stream()
                .sorted()
                .collect(Collectors.toList());

        System.out.println("Sorted fruits: " + sortedFruits);


        // allMatch、anyMatch 和 noneMatch：用于检查集合中的元素是否满足某个条件。
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        boolean allEven = numbers.stream().allMatch(n -> n % 2 == 0);
        System.out.println("All even: " + allEven); // false

        boolean anyEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        System.out.println("Any even: " + anyEven); // true

        boolean noneEven = numbers.stream().noneMatch(n -> n % 2 == 0);
        System.out.println("None even: " + noneEven);  // false


        // reduce：将集合中的元素合并为一个值。
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);
    }
}
