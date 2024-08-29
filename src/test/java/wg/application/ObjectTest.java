package wg.application;

import cn.hutool.core.bean.BeanUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.entity.Student;

/************************************************************************
 * author: wg
 * description: ObjectTest 
 * createTime: 15:40 2023/1/31
 * updateTime: 15:40 2023/1/31
 ************************************************************************/
@SpringBootTest
public class ObjectTest {

    /************************************************************************
     * @author: wg
     * @description: 复制中的 覆盖问题
     * @params:
     * @return:
     * @createTime: 9:36  2023/2/1
     * @updateTime: 9:36  2023/2/1
     ************************************************************************/
    @Test
    public void copyTest() {
        Student student = new Student();
        student.setId(1);
        student.setAge(1);

        Student student1 = new Student();
        student1.setAge(2);
        student1.setName("123");

        BeanUtil.copyProperties(student, student1);

        System.out.println(student1); // Student{name='null', age=1, id=null, sex=null, birthday='null'}

        // ****************************************************************************************************************************** //

        // ↓↓*******************  <code> start  *******************↓↓
        Student student0 = new Student();
        student0.setId(1);
        student0.setAge(1);

        Student student2 = null;
        student2 = student0;
        student2.setAge(3);

        System.out.println("student2 = " + student2);   // student2 = Student{name='null', age=3, id=1, sex=null, birthday='null'}
        System.out.println("student0 = " + student0);     // student = Student{name='null', age=3, id=1, sex=null, birthday='null'}

        Student student3 = new Student();
        student3 = student0;
        student3.setAge(33);

        // 总结: java 的 引用传递
        // ↑↑*******************  <code>  end  *******************↑↑
    }

    /**
     * @author wg
     * @description java 的 引用传递
     * @createTime 15:15  2024/8/29
     * @updateTime 15:15  2024/8/29
     */
    @Test
    public void reference() {
        Student student0 = new Student();
        student0.setId(1);
        student0.setAge(1);

        Student student3 = new Student();
        student3 = student0;
        student3.setAge(33);

        System.out.println("student3 = " + student3); // student3 = Student{name='null', age=33, id=1, sex=null, birthday='null'}
        System.out.println("student0 = " + student0); // student0 = Student{name='null', age=33, id=1, sex=null, birthday='null'}
    }
}
