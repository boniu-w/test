package wg.application.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.entity.Student;
import wg.application.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:20 2022/5/12
 * @updateTime: 10:20 2022/5/12
 ************************************************************************/
@RestController
@RequestMapping(value = "/redistest")
public class RedisTest {

    @RequestMapping(value = "test1")
    public static void test() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");

        RedisUtil.set("test1", list, 60 * 60 * 24);

        Object test1 = RedisUtil.get("test1");

        if (test1 instanceof List) {
            List list1 = (List) test1;
            System.out.println(list1);

        }
    }

    @RequestMapping(value = "/test2")
    public void test2() {
        ArrayList<Student> list = new ArrayList<>();
        Student student = new Student();
        student.setAge(500);

        for (int i = 0; i < 3; i++) {
            student = new Student();
            student.setAge(i << 1);
            list.add(student);
        }

        RedisUtil.set("studentList", list);
        RedisUtil.set("myStudent", student);

        Object studentList = RedisUtil.get("studentList");
        System.out.println(studentList);

        Object myStudent = RedisUtil.get("myStudent");
        System.out.println(myStudent);

    }

}
