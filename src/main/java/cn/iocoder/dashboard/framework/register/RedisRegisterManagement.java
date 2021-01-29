package cn.iocoder.dashboard.framework.register;

import cn.iocoder.dashboard.framework.register.registry.RedisDiscoveryProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.*;

/**
 * redis注册中心的数据操作管理
 * TODO 原子操作，移除过期实例同时移除无实例的服务
 * @author Hccake 2021/1/26
 * @version 1.0
 */
@RequiredArgsConstructor
public class RedisRegisterManagement {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisDiscoveryProperties redisDiscoveryProperties;

    /**
     * 服务列表，SET 类型，member 是 serviceId
     */
    private final static String SERVICES_KEY = "redis-register-services";

    /**
     * 服务实例列表，HASH类型，field 是 instanceId, value 是实例信息
     */
    private final static String SERVICES_INSTANCE_KEY_PREFIX = "redis-register-service-instance:";

    private static String getInstancesKey(String serviceId) {
        return SERVICES_INSTANCE_KEY_PREFIX + serviceId;
    }

    /**
     * 服务实例心跳维护，ZSET 类型，member instanceId, value 是过期时间
     */
    private final static String SERVICES_INSTANCE_EXPIRES_KEY_PREFIX = "redis-register-service-instance-expires:";

    private static String getInstanceExpiresKey(String serviceId) {
        return SERVICES_INSTANCE_EXPIRES_KEY_PREFIX + serviceId;
    }


    /**
     * 添加一个服务实例
     *
     * @param redisServiceInstance redis服务实例对象
     * @throws JsonProcessingException json 序列化异常
     */
    public void addServiceInstance(RedisServiceInstance redisServiceInstance) throws JsonProcessingException {
        String serviceId = redisServiceInstance.getServiceId();
        String instanceId = redisServiceInstance.getInstanceId();

        // 服务列表
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        opsForSet.add(SERVICES_KEY, serviceId);

        // 服务实例
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String value = new ObjectMapper().writeValueAsString(redisServiceInstance);
        opsForHash.put(getInstancesKey(serviceId), instanceId, value);

        // 服务实例的过期时间
        ZSetOperations<String, String> opsForZSet = stringRedisTemplate.opsForZSet();
        opsForZSet.add(getInstanceExpiresKey(serviceId), instanceId, System.currentTimeMillis() + redisDiscoveryProperties.getHeartbeatInterval());

    }


    /**
     * 移除一个服务实例
     *
     * @param registration 注册数据
     */
    public void removeServiceInstance(Registration registration) {
        String serviceId = registration.getServiceId();
        String instanceId = registration.getInstanceId();

        // 服务实例
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        opsForHash.delete(getInstancesKey(serviceId), instanceId);

        // 服务实例的过期时间
        ZSetOperations<String, String> opsForZSet = stringRedisTemplate.opsForZSet();
        opsForZSet.remove(getInstanceExpiresKey(serviceId), instanceId);
    }

    /**
     * 根据 服务id 获取其实例列表
     *
     * @param serviceId 服务id
     * @return List<ServiceInstance> 服务实例列表
     * @throws JsonProcessingException json 序列化异常
     */
    public List<ServiceInstance> getInstances(String serviceId) throws JsonProcessingException {
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        Map<String, String> entries = opsForHash.entries(getInstancesKey(serviceId));
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            RedisServiceInstance redisServiceInstance = objectMapper.readValue(entry.getValue(), RedisServiceInstance.class);
            serviceInstances.add(redisServiceInstance);
        }
        return serviceInstances;
    }


    /**
     * 获取服务列表
     *
     * @return List<String> 服务列表
     */
    public List<String> getServices() {
        // 服务列表
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        Set<String> members = opsForSet.members(SERVICES_KEY);
        return members == null ? new ArrayList<>() : new ArrayList<>(members);
    }

    /**
     * 延迟实例过期时间
     * @param serviceId 服务ID
     * @param instanceId 实例ID
     */
    public void deferInstanceExpired(String serviceId, String instanceId) {
        // 服务列表更新，因为可能被别的线程移除了
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        opsForSet.add(SERVICES_KEY, serviceId);
        // 服务实例的过期时间
        ZSetOperations<String, String> opsForZSet = stringRedisTemplate.opsForZSet();
        opsForZSet.add(getInstanceExpiresKey(serviceId), instanceId, System.currentTimeMillis() + redisDiscoveryProperties.getHeartbeatInterval());
    }


    private final static String REMOVE_EXPIRED_INSTANCES_SCRIPT = "local instanceIds = redis.call('zrangeByScore', KEYS[1], 0, ARGV[1])\n" +
            "if next(instanceIds) ~= nil then\n" +
            "    redis.call('zremrangeByScore', KEYS[1], 0, ARGV[1])\n" +
            "    redis.call('hdel', KEYS[2], unpack(instanceIds))\n" +
            "end";

    /**
     * 移除过期实例
     */
    public void removeExpiredInstances(String serviceId) {
        DefaultRedisScript<Void> redisScript = new DefaultRedisScript<>(REMOVE_EXPIRED_INSTANCES_SCRIPT);
        stringRedisTemplate.execute(redisScript,
                Arrays.asList(getInstanceExpiresKey(serviceId), getInstancesKey(serviceId)),
                String.valueOf(System.currentTimeMillis())
        );
    }

}
