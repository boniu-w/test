package wg.application.timerTask;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/************************************************************************
 * @description:
 * TimerTask 的缺点
 * 1. 任务执行时间长影响其他任务 当一个任务的执行时间过长时，会影响其他任务的调度
 * 2. 任务异常影响其他任务 使用 Timer 类实现定时任务时，当一个任务抛出异常，其他任务也会终止运行，
 * @author: wg
 * @date:  9:39  2021/11/2
 * @params:
 * @return:
 ************************************************************************/
public class MyTimerTask {

    /************************************************************************
     * @description:
     *  从结果中可以看出，当任务 1 运行时间超过设定的间隔时间时，任务 2 也会延迟执行。 原本任务 1
     * 和任务 2 的执行时间间隔都是 3s，但因为任务 1 执行了 5s，因此任务 2 的执行时间间隔也变成了 10s（和原定时间不符）
     * @author: wg
     * @date:  9:37  2021/11/2
     * @params:
     * @return:
     ************************************************************************/
    public static void main(String[] args) {
        // 定义任务 1
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("进入 timerTask 1：" + new Date());
                try {
                    // 休眠 5 秒
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Run timerTask 1：" + new Date());
            }
        };
        // 定义任务 2
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Run timerTask 2：" + new Date());
            }
        };
        // 计时器
        Timer timer = new Timer();
        // 添加执行任务（延迟 1s 执行，每 3s 执行一次）
        timer.schedule(timerTask, 1000, 3000);
        timer.schedule(timerTask2, 1000, 3000);
    }
}