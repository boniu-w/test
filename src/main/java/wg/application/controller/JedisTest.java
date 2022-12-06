package wg.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/************************************************************************
 * author: wg
 * description: JedisTest 
 * createTime: 11:05 2022/12/6
 * updateTime: 11:05 2022/12/6
 ************************************************************************/
@RestController
@RequestMapping(value = "/jedis_test")
public class JedisTest {

    @Resource
    JedisPool jedisPool;

    @GetMapping(value = "/test_jedis")
    public void testJedis() {
        Jedis jedis = jedisPool.getResource();
        jedis.set("wg", "123123");

        String wg = jedis.get("wg");
        System.out.println(wg);
    }
}
