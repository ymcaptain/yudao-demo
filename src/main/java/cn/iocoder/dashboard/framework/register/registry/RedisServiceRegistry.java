package cn.iocoder.dashboard.framework.register.registry;

import cn.iocoder.dashboard.framework.register.RedisRegisterManagement;
import cn.iocoder.dashboard.framework.register.RedisServiceInstance;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.client.serviceregistry.endpoint.ServiceRegistryEndpoint;

/**
 * @author Hccake 2021/1/26
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class RedisServiceRegistry implements ServiceRegistry<Registration> {
    private final RedisRegisterManagement redisRegisterManagement;

    /**
     * Registers the registration. A registration typically has information about an
     * instance, such as its hostname and port.
     *
     * @param registration registration meta data
     */
    @SneakyThrows
    @Override
    public void register(Registration registration) {
        RedisServiceInstance redisServiceInstance = new RedisServiceInstance();
        String serviceId = registration.getServiceId();
        redisServiceInstance.setServiceId(serviceId);
        redisServiceInstance.setHost(registration.getHost());
        redisServiceInstance.setPort(registration.getPort());
        redisServiceInstance.setMetadata(registration.getMetadata());
        redisServiceInstance.setInstanceId(registration.getInstanceId());
        redisRegisterManagement.addServiceInstance(redisServiceInstance);
    }

    /**
     * Deregisters the registration.
     *
     * @param registration registration meta data
     */
    @Override
    public void deregister(Registration registration) {
        redisRegisterManagement.removeServiceInstance(registration);
    }

    /**
     * Closes the ServiceRegistry. This is a lifecycle method.
     */
    @Override
    public void close() {
    }

    /**
     * Sets the status of the registration. The status values are determined by the
     * individual implementations.
     *
     * @param registration The registration to update.
     * @param status       The status to set.
     * @see ServiceRegistryEndpoint
     */
    @Override
    public void setStatus(Registration registration, String status) {

    }

    /**
     * Gets the status of a particular registration.
     *
     * @param registration The registration to query.
     * @return The status of the registration.
     * @see ServiceRegistryEndpoint
     */
    @Override
    public <T> T getStatus(Registration registration) {
        return null;
    }
}
