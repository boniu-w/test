package wg.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.entity.Student;
import wg.application.vo.Result;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/9/3 13:48
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/reflectController")

public class ReflectController {

    private static final Logger logger = LoggerFactory.getLogger(ReflectController.class);

    /****************************************************************
     * 修饰符为 private 通过反射 依然能拿到字段 即使没有get set ,但不能赋值
     * @author: wg
     * @time: 2020/9/7 10:46
     ****************************************************************/
    @RequestMapping(value = "/test1")
    public Result test1() {
        Class<Student> studentClass = Student.class;

        Field[] declaredFields1 = studentClass.getDeclaredFields();
        System.out.println(Arrays.toString(declaredFields1));

        try {
            Field field = studentClass.getDeclaredField("name");
            field.set(studentClass, "wg");


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Result.ok(declaredFields1);
    }

    /****************************************************************
     * <?> 和 <T> 的区别
     * @author: wg
     * @time: 2020/9/7 10:42
     ****************************************************************/
    //@RequestMapping(value = "/test2")
    //public Result test2() {
    //    List<Student> list1 = new ArrayList<>();
    //    list1.add(new Student("zhangsan", 18, 0));
    //    list1.add(new Student("lisi", 28, 0));
    //    //list1.add(new Teacher("wangwu",24,1));
    //    //这里如果add(new Teacher(...));就会报错，因为我们已经给List指定了数据类型为Student
    //    show1(list1);
    //
    //    System.out.println("************分割线**************");
    //
    //    //这里我们并没有给List指定具体的数据类型，可以存放多种类型数据
    //    List list2 = new ArrayList<>();
    //    list2.add(new Student("zhaoliu", 22, 1));
    //    list2.add(new Teacher("sunba", 30, 0));
    //    show2(list2);
    //
    //    return Result.ok();
    //}
    public static <T> void show1(List<T> list) {
        for (Object object : list) {
            System.out.println(object.toString());
        }
    }

    public static void show2(List<?> list) {
        for (Object object : list) {
            System.out.println(object);
        }
    }
}
