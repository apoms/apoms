package io.aetherit.ats.ws.application.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix="ats.tomcat")
@Data
public class TomcatProperties {
    private String maxThreads;
    private String minSpareThreads;
    private String maxConnections;
    private String connectionLingers;
    private String connectionTimeout;
    private String keepAliveTimeout;
    private String maxKeepAliveRequests;
    private boolean allowOrigins;
    private String serverInfo;
}
