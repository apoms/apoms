package io.aetherit.ats.ws.application.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "ats.ws.file")
@Data
public class FileStorageProperties {
    private String uploadDir;
    private String filesDomain;
    private String moviePath;
    private String thumnailUrl;
}
