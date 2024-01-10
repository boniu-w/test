package wg.application.redis.controller;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.entity.Student;
import wg.application.util.RedisUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:20 2022/5/12
 * @updateTime: 10:20 2022/5/12
 ************************************************************************/
@RestController
@RequestMapping(value = "/redistest")
public class RedisTest {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    CacheManager cacheManager;

    @Resource
    RedisCacheManager redisCacheManager;

    @RequestMapping(value = "test1")
    public static void test() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");

        RedisUtil.set("test1", list, 60 * 60 * 24);

        Object test1 = RedisUtil.get("test1");

        if (test1 instanceof List) {
            List list1 = (List) test1;
            System.out.println(list1);

        }
    }

    @RequestMapping(value = "/test2")
    public void test2() {
        ArrayList<Student> list = new ArrayList<>();
        Student student = new Student();
        student.setAge(500);

        for (int i = 0; i < 3; i++) {
            student = new Student();
            student.setAge(i << 1);
            list.add(student);
        }

        RedisUtil.set("studentList", list);
        RedisUtil.set("myStudent", student);

        Object studentList = RedisUtil.get("studentList");
        System.out.println(studentList);

        Object myStudent = RedisUtil.get("myStudent");
        System.out.println(myStudent);
    }

    @GetMapping("/redistest")
    public Object redisTest(String cacheName, String cacheKey) {
        /*// 从 Redis 缓存中获取值
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Object cachedValue = opsForHash.get(cacheName, cacheKey); // non null hash key required

        if (cachedValue == null) {
            System.out.println("cachedValue = " + cachedValue);
        }

        BoundListOperations<String, Object> listOps = redisTemplate.boundListOps("springcachetest:userlist");

        // 获取列表的所有元素
        List<Object> range = listOps.range(0, -1);
        System.out.println("range = " + range);*/

        // 获取 key 为 "springcachetest:userlist" 的值
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        Object obj = valueOps.get("springcachetest::userlist");
        System.out.println("obj = " + obj); // obj = null

        Object obj1 = valueOps.get("userlist");
        System.out.println("obj1 = " + obj1); // obj1 = null

        return null;
    }

    @GetMapping("/redistestcachemanager")
    public Object redisTestCacheManager(String cacheName, String cacheKey) {
        // Spring Cache 提供的缓存管理机制来读取缓存数据。
        {
            Cache orgStructureCache = cacheManager.getCache(cacheName);
            if (orgStructureCache != null) {
                Cache.ValueWrapper valueWrapper = orgStructureCache.get(cacheKey);
                if (valueWrapper != null) {
                    return valueWrapper.get();
                }
            }
        }

        {
            Cache orgStructureCache = cacheManager.getCache(cacheName + "::" + cacheKey);
            if (orgStructureCache != null) {
                Cache.ValueWrapper valueWrapper = orgStructureCache.get(cacheKey);
                if (valueWrapper != null) {
                    return valueWrapper.get();
                }
            }
        }

        {
            Cache orgStructureCache = cacheManager.getCache("springcachetest::userlist");
            if (orgStructureCache != null) {
                Cache.ValueWrapper valueWrapper = orgStructureCache.get(cacheKey);
                if (valueWrapper != null) {
                    return valueWrapper.get();
                }
            }
        }

        {
            Cache orgStructureCache = cacheManager.getCache("springcachetest:userlist");
            if (orgStructureCache != null) {
                Cache.ValueWrapper valueWrapper = orgStructureCache.get(cacheKey);
                if (valueWrapper != null) {
                    return valueWrapper.get();
                }
            }
        }

        {
            Cache orgStructureCache = cacheManager.getCache("userlist");
            if (orgStructureCache != null) {
                Cache.ValueWrapper valueWrapper = orgStructureCache.get(cacheKey);
                if (valueWrapper != null) {
                    return valueWrapper.get();
                }
            }
        }

        return null;
    }

    @GetMapping("/getOrgStructureFromCache")
    public Object getOrgStructureFromCache(String cacheName, String cacheKey) {
        // 使用 String 序列化器
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 获取 key，这里我们直接使用硬编码的 'orgstructure'，因为注解中的条件不适用于直接 Redis 操作
        byte[] cacheKeys = stringSerializer.serialize(cacheKey);

        // 获取缓存值
        return (Object) redisTemplate.opsForValue().get(cacheKeys);
    }

    // redisCacheManager
    @GetMapping("/getRedisCacheManager")
    public Object getRedisCacheManager(String cacheName, String cacheKey) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = redisCacheManager.getCacheConfigurations();
        System.out.println("cacheConfigurations = " + cacheConfigurations);

        Collection<String> cacheNames = redisCacheManager.getCacheNames();

        Cache cache = redisCacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);
            if (valueWrapper != null) {
                return valueWrapper.get();
            }
        }

        return cacheNames;
    }
}
