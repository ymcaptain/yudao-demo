package cn.iocoder.dashboard.framework.register;

import cn.iocoder.dashboard.framework.register.registry.RedisDiscoveryProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Hccake 2021/1/27
 * @version 1.0
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class RedisRegisterAutoConfiguration {
    private final RedisDiscoveryProperties redisDiscoveryProperties;

    @Bean
    public RedisRegisterManagement redisRegisterManagement(ObjectMapper objectMapper, StringRedisTemplate stringRedisTemplate){
        return new RedisRegisterManagement(objectMapper, stringRedisTemplate, redisDiscoveryProperties);
    }
}
