package wg.application.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wg.application.cache.CacheTest;
import wg.application.entity.Picture;
import wg.application.entity.User;
import wg.application.exception.TheException;
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
    // @SentinelResource(value = "userList", fallback = "fallbackHandler")
    public Result<List<User>> list() {
        Result<List<User>> result = new Result<>();
        List<User> all = userService.list();
        all.get(0).setWealth(new BigDecimal("-99999.99"));
        all.get(0).setGender("man"); // 500
        result.setData(all);
        
        // ↓↓*******************  <测试缓存> start  *******************↓↓
        
        // // 测试缓存, 必须启动redis, 不想启动redis, 可以先引入github依赖,然后在配置文件中修改 spring.cache.type=caffeine
        CacheTest cacheBean = SpringContextUtils.getBean("cacheTest", CacheTest.class);
        cacheBean.cacheUserId(all.get(5));  // 赵敏
        
        Cache cache = cacheManager.getCache("myCacheTest");
        String name = cache.getName();
        System.out.println("cache group name : " + name);
        
        // Cache.ValueWrapper wrapper = cache.get(all.get(5).getId());
        // Object obj = wrapper.get();
        // System.out.println("value: " + obj); // 赵敏
        
        Cache cache1 = cacheManager.getCache("123");
        Cache.ValueWrapper wrapper1 = cache1.get(all.get(5).getId());
        System.out.println(wrapper1.get()); // 赵敏
        
        // ↑↑*******************  <测试缓存>  end  *******************↑↑
        
        return result;
    }
    
    @PostMapping(value = "/add")
    public void add(User user) {
        System.out.println(user);
    }
    
    @PostMapping(value = "/update_user")
    public void updateUser(@Validated @RequestBody User user) {
        userService.updateTestPrivate(user);
    }
    
    @PostMapping(value = "/update_user_test_throws")
    public void updateUserTestThrows(@RequestBody User user) {
        try {
            userService.updateTestThrows(user);
        } catch (TheException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/update_user_test_tool_of_try")
    public void updateUserTestToolOfTry(@RequestBody User user) {
        try {
            userService.testToolClass(user);
        } catch (TheException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/update_user_throw")
    public void updateUserThrow(@RequestBody User user) {
        try {
            userService.testThrow(user);
        } catch (TheException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/update_user_throw2")
    public void updateUserThrow2(@RequestBody User user) {
        try {
            userService.testThrow2(user);
        } catch (TheException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping(value = "/update_user_test_try_and_throw")
    public void testTryAndThrow(@RequestBody User user) {
        try {
            userService.testTryAndThrow(user);
        } catch (TheException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    @PostMapping(value = "/test_update_interceptor")
    public void testUpdateInterceptor(User user) {
        userService.testUpdateInterceptor(user);
        System.out.println(user);
    }
    
    /************************************************************************
     * @author: wg
     * @description: sentinel test
     * @params:
     * @return:
     * @createTime: 10:32  2023/4/14
     * @updateTime: 10:32  2023/4/14
     ************************************************************************/
    @GetMapping(value = "/sentinel_list")
    @SentinelResource(value = "sentinelList", fallback = "fallbackHandler")
    public Result<List<User>> sentinelList() {
        System.out.println("sentinel list");
        Result<List<User>> result = new Result<>();
        List<User> all = userService.list();
        all.get(0).setWealth(new BigDecimal("-99999.99"));
        
        result.setData(all);
        return result;
    }
    
    public void fallbackHandler() {
        System.out.println("usercontroller fall back");
    }
    
    
    /************************************************************************
     * @author: wg
     * @description: get post 测试
     * 结论:
     * 1. 只能有一个 requestbody
     * 2. getmapping 时, 参数 可以是 类
     * @params:
     * @return:
     * @createTime: 17:52  2023/7/11
     * @updateTime: 17:52  2023/7/11
     ************************************************************************/
    /*@GetMapping(value = "/list1")
    public Result<List<User>> list1(@RequestParam User user) {
        Result<List<User>> result = new Result<>();

        result.success("123");
        return result;
    }*/
    
    /*@GetMapping(value = "/list1")
    public Result<User> list1(User user) {
        Result<User> result = new Result<>();

        System.out.println("user.getName() = " + user.getName());
        // List<User> all = userService.list();

        result.setData(user);
        result.success("123");
        return result;
    }*/
    
    /*@PostMapping(value = "/list1")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "name"),
                    @ApiImplicitParam(name = "picture", dataType = "Picture", paramType = "body")
            }
    )
    public Result<User> list1(User user, @RequestBody Picture picture) {
        Result<User> result = new Result<>();

        System.out.println("user.getName() = " + user.getName());
        // List<User> all = userService.list();

        result.setData(user);
        result.success(picture.getName());
        return result;
    }*/
    
    @PostMapping(value = "/list1")
    // @ApiImplicitParams(
    //         {
    //                 @ApiImplicitParam(name = "user", dataType = "User", paramType = "body"),
    //                 @ApiImplicitParam(name = "picture", dataType = "Picture", paramType = "body")
    //         }
    // )
    public Result<User> list1(User user, @RequestBody Picture picture) {
        Result<User> result = new Result<>();
        
        System.out.println("user.getName() = " + user.getName());
        System.out.println("picture.getName() = " + picture.getName());
        
        result.setData(user);
        result.success(picture.getName());
        return result;
    }
    
    
    /************************************************************************
     * @author: wg
     * @description:
     * {
     *     "timestamp": 1689070743398,
     *     "status": 400,
     *     "error": "Bad Request",
     *     "message": "Required Picture parameter 'picture' is not present",
     *     "path": "/wg/user_controller/list1"
     * }
     * @params:
     * @return:
     * @createTime: 18:23  2023/7/11
     * @updateTime: 18:23  2023/7/11
     ************************************************************************/
    @GetMapping(value = "/list1")
    public Result<User> list1(@RequestParam(value = "picture") Picture picture) {
        Result<User> result = new Result<>();
        
        System.out.println("picture.getName() = " + picture.getName());
        
        result.success(picture.getName());
        return result;
    }
}
