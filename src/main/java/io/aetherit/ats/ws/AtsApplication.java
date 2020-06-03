package io.aetherit.ats.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.aetherit.ats.ws")
public class AtsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtsApplication.class, args);
    }
}