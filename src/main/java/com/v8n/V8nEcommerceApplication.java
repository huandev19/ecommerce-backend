package com.v8n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class V8nEcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V8nEcommerceApplication.class, args);
    }
}
