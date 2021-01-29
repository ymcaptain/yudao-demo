package cn.iocoder.dashboard.framework.register.discovery;

import cn.iocoder.dashboard.framework.register.RedisRegisterManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务发现自动配置类
 * @author Hccake 2021/1/26
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
public class RedisDiscoveryAutoConfiguration {

    @Bean
    public RedisDiscoveryClient redisDiscoveryClient(RedisRegisterManagement redisRegisterManagement){
        return new RedisDiscoveryClient(redisRegisterManagement);
    }
}
