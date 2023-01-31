package wg.application;

import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wg.application.entity.Student;
import wg.application.entity.User;
import wg.application.util.CollectionUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 15:15 2022/4/26
 * @updateTime: 15:15 2022/4/26
 ************************************************************************/
@SpringBootTest
public class ListTest {

    /************************************************************************
     * @author: wg
     * @description: 差集
     * @params:
     * @return:
     * @createTime: 15:48  2022/4/26
     * @updateTime: 15:48  2022/4/26
     ************************************************************************/
    @Test
    public void chajitest() {
        Student student = new Student();
        ArrayList<Student> list1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            student = new Student();
            student.setAge(i);
            list1.add(student);

        }

        ArrayList<Student> list2 = new ArrayList<>(list1);
        for (int i = 0; i < 1; i++) {
            student = new Student();
            student.setAge(i);
            list2.add(student);

        }
        student = new Student();
        student.setAge(4);
        list2.add(student);

        // list2.clear();
        // student = new Student();
        // student.setAge(4);
        // list2.add(student);

        System.out.println("list1  ");
        list1.forEach(System.out::println);
        System.out.println("list2  ");
        list2.forEach(st -> System.out.println(st));

        System.out.println();
        List<Student> collect = list1.stream()
                .filter(st -> !list2.contains(st))
                .collect(Collectors.toList());
        System.out.println("collect差集  " + collect.size());
        collect.forEach(System.out::println);

