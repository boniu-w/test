package wg.application.timerTask;

import org.springframework.stereotype.Component;
import wg.application.entity.User;
import wg.application.service.UserService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TimerTest {

    // public static void main(String[] args) {
    //     TimerTask timerTask = new TimerTask() {
    //         @Override
    //         public void run() {
    //             System.out.println("---");
    //         }
    //     };
    //
    //     Timer timer = new Timer();
    //     timer.schedule(timerTask, 1000, 3000);
    // }

    @Resource
    UserService userService;

    /************************************************************************
     * @description: springboot方式
     * 秒 分 时 日 月  周(周几)
     * 0 0 0 * * ? 表示 每天的凌晨 执行此任务
     * 0 * * * * 2 表示 每周2 的任意小时 任意分钟的 第 0 秒 开始执行 此任务
     * 0 36 10 2 11 *  每年的 11月2日10点36分 运行
     * 0/10 * * * * ?  每10秒运行一次
     * 缺点: 没法管理
     * @author: wg
     * @date:  10:07  2021/11/2
     * @params:
     * @return:
     ************************************************************************/
    // @Scheduled(cron = "0/10 * * * * ?")
    public void testSpringBootTimerTask(){
        List<User> all = userService.list();
        all.forEach(System.out::println);
        System.out.println(LocalDateTime.now());
    }
}
