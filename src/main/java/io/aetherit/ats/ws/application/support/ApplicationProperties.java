package io.aetherit.ats.ws.application.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "ats.ws")
@Data
public class ApplicationProperties {
    private int networkConnTimeout;
    private int networkReadTimeout;

}
