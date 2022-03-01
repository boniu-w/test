package wg.application.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import wg.application.util.JwtTokenUtil;
import wg.application.vo.Result;

/*************************************************************
 * @Package wg.application.redis.controller
 * @author wg
 * @date 2020/8/21 11:20
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/redisTestController")
public class RedisTestController {


    /***************************************************
     * 链接redis
     * @author: wg
     * @time: 2020/8/22 22:39
     ***************************************************/
    @RequestMapping(value = "/redisTest1")
    public Result redisTest1() {
        Jedis jedis = new Jedis("localhost");

        String info = jedis.info();
        System.out.println(info);

        System.out.println("redis is running  " + jedis.ping());
        return Result.ok("redis is running  " + jedis.ping());
    }


    public Result redisTest2() {

        String token = JwtTokenUtil.generateJwtToken();
        Jedis jedis = new Jedis("localhost");

        jedis.append("token", token);
        jedis.expire("token", 12);


        return Result.ok();
    }

}
