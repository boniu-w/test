package wg.application.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import wg.application.entity.User;

/************************************************************************
 * author: wg
 * description: CacheTest 
 * createTime: 10:07 2023/4/12
 * updateTime: 10:07 2023/4/12
 ************************************************************************/
@CacheConfig(cacheNames = {"myCacheTest"})
@Component
public class CacheTest {
    
    /************************************************************************
     * @author: wg
     * @description:
     *  `@AliasFor("cacheNames")`
     *  `String[] value() default {};`
     * @params:
     * @return:
     * @createTime: 11:32  2023/4/12
     * @updateTime: 11:32  2023/4/12
     ************************************************************************/
    @Cacheable(key = "#user.id", value = "123")
    public String cacheUserId(User user) {
        System.out.println("run cache " + user);
        return user.getName();
    }
}