        System.out.println();
        List<Student> collect2 = list2.stream()
                .filter(st -> !list1.contains(st))
                .collect(Collectors.toList());
        System.out.println("collect2差集  " + collect2.size());
        collect2.forEach(System.out::println);
    }

    /************************************************************************
     * @author: wg
     * @description: 空 null
     * @params:
     * @return:
     * @createTime: 9:24  2022/4/27
     * @updateTime: 9:24  2022/4/27
     ************************************************************************/
    @Test
    public void nullTest() {
        List list = null;
        // System.out.println(list.size()); // NullPointerException

        System.out.println(ObjectUtils.isEmpty(list)); // true
        System.out.println(list == null);
        System.out.println(list != null);

    }

    /************************************************************************
     * @author: wg
     * @description: 找不同
     * @params:
     * @return:
     * @createTime: 18:16  2022/4/27
     * @updateTime: 18:16  2022/4/27
     ************************************************************************/
    @Test
    public void test2() {
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(5);

        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);

        // 找出 list2 中有, list1 没有的
        Collection<Integer> different = CollectionUtil.getDifferentNoDuplicate(list1, list2);
        different.forEach(System.out::println);
    }

    /************************************************************************
     * @author: wg
     * @description: 找出多余的
     * @params:
     * @return:
     * @createTime: 16:05  2022/9/27
     * @updateTime: 16:05  2022/9/27
     ************************************************************************/
    @Test
    public void testDiff() {
        Student s0 = new Student();
        s0.setName("a");

        Student s1 = new Student();
        s1.setName("b");

        Student s2 = new Student();
        s2.setName("c");

        ArrayList<Student> minioList = new ArrayList<>();
        minioList.add(s0);
        minioList.add(s1);
        minioList.add(s2);

        Student s3 = new Student();
        s3.setName("a");

        Student s4 = new Student();
        s4.setName("b");

        Student s5 = new Student();
        s5.setName("d");

        Student s6 = new Student();
        s6.setName("f");

        ArrayList<Student> frontList = new ArrayList<>();
        frontList.add(s3);
        frontList.add(s4);
        frontList.add(s5);
        frontList.add(s6);

        List<Student> redundant = minioList.stream()
                .filter(student -> !frontList.stream().map(Student::getName).collect(Collectors.toList()).contains(student.getName()))
                .collect(Collectors.toList());


        redundant.forEach(System.out::println);

    }

    @Test
    public void test3() {
        Student student = new Student();
        student.setAge(4);
        student.setId(11111);

        User user = new User();
        user.setAge(5);

        BeanUtil.copyProperties(student, user);

        System.out.println(user);
    }

    /************************************************************************
     * @author: wg
     * @description: foreach
     * @params:
     * @return:
     * @createTime: 15:42  2022/5/11
     * @updateTime: 15:42  2022/5/11
     ************************************************************************/
    public void testForeach() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

    }

    @Test
    public void testSame() {
        // 集合一

        List<String> first = new ArrayList<>();

        first.add("jim");

        first.add("tom");

        first.add("jack");

//集合二

        List<String> second = new ArrayList<>();

        second.add("jack");

        second.add("happy");

        second.add("sun");

        second.add("good");

        // Collection exists=new ArrayList(second);
        //
        // Collection notexists=new ArrayList(second);
        //
        // exists.removeAll(first);
        //
        // System.out.println("_second中不存在于_set中的："+exists);
        //
        // notexists.removeAll(exists);
        //
        // System.out.println("相同："+notexists);

        Object[] objects = CollectionUtil.getSame(first, second).toArray();
        List<String> collect = new ArrayList<>(CollectionUtil.getSame(first, second));
        System.out.println(objects);
    }

    /**
     * 测试 group by
     * 测试结果: 并不会触发空指针
     */
    @Test
    public void testGroup() {
        Student student = new Student();
        ArrayList<Student> list1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            student = new Student();
            student.setAge(i);
            list1.add(student);
        }

        Student student1 = new Student();
        student1.setAge(1);
        list1.add(student1);

        list1.clear();

        Map<Integer, List<Student>> ageMap = list1.stream().collect(Collectors.groupingBy(Student::getAge));
        System.out.println(ageMap);
    }

    /************************************************************************
     * @author: wg
     * @description: flatMap: 把二维list 转成 一维 的 (把几个小的list转换到一个大的list)
     * @params:
     * @return:
     * @createTime: 17:09  2022/6/7
     * @updateTime: 17:09  2022/6/7
     ************************************************************************/
    @Test
    public void testFlatMap() {
        List<String> teamIndia = Arrays.asList("Virat", "Dhoni", "Jadeja");
        List<String> teamAustralia = Arrays.asList("Warner", "Watson", "Smith");
        List<String> teamEngland = Arrays.asList("Alex", "Bell", "Broad");
        List<String> teamNewZeland = Arrays.asList("Kane", "Nathan", "Vettori");
        List<String> teamSouthAfrica = Arrays.asList("AB", "Amla", "Faf");
        List<String> teamWestIndies = Arrays.asList("Sammy", "Gayle", "Narine");
        List<String> teamSriLanka = Arrays.asList("Mahela", "Sanga", "Dilshan");
        List<String> teamPakistan = Arrays.asList("Misbah", "Afridi", "Shehzad");

        List<List<String>> playersInWorldCup2016 = new ArrayList<>();
        playersInWorldCup2016.add(teamIndia);
        playersInWorldCup2016.add(teamAustralia);
        playersInWorldCup2016.add(teamEngland);
        playersInWorldCup2016.add(teamNewZeland);
        playersInWorldCup2016.add(teamSouthAfrica);
        playersInWorldCup2016.add(teamWestIndies);
        playersInWorldCup2016.add(teamSriLanka);
        playersInWorldCup2016.add(teamPakistan);

        playersInWorldCup2016.forEach(System.out::println);

        // Let's print all players before Java 8
        List<String> listOfAllPlayers = new ArrayList<>();

        for (List<String> team : playersInWorldCup2016) {
            listOfAllPlayers.addAll(team);
        }

        System.out.println("Players playing in world cup 2016");
        System.out.println(listOfAllPlayers);


        // Now let's do this in Java 8 using FlatMap
        List<String> flatMapList = playersInWorldCup2016
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println("List of all Players using Java 8");
        System.out.println(flatMapList);

        // -------------------------------------------------------------------------------------------------------------
        ArrayList<Student> studentList1 = new ArrayList<>();
        ArrayList<Student> studentList2 = new ArrayList<>();
        ArrayList<Student> studentList3 = new ArrayList<>();
        Student student = new Student();
        for (int i = 0; i < 3; i++) {
            student = new Student();
            student.setId(i);
            student.setAge(0);

            studentList1.add(student);

            student = new Student();
            student.setId((i + 10) << 1);
            student.setAge(10);
            studentList2.add(student);

            student = new Student();
            student.setId((i + 20) << 1);
            student.setAge(20);
            studentList3.add(student);
        }

        ArrayList<ArrayList<Student>> list = new ArrayList<>();
        list.add(studentList1);
        list.add(studentList2);
        list.add(studentList3);

        list.forEach(System.out::println);

        List<Student> studentList = list.stream()
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        studentList.forEach(System.out::println);
    }

    /************************************************************************
     * @author: wg
     * @description: 使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方法,
     * 它的 add/remove/clear 方法会抛出 UnsupportedOperationException 异常
     *  Arrays.asList 体现的是适配器模式，只是转换接口，后台的数据仍是数组
     * @params:
     * @return:
     * @createTime: 9:38  2022/6/15
     * @updateTime: 9:38  2022/6/15
     ************************************************************************/
    @Test
    public void arrayTest() {
        String[] str = new String[]{"yang", "hao"};
        List<String> list = Arrays.asList(str);

        // list.add("123");
        // System.out.println(list); // UnsupportedOperationException

        // ↓↓******************* start <Arrays.asList 体现的是适配器模式，只是转换接口，后台的数据仍是数组> *******************↓↓
        str[0] = "79";
        System.out.println(list);
        // ↑↑******************* end  <Arrays.asList 体现的是适配器模式，只是转换接口，后台的数据仍是数组>  *******************↑↑

    }

    @Test
    public void removeTest() {
        List<String> list = new ArrayList<>();
        int sum = list.stream().mapToInt(e -> (int) Long.parseLong(e)).sum();
        System.out.println("sum: " + sum);

        list.add("1");
        list.add("2");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (1 == 1) {
                iterator.remove();
            }
        }
        System.out.println(list);

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        for (String item : list1) {
            if ("1".equals(item)) {
                list1.remove(item);
            }
        }

        // ConcurrentModificationException
        for (String item : list1) {
            if ("2".equals(item)) {
                list1.remove(item);
            }
        }

        System.out.println(list1);

    }

    /************************************************************************
     * @author: wg
     * @description: 两个对象list 根据某些属性去重
     * 中心思想: 利用map 的key 不重复 原理
     * @params:
     * @return:
     * @createTime: 16:21  2023/1/29
     * @updateTime: 16:21  2023/1/29
     ************************************************************************/
    // @Transactional(rollbackFor = Exception.class)
    // private int insertPipe(List<PipeEntity> pipeEntityList) {
    //     int insert = 0;
    //     // 对 导入的表去重
    //     ArrayList<PipeEntity> distinctExcelList = pipeEntityList.stream()
    //             .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> {
    //                                 return new TreeSet<>(Comparator.comparing(item -> item.getPlatformInCode() + ";" + item.getPipeCode()));
    //                             })
    //                             , ArrayList::new)
    //             );
    //
    //     Map<String, PipeEntity> excelMap = distinctExcelList.stream()
    //             .collect(Collectors.toMap(item -> item.getPipeCode() + "-" + item.getPlatformInCode(), m -> m));
    //
    //     List<PipeEntity> datasourcePipeList = pipeDao.selectList(new QueryWrapper<>());
    //     Map<String, PipeEntity> datasourcePipeMap = datasourcePipeList.stream()
    //             .collect(Collectors.toMap(item -> item.getPipeCode() + "-" + item.getPlatformInCode(), m -> m));
    //
    //     // 融合 两个map
    //     Stream<Map.Entry<String, PipeEntity>> stream = Stream.concat(datasourcePipeMap.entrySet().stream(), excelMap.entrySet().stream());
    //     Map<String, PipeEntity> entityMap = stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2));
    //
    //     ArrayList<PipeEntity> pipeEntities = new ArrayList<>();
    //     UpdateWrapper<PipeEntity> updateWrapper = null;
    //     for (PipeEntity pipeEntity : entityMap.values()) {
    //         if (pipeEntity.getId() == null) {
    //             updateWrapper = new UpdateWrapper<>();
    //             updateWrapper.lambda()
    //                     .eq(PipeEntity::getPlatformInCode, pipeEntity.getPlatformInCode())
    //                     .eq(PipeEntity::getPipeCode, pipeEntity.getPipeCode());
    //             pipeDao.delete(updateWrapper);
    //
    //             pipeEntities.add(pipeEntity);
    //         }
    //     }
    //
    //     for (PipeEntity pipeEntity : pipeEntities) {
    //         insert += pipeDao.insert(pipeEntity);
    //     }
    //
    //     return insert;
    // }

}
