package com.course.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.course")
@ComponentScan(basePackages = "com.course")
public class ServiceActivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceActivityApplication.class,args);
    }
}
