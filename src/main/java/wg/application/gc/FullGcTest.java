package wg.application.gc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*************************************************************
 * @Package wg.application.gc
 * @author wg
 * @date 2021/3/2 16:12
 * @version
 * @Copyright
 * @discription
 * 爱钱进 暴雷程序
 * java -Xms20M -Xmx20M -XX:+PrintGC wg.application.gc.FullGcTest
 *************************************************************/
public class FullGcTest {

    private static class CardInfo {
        BigDecimal price = new BigDecimal(0.0);
        String name = "张三";
        int age = 5;
        Date birthdate = new Date();

        public void m() {
            System.out.println(name);
        }

    }

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50, new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) throws InterruptedException {
        executor.setMaximumPoolSize(50);

        for (; ; ) {
            modelFit();
            Thread.sleep(100);
        }
    }

    private static void modelFit() {
        List<CardInfo> taskList = getAllCardInfo();
        System.out.println("taskList.size(): " + taskList.size());
        taskList.forEach(cardInfo -> {
            executor.scheduleWithFixedDelay(() -> {
                cardInfo.m();
            }, 2, 3, TimeUnit.SECONDS);
        });

    }

    private static List<CardInfo> getAllCardInfo() {
        ArrayList<CardInfo> taskList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            CardInfo cardInfo = new CardInfo();
            taskList.add(cardInfo);
        }

        return taskList;
    }


}
