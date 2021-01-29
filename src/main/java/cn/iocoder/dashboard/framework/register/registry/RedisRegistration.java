package cn.iocoder.dashboard.framework.register.registry;

import cn.iocoder.dashboard.framework.register.RedisRegisterManagement;
import cn.iocoder.dashboard.framework.register.util.InetUtil;
import cn.iocoder.dashboard.framework.register.util.NamedThreadFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ManagementServerPortUtils;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Hccake 2021/1/26
 * @version 1.0
 */
@Slf4j
@Setter
public class RedisRegistration implements Registration, ServiceInstance, ApplicationContextAware {

    private static final String MANAGEMENT_SCHEME = "management.scheme";

    private static final String MANAGEMENT_ADDRESS = "management.address";

    private static final String MANAGEMENT_PORT = "management.port";

    private static final String MANAGEMENT_CONTEXT_PATH = "management.context-path";

    private static final String KEY_HEALTH_PATH = "health.path";

    private final RedisRegisterManagement redisRegisterManagement;

    private final RedisDiscoveryProperties redisDiscoveryProperties;

    private ApplicationContext context;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port: 8080}")
    private Integer port;


    private String host;

    private Map<String, String> metadata = new HashMap<>();

    private final ScheduledExecutorService expireExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("RedisRegistryExpireTimer", true));

    private ScheduledFuture<?> expireFuture;


    public RedisRegistration(RedisRegisterManagement redisRegisterManagement, RedisDiscoveryProperties redisDiscoveryProperties) {
        this.redisRegisterManagement = redisRegisterManagement;
        this.redisDiscoveryProperties = redisDiscoveryProperties;
    }

    @PostConstruct
    public void init() {

        Environment env = context.getEnvironment();

        Integer managementPort = ManagementServerPortUtils.getPort(context);
        if (null != managementPort) {
            metadata.put(MANAGEMENT_PORT, managementPort.toString());
            String contextPath = env
                    .getProperty("management.server.servlet.context-path");
            String address = env.getProperty("management.server.address");
            if (!StringUtils.hasText(contextPath)) {
                metadata.put(MANAGEMENT_CONTEXT_PATH, contextPath);
            }
            if (!StringUtils.hasText(address)) {
                metadata.put(MANAGEMENT_ADDRESS, address);
            }
        }

        // 过期时间延长线程
        int heartbeatInterval = redisDiscoveryProperties.getHeartbeatInterval();
        this.expireFuture = expireExecutor.scheduleWithFixedDelay(() -> {
            try {
                redisRegisterManagement.deferInstanceExpired(this.getServiceId(), this.getInstanceId()); // Extend the expiration time
            } catch (Throwable t) { // Defensive fault tolerance
                log.error("Unexpected exception occur at defer expire time, cause: " + t.getMessage(), t);
            }
        }, heartbeatInterval / 2, heartbeatInterval / 2, TimeUnit.MILLISECONDS);
    }


    @PreDestroy
    public void destroy(){
        expireFuture.cancel(true);
        expireExecutor.shutdown();
    }

    /**
     * @return The service ID as registered.
     */
    @Override
    public String getServiceId() {
        return applicationName;
    }

    @Override
    public String getInstanceId() {
        return  this.host + '-' + this.applicationName + '-' + this.port;
    }

    /**
     * @return The hostname of the registered service instance.
     */
    @Override
    public String getHost() {
        try {
            if (this.host == null) {
                InetAddress address = InetUtil.getLocalHostLANAddress();
                if (address != null){
                    this.host = address.getHostAddress();
                }
            }
            return host;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return host;
    }

    /**
     * @return The port of the registered service instance.
     */
    @Override
    public int getPort() {
        return port;
    }

    /**
     * @return Whether the port of the registered service instance uses HTTPS.
     */
    @Override
    public boolean isSecure() {
        return false;
    }

    /**
     * @return The service URI address.
     */
    @Override
    public URI getUri() {
        return null;
    }

    /**
     * @return The key / value pair metadata associated with the service instance.
     */
    @Override
    public Map<String, String> getMetadata() {
        return metadata;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
