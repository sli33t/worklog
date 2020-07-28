package cn.linbin.worklog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.rabbitmq")
@Component
public class RabbitProperties {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String virtualhost;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualhost() {
        return virtualhost;
    }

    public void setVirtualhost(String virtualhost) {
        this.virtualhost = virtualhost;
    }
}
