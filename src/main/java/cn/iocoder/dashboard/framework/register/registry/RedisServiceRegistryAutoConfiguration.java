package cn.iocoder.dashboard.framework.register.registry;

import cn.iocoder.dashboard.framework.register.RedisRegisterManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hccake 2021/1/26
 * @version 1.0
 */
@EnableConfigurationProperties(RedisDiscoveryProperties.class)
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ AutoServiceRegistrationConfiguration.class,
        AutoServiceRegistrationAutoConfiguration.class })
@RequiredArgsConstructor
public class RedisServiceRegistryAutoConfiguration {
    private final RedisRegisterManagement redisRegisterManagement;
    private final RedisDiscoveryProperties redisDiscoveryProperties;

    @Bean
    public RedisServiceRegistry redisServiceRegistry() {
        return new RedisServiceRegistry(redisRegisterManagement);
    }

    @Bean
    public RedisRegistration redisRegistration() {
        return new RedisRegistration(redisRegisterManagement, redisDiscoveryProperties);
    }

    @Bean
    public RedisAutoServiceRegistration registration(
            RedisServiceRegistry redisServiceRegistry,
            AutoServiceRegistrationProperties autoServiceRegistrationProperties,
            RedisRegistration redisRegistration) {
        return new RedisAutoServiceRegistration(redisServiceRegistry,
                autoServiceRegistrationProperties, redisRegistration);
    }



}
