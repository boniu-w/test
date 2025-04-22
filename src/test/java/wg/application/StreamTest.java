package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.entity.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    /**
     * 找出重复元素
     */
    @Test
    public void duplicate(){
        List<String> list = Arrays.asList("Apple", "Banana", "Cherry", "Apple", "Orange");

        Map<String, Long> countMap = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<String> duplicates = countMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Duplicate elements: " + duplicates);
    }

    /**
     * @author wg
     * @description 测试 peek
     * @param
     * @return
     * @createTime 15:05  2025/4/22
     * @updateTime 15:05  2025/4/22
     */
    @Test
    public void testPeek(){
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(1);
        integers.add(1);
        integers.add(1);
        integers = integers.stream()
                .peek(e -> {
                    if (e == 1) {
                        e = 100;
                    }
                    return;
                })
                .collect(Collectors.toList());

        // 修改不成功
        for (Integer integer : integers) {
            System.out.println("integer = " + integer);
        }

        for (Integer e : integers) {
            if (e == 1) {
                e = 100;
            }
        }
        // 修改不成功
        for (Integer integer : integers) {
            System.out.println("integer = " + integer);
        }


        User xiao1 = new User();
        xiao1.setName("xiao1");
        xiao1.setBirthday(LocalDateTime.now());

        User xiao = new User();
        xiao.setName("xiao");
        xiao.setBirthday(LocalDateTime.now());

        User zhong = new User();
        zhong.setName("zhong");
        zhong.setBirthday(LocalDateTime.now().plus(200, ChronoUnit.SECONDS));

        User da = new User();
        da.setName("da");
        da.setBirthday(LocalDateTime.now().plus(2000, ChronoUnit.SECONDS));

        // 修改成功
        List<User> users = new ArrayList<>();
        users.add(xiao);
        users.add(xiao1);
        users.add(zhong);
        users.add(da);
        users= users.stream()
                .peek(e->{
                    if (e.getName().equals("da")){
                        e.setName("111111111111");
                    }
                })
                .collect(Collectors.toList());

        for (User user : users) {
            System.out.println("user.getName() = " + user.getName());
        }
    }
}
