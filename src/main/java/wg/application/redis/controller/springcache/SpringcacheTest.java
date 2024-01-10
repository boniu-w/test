package wg.application.redis.controller.springcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.entity.User;
import wg.application.service.UserService;
import wg.application.vo.Result;

import javax.annotation.Resource;
import java.util.List;

/************************************************************************
 * author: wg
 * description: SpringcacheTest 
 * createTime: 16:08 2023/8/16
 * updateTime: 16:08 2023/8/16
 ************************************************************************/
@RestController
@RequestMapping(value = "/springcachetest")
public class SpringcacheTest {

    @Resource
    UserService userService;

    @GetMapping(value = "/list")
    @Cacheable(cacheNames = {"springcachetest"}, key = "'userlist'", condition = "#user==null || #user.id==null") // 第一次会执行方法体, 然后 存入redis, 之后 再调用就不走方法体, 直接从redis获取
    public Result<Object> list(User user) {
        Result<Object> result = new Result<>();
        try {
            List<User> list = userService.list();
            result.setData(list);
            return result.success();
        } catch (Exception e) {
            return result.error();
        }
    }

    @GetMapping(value = "/getbyid")
    @Cacheable(cacheNames = {"springcachetest"}, key = "#id") // 第一次会执行方法体, 然后 存入redis, 之后 再调用就不走方法体, 直接从redis获取
    public Result<Object> getById(Long id) {
        Result<Object> result = new Result<>();
        try {
            List<User> list = userService.list();
            for (User user : list) {
                if (user.getId().equals(id)) {
                    result.setData(user);
                    break;
                }
            }

            return result.success();
        } catch (Exception e) {
            return result.error();
        }
    }

    @GetMapping(value = "/removebyid")
    @CacheEvict(cacheNames = {"springcachetest"}, key = "#id")
    public Result<Object> removeById(Long id) {
        Result<Object> result = new Result<>();
        try {
            int i = userService.removeById(id);

            return result.success();
        } catch (Exception e) {
            return result.error();
        }
    }
}
