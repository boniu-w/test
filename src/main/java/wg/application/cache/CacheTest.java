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
    
    @Cacheable(key = "#user.id")
    public Long cacheUserId(User user) {
        System.out.println("run cache " + user);
        return user.getId();
    }
}
