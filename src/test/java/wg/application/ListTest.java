package wg.application;

import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.entity.Student;
import wg.application.entity.User;
import wg.application.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void test3() {
        Student student = new Student();
        student.setAge(4);
        student.setId(11111);

        User user = new User();
        user.setAge(5);

        BeanUtil.copyProperties(student,user);

        System.out.println(user);
    }

}
