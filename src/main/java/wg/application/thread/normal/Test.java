package wg.application.thread.normal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "/race_of_rabbit_and_tortoise")
public class Test {

    /**
     * 赛程
     */
    private static final Double course = 100D;

    @GetMapping(value = "/race")
    public static Map<String, Object> race() {
        HashMap<String, Object> map = new HashMap<>();
        Rabbit rabbit = new Rabbit();
        Tortoise tortoise = new Tortoise();

        // 开始跑
        Thread rabbitThread = new Thread(rabbit);
        Thread tortoiseThread = new Thread(tortoise);
        rabbitThread.start();
        tortoiseThread.start();

        long start = System.currentTimeMillis();
        System.out.println("-------------------------------------------");
        long count = 0;
        for (; ; ) {
            count++;
            // 当比赛结束是 返回 龟兔 所跑的距离
            if (rabbit.getLength().compareTo(course) >= 0 || tortoise.getLength().compareTo(course) >= 0) {
                System.out.println("比赛结束");
                System.out.println(System.currentTimeMillis() - start);

                map.put("stepOfRabbit", rabbit.getLength());
                map.put("stepOfTortoise", tortoise.getLength());
                map.put("count", count);

                rabbit.stopCurrentThread();
                tortoise.stopCurrentThread();

                return map;
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Object> race = race();
        System.out.println(race);
    }
}
