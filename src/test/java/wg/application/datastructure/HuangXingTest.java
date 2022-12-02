package wg.application.datastructure;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.datastructure.huangxingduilie.CircularQueue;

/************************************************************************
 * @author: wg
 * @description: 环形队列 测试
 * @createTime: 17:25 2022/11/8
 * @updateTime: 17:25 2022/11/8
 ************************************************************************/
@SpringBootTest
public class HuangXingTest {

    @Test
    public void test1() {
        CircularQueue<Integer> queue = new CircularQueue<>(4);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        System.out.println(queue.getSize());
        System.out.println(queue);

        //出队测试
        queue.pop();
        queue.pop();
        System.out.println(queue.getSize());
        System.out.println(queue);

        //环形添加数据测试
        queue.add(5);
        queue.add(6);
        System.out.println(queue.getSize());
        System.out.println(queue);

        //环形弹出数据测试
        queue.pop();
        queue.pop();
        queue.pop();
        queue.pop();
        System.out.println(queue.getSize());
        System.out.println(queue);

        //再添加数据
        queue.add(7);
        queue.add(8);
        queue.add(9);
        queue.add(10);
        System.out.println(queue.getSize());
        System.out.println(queue);
    }

}
