package cn.iocoder.dashboard.framework.register.registry;

import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

/**
 * @author Hccake 2021/1/26
 * @version 1.0
 */
public class RedisAutoServiceRegistration  extends AbstractAutoServiceRegistration<Registration> {

    private final RedisRegistration redisRegistration;

    protected RedisAutoServiceRegistration(ServiceRegistry<Registration> serviceRegistry, AutoServiceRegistrationProperties properties, RedisRegistration registration) {
        super(serviceRegistry, properties);
        this.redisRegistration = registration;
    }

    /**
     * @return The object used to configure the registration.
     */
    @Override
    protected Object getConfiguration() {
        return null;
    }

    /**
     * @return True, if this is enabled.
     */
    @Override
    protected boolean isEnabled() {
        return true;
    }

    @Override
    protected RedisRegistration getRegistration() {
        return redisRegistration;
    }

    @Override
    protected RedisRegistration getManagementRegistration() {
        return null;
    }
}
