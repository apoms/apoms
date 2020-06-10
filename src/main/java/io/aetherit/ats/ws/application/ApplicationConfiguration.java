package io.aetherit.ats.ws.application;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.aetherit.ats.ws.application.support.TomcatProperties;
import io.aetherit.ats.ws.util.AES256Util;
import io.aetherit.ats.ws.util.CommonUtil;
import io.aetherit.ats.ws.util.JsonControllerUtil;
import io.aetherit.ats.ws.util.JsonUtil;
import io.aetherit.ats.ws.util.JwtTokenUtil;
import io.aetherit.ats.ws.util.MailTemplate;
import io.aetherit.ats.ws.util.SystemEnvUtil;

@Configuration
@EnableAutoConfiguration
public class ApplicationConfiguration {

    // Tomcat configuration
    @Bean
    public TomcatServletWebServerFactory servletContainer(TomcatProperties tomcatProperties) {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);

                connector.setProperty("maxThreads", 			tomcatProperties.getMaxThreads());
                connector.setProperty("minSpareThreads", 		tomcatProperties.getMinSpareThreads());
                connector.setProperty("maxConnections",		    tomcatProperties.getMaxConnections());
                connector.setProperty("connectionLinger", 	    tomcatProperties.getConnectionLingers());
                connector.setProperty("connectionTimeout", 	    tomcatProperties.getConnectionTimeout());
                connector.setProperty("keepAliveTimeout", 	    tomcatProperties.getKeepAliveTimeout());
                connector.setProperty("maxKeepAliveRequests",   tomcatProperties.getMaxKeepAliveRequests());
                connector.setProperty("server", 				tomcatProperties.getServerInfo());
            }
        };
    }

    // Utility Beans
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    public SystemEnvUtil systemEnvUtil() {
        return new SystemEnvUtil();
    }

    @Bean
    public JsonUtil jsonUtil() {
        return new JsonUtil();
    }
    
    
    @Bean
    public AES256Util aesUtil() throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException {
    	return new AES256Util();
    }

    @Bean
    public JsonControllerUtil jsonControllerUtil() {
        return new JsonControllerUtil(jsonUtil());
    }
    
    @Bean
    public CommonUtil commonUtil() {
        return new CommonUtil();
    }
    
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
    	return new JwtTokenUtil();
    }
    
    @Bean
    public MailTemplate MailTemplate() {
        return new MailTemplate();
    }
    
}
