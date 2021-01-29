package cn.iocoder.dashboard.framework.register;

import cn.iocoder.dashboard.framework.register.registry.RedisRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hccake 2021/1/29
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
public class HeartBeatAuoConfiguration {


    @Bean
    public RedisHeartbeatDetection redisHeartbeatDetection(RedisRegisterManagement redisRegisterManagement, RedisRegistration redisRegistration){
        return new RedisHeartbeatDetection(redisRegisterManagement, redisRegistration);
    }

}
