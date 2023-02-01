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
    public void copyTest(){
        Student student = new Student();
        student.setAge(1);

        Student student1 = new Student();
        student1.setAge(2);
        student1.setName("123");

        BeanUtil.copyProperties(student, student1);

        System.out.println(student1);
    }
}
