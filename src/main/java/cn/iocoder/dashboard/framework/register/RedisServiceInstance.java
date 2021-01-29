package cn.iocoder.dashboard.framework.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Hccake 2021/1/26
 * @version 1.0
 */
@Setter
@JsonIgnoreProperties({"scheme", "uri"})
public class RedisServiceInstance implements ServiceInstance {

    private String serviceId;

    private String instanceId;

    private String host;

    private int port;

    private boolean secure;

    private Map<String, String> metadata = new LinkedHashMap<>();

    public RedisServiceInstance(){
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public String getInstanceId() {
        return  this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public URI getUri() {
        return DefaultServiceInstance.getUri(this);
    }

    @Override
    public Map<String, String> getMetadata() {
        return metadata;
    }

    @Override
    public String getScheme() {
        return this.getUri().getScheme();
    }

    public void setUri(URI uri) {
        this.host = uri.getHost();
        this.port = uri.getPort();
        String scheme = uri.getScheme();
        if ("https".equals(scheme)) {
            this.secure = true;
        }
    }

}
