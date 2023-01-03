package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.algorithm.IdWorker;

import java.util.ArrayList;
import java.util.LinkedList;

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



}
