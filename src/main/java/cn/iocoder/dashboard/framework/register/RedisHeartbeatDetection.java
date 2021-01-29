package cn.iocoder.dashboard.framework.register;

import cn.iocoder.dashboard.framework.register.registry.RedisRegistration;
import cn.iocoder.dashboard.framework.register.util.NamedThreadFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 心跳检测线程
 * TODO 目前是在每一个客户端都维护一个线程，可剥离出来放在一个中心去处理
 * @author Hccake 2021/1/29
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class RedisHeartbeatDetection {
    private final RedisRegisterManagement redisRegisterManagement;
    private final RedisRegistration redisRegistration;

    private final ScheduledExecutorService detectionExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("RedisRegistryExpireTimer", true));

    private ScheduledFuture<?> detectionFuture;

    @PostConstruct
    public void init(){
        // 心跳检测线程
        int detectionInterval = 60 * 1000;
        this.detectionFuture = detectionExecutor.scheduleWithFixedDelay(() -> {
            try {
                redisRegisterManagement.removeExpiredInstances(redisRegistration.getServiceId()); // Extend the expiration time
            } catch (Throwable t) { // Defensive fault tolerance
                log.error("Unexpected exception occur at defer expire time, cause: " + t.getMessage(), t);
            }
        }, detectionInterval, detectionInterval, TimeUnit.MILLISECONDS);
    }


    @PreDestroy
    public void destroy(){
        detectionFuture.cancel(true);
        detectionExecutor.shutdown();
    }

}
