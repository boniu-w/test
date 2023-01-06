package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.algorithm.IdWorker;

import java.text.Collator;
import java.util.*;

/*****************************************
 * description:
 * date: 13:51 2021/7/26
 * auth: wg
 *****************************************/
@SpringBootTest
public class AlgorithmTest {


    /*****************************************************
     * @params:
     * @description: 通过雪花算法 生成一个数
     * @author: wg
     * @date: 2021/7/26 14:27
     *****************************************************/
    // @Test
    // public void test1() throws InterruptedException {
    //     long id = IdWorker.getId();
    //     System.out.println(id);
    //
    //     Thread.sleep(1500);
    //     System.out.println();
    //
    //     LinkedList<Long> longs = new LinkedList<>();
    //
    //     for (int i = 0; i < 10; i++) {
    //         // ids.add(i, IdWorker.getId());
    //         longs.add(IdWorker.getId());
    //     }
    //
    //     longs.forEach(System.out::println);
    // }
    //
    // @Test
    // public void test2(){
    //     final long workerIdBits = 5L;
    //     long maxWorkerId = -1L ^ (-1L << workerIdBits);
    //
    //     System.out.println(maxWorkerId);
    //     System.out.println();
    //
    //     ArrayList<Long> ids = new ArrayList<>();
    //
    //     IdWorker.initSequence(31L,2L);
    //     for (int i = 0; i < 10; i++) {
    //         ids.add(i, IdWorker.getId());
    //         // longs.add(IdWorker.getId());
    //     }
    //
    //     ids.forEach(System.out::println);
    // }

    /************************************************************************
     * @author: wg
     * @description: 排序, 空间复杂度太大, 不实用
     * @params:
     * @return:
     * @createTime: 11:07  2023/1/5
     * @updateTime: 11:07  2023/1/5
     ************************************************************************/
    @Test
    public void test() {
        int[] a = {3, 2, 8, 9, 6, 4, 5};
        int[] b = new int[10];

        for (int i = 0; i < a.length; i++) {
            int i1 = a[i];
            b[a[i]] = a[i];
            System.out.println(i1);
        }

        System.out.println();
        for (int i : b) {
            System.out.println(i);
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 一组人名排序
     * @params:
     * @return:
     * @createTime: 11:40  2023/1/5
     * @updateTime: 11:40  2023/1/5
     ************************************************************************/
    @Test
    public void testNames(){
        String[] names = {"删掉", "三十分", "啊啊是", "下面,吹牛逼v", "请问提前", "里皮哦", "立刻解放", "强迫五i额"};
        Arrays.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Collator collator = Collator.getInstance(Locale.CHINA);
                return collator.compare(o1, o2);
            }
        });

        for (String name : names) {
            System.out.println(name);
        }
    }
}
