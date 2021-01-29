package cn.iocoder.dashboard.framework.register.registry;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Hccake 2021/1/26
 * @version 1.0
 */
@Data
@ConfigurationProperties("spring.cloud.redis.discovery")
public class RedisDiscoveryProperties {

    /**
     * 心跳间隔时间
     */
    private int heartbeatInterval = 60 * 1000;

}
