package wg.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/************************************************************************
 * author: wg
 * description: JedisConfig 
 * createTime: 11:17 2022/12/6
 * updateTime: 11:17 2022/12/6
 ************************************************************************/
@Configuration
public class JedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Profile({"wg","dev"})
    @Bean
    public JedisPool generateJedisPoolFactory() {
        System.out.println("<<<<<<<<<<<<<<<<<<  wg  >>>>>>>>>>>>>>>>>>>");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout);
        return jedisPool;
    }
    
    @Profile({"ubuntu"})
    @Bean
    public JedisPool generateJedisPoolFactory1() {
        System.out.println("<<<<<<<<<<<<<<<<<<  ubuntu  >>>>>>>>>>>>>>>>>>>");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout);
        return jedisPool;
    }
}
