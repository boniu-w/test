package wg.application.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskTest {

    private Logger logger = LoggerFactory.getLogger(TaskTest.class);

    public void test1() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("time: ");
            }
        }, 2000, 40); // 2000表示第一次执行任务延迟时间，40表示以后每隔多长时间执行一次run里面的任务
    }

    public void test2(){
        ScheduledThreadPoolExecutorTest scheduledThreadPoolExecutorTest = new ScheduledThreadPoolExecutorTest();
        scheduledThreadPoolExecutorTest.test1();
    }
}

class ScheduledThreadPoolExecutorTest {
    private Logger log = LoggerFactory.getLogger(ScheduledThreadPoolExecutorTest.class);

    protected void test1() {
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("time: ");
            }
        }, 0, 40, TimeUnit.MILLISECONDS);//0表示首次执行任务的延迟时间，40表示每次执行任务的间隔时间，TimeUnit.MILLISECONDS执行的时间间隔数值单位


    }
}
