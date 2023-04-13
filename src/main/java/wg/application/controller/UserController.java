package wg.application.controller;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;
import wg.application.cache.CacheTest;
import wg.application.entity.User;
import wg.application.exception.WgException;
import wg.application.service.UserService;
import wg.application.util.SpringContextUtils;
import wg.application.vo.Result;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/user_controller")
public class UserController {
    
    @Resource
    UserService userService;
    
    @Resource
    CacheManager cacheManager;
    
    @GetMapping(value = "page")
    public Result<List<User>> page() {
        
        return null;
    }
    
    @GetMapping(value = "/list")
    public Result<List<User>> list() {
        Result<List<User>> result = new Result<>();
        List<User> all = userService.list();
        all.get(0).setWealth(new BigDecimal("1"));
        result.setResult(all);
        
        // // 测试缓存, 必须启动redis, 不想启动redis, 可以先引入github依赖,然后在配置文件中修改 spring.cache.type=caffeine
        // CacheTest cacheBean = SpringContextUtils.getBean("cacheTest", CacheTest.class);
        // cacheBean.cacheUserId(all.get(5));  // 赵敏
        //
        // Cache cache = cacheManager.getCache("myCacheTest");
        // String name = cache.getName();
        // System.out.println("cache group name : " + name);
        //
        // // Cache.ValueWrapper wrapper = cache.get(all.get(5).getId());
        // // Object obj = wrapper.get();
        // // System.out.println("value: " + obj); // 赵敏
        //
        // Cache cache1 = cacheManager.getCache("123");
        // Cache.ValueWrapper wrapper1 = cache1.get(all.get(5).getId());
        // System.out.println(wrapper1.get()); // 赵敏
        
        return result;
    }
    
    @PostMapping(value = "/add")
    public void add(User user) {
        System.out.println(user);
    }
    
    @PostMapping(value = "/update_user")
    public void updateUser(@RequestBody User user) {
        userService.updateTestPrivate(user);
    }
    
    @PostMapping(value = "/update_user_test_throws")
    public void updateUserTestThrows(@RequestBody User user) {
        try {
            userService.updateTestThrows(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/update_user_test_tool_of_try")
    public void updateUserTestToolOfTry(@RequestBody User user) {
        try {
            userService.testToolClass(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/update_user_throw")
    public void updateUserThrow(@RequestBody User user) {
        try {
            userService.testThrow(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/update_user_throw2")
    public void updateUserThrow2(@RequestBody User user) {
        try {
            userService.testThrow2(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/update_user_test_try_and_throw")
    public void testTryAndThrow(@RequestBody User user) {
        try {
            userService.testTryAndThrow(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    @PostMapping(value = "/test_update_interceptor")
    public void testUpdateInterceptor(User user) {
        userService.testUpdateInterceptor(user);
        System.out.println(user);
    }
}
