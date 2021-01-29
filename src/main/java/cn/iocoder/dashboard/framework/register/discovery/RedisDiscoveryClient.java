package cn.iocoder.dashboard.framework.register.discovery;

import cn.iocoder.dashboard.framework.register.RedisRegisterManagement;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * 服务发现客户端
 * @author Hccake 2021/1/26
 * @version 1.0
 */
@RequiredArgsConstructor
public class RedisDiscoveryClient implements DiscoveryClient {
    private final RedisRegisterManagement redisRegisterManagement;

    @Override
    public String description() {
        return "Redis Discovery Client";
    }

    @SneakyThrows
    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        return redisRegisterManagement.getInstances(serviceId);
    }

    @Override
    public List<String> getServices() {
        return redisRegisterManagement.getServices();

    }
}
